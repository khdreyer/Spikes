package org.example.CsvUtils.interfaces;

public interface RecordInfo {
     void setIsMalformed(boolean isMalformedRow);
     void setRowValues(String[] parsedCsvRec);
     void setRawString(String rawStr);
     void setLineNumber(int lineNum);
     void addMessage(String msg);
     int getLineNumber();
     String toString();
}
