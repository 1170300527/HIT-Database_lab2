package main.record;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenerateRecordTest {

    @Test
    void generateRecord() {
        List<Record<Integer, String>> records = GenerateRecord.generateRecord(1000000);
        for (Record<Integer, String> record : records) {
            System.out.println(record);
        }
    }
}