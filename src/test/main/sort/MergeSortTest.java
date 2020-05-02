package main.sort;

import main.file.FileRecord;
import main.record.Record;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void splitSort() {
        MergeSort.splitSort("data", 10);
        List<Record<Integer, String>> records1 = FileRecord.readRecord("tmp/data0");
        List<Record<Integer, String>> records2 = FileRecord.readRecord("tmp/data1");
        System.out.println(records1);
        System.out.println(records2);
    }
}