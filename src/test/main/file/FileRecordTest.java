package main.file;

import main.record.GenerateRecord;
import main.record.Record;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileRecordTest {

    @Order(1)
    @Test
    void writeRecord() {
        String filename = "data";
        List<Record<Integer, String>> records = GenerateRecord.generateRecord(20);
        FileRecord.writeRecord(filename, records);
    }

    @Order(2)
    @Test
    void readRecord() {
        String filename = "data";
        List<Record<Integer, String>> records = FileRecord.readRecord(filename);
        for (Record<Integer, String> record : records) {
            System.out.println(record);
        }
    }

    @Order(3)
    @Test
    void readAllIndex() {
        List<Record<Integer, Integer>> records;
        records = FileRecord.readAllIndex("data");
        for (Record<Integer, Integer> record : records) {
            System.out.println(record);
        }
    }

    @Test
    void readByIndex() {
        List<Record<Integer, Integer>> records;
        records = FileRecord.readAllIndex("data");
        for (Record<Integer, Integer> record : records) {
            System.out.println(FileRecord.readByIndex("data", record.getInfo()));
        }
    }
}