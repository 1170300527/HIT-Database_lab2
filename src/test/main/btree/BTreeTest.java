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
//        for (Record record : records) {
//            System.out.println(record);
//        }
        for (int i = 0; i < 10; i++) {
            bTree.insert(records.get(i));
        }
        bTree.output();
        for (Record record : records) {
            System.out.println(bTree.search(record.getId()));
        }
//        System.out.println(bTree.search(records.get(7).getId()));
//        bTree.test();
    }
}