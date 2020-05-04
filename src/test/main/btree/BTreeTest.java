package main.btree;

import main.file.FileRecord;
import main.record.GenerateRecord;
import main.record.Record;
import org.junit.jupiter.api.Test;

import java.util.List;

class BTreeTest {

    @Test
    void createTest() {
        BTree<Integer, String> bTree = new BTree<>();
        List<Record<Integer, String>> records = GenerateRecord.generateRecord(10);
        for (Record<Integer, String> record : records) {
            bTree.insert(record);
        }
        bTree.output();
    }

    @Test
    void searchTest() {
        BTree<Integer, String> bTree = new BTree<>();
        List<Record<Integer, String>> records = GenerateRecord.generateRecord(100);
        for (Record<Integer, String> integerStringRecord : records) {
            bTree.insert(integerStringRecord);
        }
        bTree.output();
        for (Record<Integer, String> record : records) {
            System.out.println(bTree.search(record.getId()));
        }
        System.out.println(bTree.search(0));
    }

    @Test
    void searchIndexTest() {
        BTree<Integer, Integer> bTree = new BTree<>();
        List<Record<Integer, Integer>> records = FileRecord.readAllIndex("data");
        for (Record<Integer, Integer> integerRecord : records) {
            bTree.insert(integerRecord);
        }
        for (Record<Integer, Integer> record : records) {
            System.out.println(bTree.search(record.getId()));
        }
    }

    @Test
    void deleteTest() {
        BTree<Integer, String> bTree = new BTree<>();
//        List<Record<Integer, String>> records = GenerateRecord.generateRecord(1000000);
//        FileRecord.writeRecord("data", records);
        List<Record<Integer, String>> records = FileRecord.readRecord("data");
        for (Record<Integer, String> integerStringRecord : records) {
            bTree.insert(integerStringRecord);
        }
        bTree.output();
        System.out.println("=======================开始删除======================");
        for (Record<Integer, String> record : records) {
            bTree.delete(record.getId());
        }
        bTree.output();
    }

    @Test
    void deleteIndexTest() {
        BTree<Integer, Integer> bTree = new BTree<>();
        List<Record<Integer, Integer>> readAllIndex = FileRecord.readAllIndex("data");
        for (Record<Integer, Integer> allIndex : readAllIndex) {
            bTree.insert(allIndex);
        }
        bTree.output();
        System.out.println("=======================开始删除======================");
        for (Record<Integer, Integer> allIndex : readAllIndex) {
            bTree.delete(allIndex.getId());
        }
        bTree.output();
    }
}
