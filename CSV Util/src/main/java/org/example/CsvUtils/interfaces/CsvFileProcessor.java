package org.example.CsvUtils.interfaces;

import java.io.IOException;

public interface CsvFileProcessor {

    void process(String filePath, String encoding) throws IOException;
    void registerOnHeaderParsedListener(OnHeaderParsedListener listener);
    void registerOnRecordParsedListener(OnRecordParsedListener listener);
    void registerOnProcessCompleteListener(OnProcessCompleteListener listener);
    void registerOnMalformedRecordFoundListener (OnMalformedRecordFoundListener listener);
    void registerOnValidRecordFoundListener (OnValidRecordFoundListener listener);

}
