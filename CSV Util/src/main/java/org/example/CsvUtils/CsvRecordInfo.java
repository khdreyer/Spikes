package org.example.CsvUtils;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;
import org.example.CsvUtils.interfaces.RecordInfo;

public class CsvRecordInfo implements RecordInfo {

    @Expose
    private int lineNumber = 0;
    @Expose
    public int columnCount = 0;
    @Expose
    private String rawString = null;
    @Expose
    public String[] rowValues = new String[0];
    @Expose
    public boolean isMalformed = false;

    public List<String> messages = new ArrayList<>();

    @Override
    public void setIsMalformed(boolean isMalformedRow) {
        isMalformed = isMalformedRow;
    }

    @Override
    public void setRowValues(String[] parsedCsvRec) {
        rowValues = parsedCsvRec;
        columnCount = parsedCsvRec.length;
    }

    public void setLineNumber(int lineNum){
        lineNumber = lineNum;
    }

    public int getLineNumber(){
        return lineNumber;
    }

    public void setRawString(String rawStr) {
        rawString = rawStr;
    }

    public void addMessage(String msg){
        messages.add(msg);
    }

    public String toString(){
        return rawString;
    }
}