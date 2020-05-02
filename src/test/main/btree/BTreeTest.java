package main.btree;

import main.file.FileRecord;
import main.record.GenerateRecord;
import main.record.Record;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.List;

class BTreeTest {

    @Test
    void createTest() {
        BTree<Integer, String> bTree = new BTree<>();
        List<Record<Integer, String>> records = GenerateRecord.generateRecord(10);
        for (int i = 0; i < records.size(); i++) {
            bTree.insert(records.get(i));
        }
        bTree.output();
    }

    @Test
    void searchTest() {
        BTree<Integer, String> bTree = new BTree<>();
        List<Record<Integer, String>> records = GenerateRecord.generateRecord(10);
        for (int i = 0; i < records.size(); i++) {
            bTree.insert(records.get(i));
        }
        bTree.output();
        for (Record<Integer, String> record : records) {
            System.out.println(bTree.search(record.getId()));
        }
    }

    @Test
    void deleteTest() {
        BTree<Integer, String> bTree = new BTree<>();
        List<Record<Integer, String>> records = GenerateRecord.generateRecord(1000000);
        FileRecord.writeRecord("data", records);
//        List<Record> records = FileRecord.readRecord("data");
        for (int i = 0; i < records.size(); i++) {
            bTree.insert(records.get(i));
        }
        bTree.output();
        System.out.println("=======================开始删除======================");
        for (int i = 0; i < records.size(); i++) {
            bTree.delete(records.get(i).getId());
        }
        bTree.output();
    }
}
