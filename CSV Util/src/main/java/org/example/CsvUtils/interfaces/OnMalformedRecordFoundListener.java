package org.example.CsvUtils.interfaces;

import org.example.CsvUtils.CsvRecordInfo;

public interface OnMalformedRecordFoundListener {

    void onMalformedRecordFoundEvent(CsvRecordInfo info);

}