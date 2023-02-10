package org.example;

import java.io.IOException;
import org.example.CsvUtils.CsvProcessRunner;

public class Main {

    public static void main(String[] args) throws IOException {
        var small = "./test_data/badfile.csv";
        var large = "./test_data/big_test.csv";
        var xxl = "./test_data/10millplus.csv";
        var fileName4 = "./test_data/jack_test.csv";
        var nofile = "./test_data/no.csv";

        // supported charset: US-ASCII, UTF-8, UTF-16, UTF-16LE, UTF-16BE
        var runner = new CsvProcessRunner();
        runner.go(large, "UTF-8");
        //runner.go(small, "UTF-8");

        // todo:
        // preprocess the file count and ensure we are hitting every row.
        // create a utility to get a header object
        // be smart about types. Try to identify email, phone
        // get encoding - done

    }
}