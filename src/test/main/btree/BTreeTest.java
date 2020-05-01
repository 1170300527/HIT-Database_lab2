package main.btree;

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
        List<Record> records = GenerateRecord.generateRecord(7);
        for (int i = 0; i < records.size(); i++) {
            bTree.insert(records.get(i));
        }
        bTree.output();
        for (Record record : records) {
            bTree.delete(record.getId());
            bTree.output();
        }
    }

}