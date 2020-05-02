package main.btree;

import main.file.FileRecord;
import main.record.GenerateRecord;
import main.record.Record;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BTreeTest {

    @Test
    void nodeTest() {
        BTree bTree = new BTree();
        List<Record> records = GenerateRecord.generateRecord(10);
        for (int i = 0; i < records.size(); i++) {
            bTree.insert(records.get(i));
        }
        bTree.output();
    }

    @Test
    void nodeSearchTest() {
        BTree bTree = new BTree();
        List<Record> records = GenerateRecord.generateRecord(10);
        for (int i = 0; i < records.size(); i++) {
            bTree.insert(records.get(i));
        }
        for (Record record : records) {
            System.out.println(bTree.search(record.getId()));
        }
    }

    @Test
    void nodeDeleteTest() {
        BTree bTree = new BTree();
        List<Record> records = GenerateRecord.generateRecord(1000000);
        FileRecord.writeRecord("data", records);
//        List<Record> records = FileRecord.readRecord("data");
        for (int i = 0; i < records.size(); i++) {
            bTree.insert(records.get(i));
//            System.out.println("================================" + i + "================================");
//            bTree.output();
        }
//        bTree.output();
        System.out.println("=======================开始删除======================");
        for (int i = 0; i < records.size(); i++) {
//            System.out.println("================================" + i + ": " + records.get(i).getId() + "================================");
            bTree.delete(records.get(i).getId());
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
//            bTree.output();
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
        bTree.output();
    }
}