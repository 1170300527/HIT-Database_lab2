package main.sort;

import main.file.FileRecord;
import main.record.Record;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void splitSort() {
        MergeSort.splitSort("data", 62500);
        List<Record<Integer, String>> records = FileRecord.readRecord(new File("tmp/data15"), 62499, 10);
        System.out.println(records);
    }

    @Test
    void mergeSortTest() {
        List<Record<Integer, String>> records = MergeSort.mergeSort("tmp", 4096);
        List<Record<Integer, String>> originalRecords = FileRecord.readRecord("data");
        Collections.sort(originalRecords);
        assertEquals(originalRecords, records);
    }
}