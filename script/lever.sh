#!/bin/bash

# -----------------------------------------------------------------------------
# Filename: lever.sh
# Version: v1.0.0
# Date: 2018-08-23 11:01:12
# Author: Xiaodong Xie
# Description: Stress test for java-tron
# -----------------------------------------------------------------------------

# -----------------------------------------------------------------------------
# <!-- Input
# -----------------------------------------------------------------------------

TYPE=""
COUNT=0
TPS=0

# -----------------------------------------------------------------------------
# Input --!>
# -----------------------------------------------------------------------------

# -----------------------------------------------------------------------------
# <!-- Configuration
# -----------------------------------------------------------------------------

# All nodes of stress test
NODES=(
"47.52.243.22"
"47.91.213.254"
"47.254.68.236"
"47.254.42.144"
"39.106.178.126"
)

WORKSPACE=$PWD

LOG_ERROR_FILE="${WORKSPACE}/error.log"

LEVER_PATH="${WORKSPACE}/lever"
LEVER_GITHUB="https://github.com/tronprotocol/lever.git"

TOOLS_PATH="${WORKSPACE}/tools"

XMX="40g"
XMS="40g"

# -----------------------------------------------------------------------------
# Configuration --!>
# -----------------------------------------------------------------------------

# -----------------------------------------------------------------------------
# <!-- Variable
# -----------------------------------------------------------------------------

# -----------------------------------------------------------------------------
# Variable --!>
# -----------------------------------------------------------------------------

# -----------------------------------------------------------------------------
# <!-- Constant
# -----------------------------------------------------------------------------

# Log type
LOG_TYPE_ERROR="error"
LOG_TYPE_TASK="task"
LOG_TYPE_RESULT="result"
# Log message
LOG_MSG_ERROR="ERROR"
LOG_MSG_TASK="TASK"
LOG_MSG_RESULT="RESULT"

# Type
TYPE_TRANSFER_TRX="transfer-trx" # Transfer 1 balance from 1 account to 1 account
TYPE_TRANSFER_ASSET="transfer-asset" # Transfer 1 asset from 1 account to 1 account
TYPE_DEPLOY_CONTRACT="deploy-contract" # Deploy a simple contract
TYPE_TRIGGER_CONTRACT="trigger-contract" # Trigger a simple contract
TYPE_AIRDROP="airdrop" # Transfer 1 contract asset from 1 account to 1 million accounts
TYPE_FIBONACCI="fibonacci" # Trigger fibonacci contract
TYPE_ALL="${TYPE_TRANSFER_TRX}/${TYPE_TRANSFER_ASSET}/${TYPE_DEPLOY_CONTRACT}/${TYPE_TRIGGER_CONTRACT}/${TYPE_AIRDROP}/${TYPE_FIBONACCI}"

# -----------------------------------------------------------------------------
# Constant --!>
# -----------------------------------------------------------------------------

# -----------------------------------------------------------------------------
# <!-- Function
# -----------------------------------------------------------------------------

# Description: Print log message
# Parameters:
#   $1: log type, [error, task, result]
#   $2: log content
print() {
  logTime=`date +'%Y-%m-%d %H:%M:%S'`
  case $1 in
    ${LOG_TYPE_ERROR})
      echo -e "\033[31m[$LOG_MSG_ERROR]\033[0m $logTime $2"
    ;;
    ${LOG_TYPE_TASK})
      echo -e "\033[34m[$LOG_MSG_TASK]\033[0m $logTime $2"
    ;;
    ${LOG_TYPE_RESULT})
      echo -e "\033[32m[$LOG_MSG_RESULT]\033[0m $logTime $2"
    ;;
  esac
}

printError() {
  print ${LOG_TYPE_ERROR} "$1"
}

printTask() {
  print ${LOG_TYPE_TASK} "$1"
}

printResult() {
  print ${LOG_TYPE_RESULT} "$1"
}

stringIsEmpty() {
  if [ -z "$1" ]; then
    return 0
  fi

  return 1
}

checkError() {
  result=`cat ${LOG_ERROR_FILE}`
  stringIsEmpty "${result}"

  if [ $? -eq 1 ]; then
    printError "${result}"
    exit 1
  fi
}

checkProgramStatus() {
  printTask "Checking ${2} status of $1"

  result=`ssh -p 22008 tron@$1 "ps aux|grep $2|grep -v 'grep'"`

  if [ "${result}" == "" ]; then
    echo "${2} status of ${1} is not running" > ${LOG_ERROR_FILE}
  fi

  checkError

  printResult "${2} status of ${1} is running"
}

checkNodesTronStatus() {
  printTask "Checking nodes java-tron status"

  for node in ${NODES[@]}; do
    checkProgramStatus "${node}" "java-tron"
  done
}

init() {
  printTask "Init"
  echo "" > ${LOG_ERROR_FILE}
}

parse() {
  while [ -n "$1" ]; do
    case "$1" in
      --type)
        if [ -n "$2" ]; then
          TYPE=$2
          shift 2
        else
          printError "Please input --type [${TYPE_ALL}]"
          exit 1
        fi
      ;;

      --count)
        if [ -n "$2" ]; then
          COUNT=$2
          shift 2
        else
          printError "Please input --count [count]"
          exit 1
        fi
      ;;

      --tps)
        if [ -n "$2" ]; then
          TPS=$2
          shift 2
        else
          printError "Please input --tps [tps]"
          exit 1
        fi
      ;;

      *)
        shift
      ;;
    esac
  done

  case ${TYPE} in
    "${TYPE_TRANSFER_TRX}")
    ;;
    "${TYPE_TRANSFER_ASSET}")
    ;;
    "${TYPE_DEPLOY_CONTRACT}")
    ;;
    "${TYPE_TRIGGER_CONTRACT}")
    ;;
    "${TYPE_AIRDROP}")
    ;;
    "${TYPE_FIBONACCI}")
    ;;
    *)
      printError "--type must be [${TYPE_ALL}]"
      exit 1
    ;;
  esac

  if [ ${COUNT} -le 0 ]; then
    printError "--count must > 0"
    exit
  fi

  if [ ${TPS} -le 0 ]; then
    printError "--tps must > 0"
    exit
  fi
}

updateLever() {
  printTask "Updating lever"

  if [ ! -e ${LEVER_PATH} ]; then
    mkdir -p ${LEVER_PATH}
    cd ${LEVER_PATH}
    git clone "${LEVER_GITHUB}"
  fi

  cd ${LEVER_PATH}
  git reset --hard HEAD
  git clean -d -f
  git pull --rebase
}

# -----------------------------------------------------------------------------
# Function --!>
# -----------------------------------------------------------------------------

# -----------------------------------------------------------------------------
# <!-- Main
# -----------------------------------------------------------------------------

# -----------------------------------------------------------------------------
# Main --!>
# -----------------------------------------------------------------------------

parse $@

init

# checkNodesTronStatus

# updateLever

