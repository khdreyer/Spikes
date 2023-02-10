package org.example.CsvUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import org.example.CsvUtils.interfaces.CsvFileProcessor;
import org.example.CsvUtils.interfaces.OnHeaderParsedListener;
import org.example.CsvUtils.interfaces.OnMalformedRecordFoundListener;
import org.example.CsvUtils.interfaces.OnProcessCompleteListener;
import org.example.CsvUtils.interfaces.OnRecordParsedListener;
import org.example.CsvUtils.interfaces.OnValidRecordFoundListener;

public class CsvProcessRunner implements
    OnProcessCompleteListener,
    OnValidRecordFoundListener,
    OnMalformedRecordFoundListener,
    OnHeaderParsedListener,
    OnRecordParsedListener {

    private final CsvFileProcessor processor;
    private int processedLines;
    private int validLines;
    private int inValidLines;

    private final CsvProcessingResult report;

    public CsvProcessRunner(){
        processor = new KensCSVFileProcessor();
        processor.registerOnProcessCompleteListener(this);
        processor.registerOnValidRecordFoundListener(this);
        processor.registerOnMalformedRecordFoundListener(this);
        processor.registerOnHeaderParsedListener(this);
        processor.registerOnRecordParsedListener(this);
        report = new CsvProcessingResult();
    }

    public void go(String filename, String encoding) throws IOException {
        try{
            report.setFileName(filename);
            report.setStartTimeEpoch(System.currentTimeMillis());
            report.setEncodingMsg(encoding);

            // get pre-count
            var simpleCounter = new FileLineCounter();
            var lineCount = simpleCounter.getLineCount(filename);
            report.setLineCount(lineCount);

            // process file
            processor.process(filename, encoding);
        } catch(Exception ex){
            throw ex;
        }
    }

    private void writeReport(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(report);
        System.out.println(prettyJson);
    }

    @Override
    public void onMalformedRecordFoundEvent(CsvRecordInfo rowInfo) {
        try {
            inValidLines++;
            report.setInValidLineCount(inValidLines);
            report.AddMalformedInfo(rowInfo);
        } catch(Exception ex){
            throw ex;
        }
    }

    @Override
    public void onValidRecordEvent(CsvRecordInfo info) {
        try {
            validLines++;
            report.setValidLineCount(validLines);
        } catch(Exception ex){
            throw ex;
        }
    }

    @Override
    public void onProcessCompleteEvent() {
        try {
            report.setEndTimeEpoch(System.currentTimeMillis());
            writeReport();
        } catch(Exception ex){
            throw ex;
        }
    }

    @Override
    public void onHeaderParsedEvent(CsvHeaderInfo info) {
        try {
            processedLines++;
            report.setHeaderInfo(info);
        } catch(Exception ex){
            throw ex;
        }
    }

    @Override
    public void onRecordParsedEvent(CsvRecordInfo info) {
        try {
            processedLines++;
            report.setProcessedLineCount(processedLines);
        } catch(Exception ex){
            throw ex;
        }
    }
}
