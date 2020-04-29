package main.file;

import main.record.GenerateRecord;
import main.record.Record;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileRecordTest {

    @Order(1)
    @Test
    void writeRecord() {
        String filename = "data";
        List<Record> records = GenerateRecord.generateRecord(1000000);
        FileRecord.writeRecord(filename, records);
    }

    @Order(2)
    @Test
    void readRecord() {
        String filename = "data";
        List<Record> records = FileRecord.readRecord(filename);
    }
}