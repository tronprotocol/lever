package org.tron.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvUtils {
  public static List<CSVRecord> read(File file) {
    BufferedReader bufferedReader = null;
    CSVParser csvParser = null;
    List<CSVRecord> list = null;
    CSVFormat csvFormat = CSVFormat.DEFAULT.withAllowMissingColumnNames();

    try {
      bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      csvParser = new CSVParser(bufferedReader, csvFormat);
      List<CSVRecord> records = csvParser.getRecords();
      list = new ArrayList();
      for (int i = 0; i < records.size(); ++i) {
        CSVRecord record = records.get(i);
        list.add(record);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        bufferedReader.close();
        csvParser.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    return list;
  }
}
