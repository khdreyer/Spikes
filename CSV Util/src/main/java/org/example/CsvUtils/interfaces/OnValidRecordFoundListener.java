package org.example.CsvUtils.interfaces;

import org.example.CsvUtils.CsvRecordInfo;

@FunctionalInterface
public interface OnValidRecordFoundListener {

    void onValidRecordEvent(CsvRecordInfo info);

}
