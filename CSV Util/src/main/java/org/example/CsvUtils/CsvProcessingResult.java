package org.example.CsvUtils;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

public class CsvProcessingResult {
    @Expose
    public String fileName;
    @Expose
    public String encoding;
    @Expose
    public int lineCount = 0;
    @Expose
    public int processedLineCount = 0;
    @Expose
    private int validLineCount = 0;
    @Expose
    private int inValidLineCount = 0;
    @Expose
    private long epochStart;
    @Expose
    private long epochEnd;
    @Expose
    public long processTimeInMilliseconds;
    @Expose
    private CsvHeaderInfo headerInfo;
    @Expose
    private List<CsvRecordInfo> malformedLines;
    @Expose
    private List<String> messages;

    public CsvProcessingResult() {
        malformedLines = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public void AddMalformedInfo(CsvRecordInfo info){
        malformedLines.add(info);
    }

    public void setEncodingMsg(String encodingMsg){
        encoding = encodingMsg;
    }

    public void setLineCount(int count){
        lineCount = count;
    }

    public void setProcessedLineCount(int count){
        processedLineCount = count;
    }

    public void setValidLineCount(int count){
        validLineCount = count;
    }
    public void setInValidLineCount(int count){
        inValidLineCount = count;
    }

    public void setHeaderInfo(CsvHeaderInfo info){
        headerInfo = info;
    }

    public CsvHeaderInfo getCsvHeaderInfo(){
        return headerInfo;
    }

    public void setFileName(String name){
        fileName = name;
    }

    public void addMessage(String msg){
        messages.add(msg);
    }

    public void setStartTimeEpoch(long epoch){
        epochStart = epoch;
    }

    public void setEndTimeEpoch(long epoch){
        epochEnd = epoch;
        processTimeInMilliseconds = getTimeToProcessInMilliseconds();
    }

    public long getTimeToProcessInMilliseconds(){
        return epochEnd - epochStart;
    }
}