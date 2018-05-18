package org.tron.common.dispatch;

import com.google.protobuf.ByteString;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.tron.protos.Protocol;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"type", "amount", "nice"})
public class Stats {
  private Protocol.Transaction.Contract.ContractType type;
  private ByteString address;
  private Long amount;
  private Boolean nice;
}
