package org.example.CsvUtils;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import org.example.CsvUtils.interfaces.CsvFileProcessor;
import org.example.CsvUtils.interfaces.OnHeaderParsedListener;
import org.example.CsvUtils.interfaces.OnMalformedRecordFoundListener;
import org.example.CsvUtils.interfaces.OnProcessCompleteListener;
import org.example.CsvUtils.interfaces.OnRecordParsedListener;
import org.example.CsvUtils.interfaces.OnValidRecordFoundListener;

public class KensCSVFileProcessor implements CsvFileProcessor {

    private OnValidRecordFoundListener validRecordFoundListener;
    private OnMalformedRecordFoundListener malformedRecordFoundListener;
    private OnProcessCompleteListener processCompleteListener;
    private OnHeaderParsedListener headerParsedListener;
    private OnRecordParsedListener recordParsedListener;

    private final String MISALIGNMENT_MESSAGE = "[INFO!] Inspect the raw text and look for escape characters causing the misalignment.";

    @Override
    public void process(String filename, String encoding) throws IOException {

        // parse header
        var csvHeaderInfo = processHeader(filename, encoding);

        // parse records
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), encoding))) {

            //skip header and set line number
            reader.readLine();
            int lineNum = 1;

            // Parse rows
            String line;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                var csvRecordInfo = parseLine(csvHeaderInfo.columnCount, lineNum, line);
                if (csvRecordInfo.isMalformed) {
                    if (malformedRecordFoundListener != null)
                        malformedRecordFoundListener.onMalformedRecordFoundEvent(csvRecordInfo);
                } else {
                    if (validRecordFoundListener != null) validRecordFoundListener.onValidRecordEvent(csvRecordInfo);
                }
                if (recordParsedListener != null) recordParsedListener.onRecordParsedEvent(csvRecordInfo);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            if (processCompleteListener != null) processCompleteListener.onProcessCompleteEvent();
        }
    }

    private CsvHeaderInfo processHeader(String filename, String encoding) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), encoding))) {
            var header = reader.readLine();
            var csvHeaderInfo = parseLine(header);
            if (headerParsedListener != null) headerParsedListener.onHeaderParsedEvent(csvHeaderInfo);
            return csvHeaderInfo;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private CsvRecordInfo parseLine(int expectedColumnCount, int lineNum, String rawLineData) {
        var recordInfo = new CsvRecordInfo();
        recordInfo.setLineNumber(lineNum);
        recordInfo.setRawString(rawLineData);

        try {
            var csvHeaderRecord = parseCSV(rawLineData);
            recordInfo.setRowValues(csvHeaderRecord);
        }
        catch (Exception ex) {
            recordInfo.setIsMalformed(true);
            recordInfo.addMessage("[ERROR] Unknown parsing error on row" + recordInfo.columnCount + ". Exception: " + ex);
        }

        if (expectedColumnCount != recordInfo.columnCount) {
            recordInfo.setIsMalformed(true);
            recordInfo.addMessage("[ERROR] Found " + recordInfo.columnCount + " columns, but was expecting " + expectedColumnCount + ".");
            recordInfo.addMessage(MISALIGNMENT_MESSAGE);
        }

        return recordInfo;
    }

    private CsvHeaderInfo parseLine(String rawLineData) {
        var headerInfo = new CsvHeaderInfo();
        headerInfo.setLineNumber(1);
        headerInfo.setRawString(rawLineData);

        try {
            var csvHeaderRecord = parseCSV(rawLineData);
            headerInfo.setRowValues(csvHeaderRecord);
        }
        catch (Exception ex) {
            headerInfo.setIsMalformed(true);
            headerInfo.addMessage("[ERROR] Error parsing header. Exception: " + ex);
            headerInfo.addMessage(MISALIGNMENT_MESSAGE);
        }

        return headerInfo;
    }

    public String[] parseCSV(String rawString) throws IOException {
        CSVReader reader = new CSVReader(new StringReader(rawString));
        return reader.readNext();
    }

    @Override
    public void registerOnProcessCompleteListener(OnProcessCompleteListener listener){
        processCompleteListener = listener;
    }

    @Override
    public void registerOnMalformedRecordFoundListener (OnMalformedRecordFoundListener listener){
        malformedRecordFoundListener = listener;
    }

    @Override
    public void registerOnValidRecordFoundListener(OnValidRecordFoundListener listener) {
        validRecordFoundListener = listener;
    }

    @Override
    public void registerOnHeaderParsedListener(OnHeaderParsedListener listener) {
        headerParsedListener = listener;
    }

    @Override
    public void registerOnRecordParsedListener(OnRecordParsedListener listener) {
        recordParsedListener = listener;
    }
}