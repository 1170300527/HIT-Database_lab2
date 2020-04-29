package main.record;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenerateRecordTest {

    @Test
    void generateRecord() {
        List<Record> records = GenerateRecord.generateRecord(1000000);
        for (Record record : records) {
            System.out.println(record);
        }
    }
}