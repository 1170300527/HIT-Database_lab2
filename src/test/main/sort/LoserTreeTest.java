package main.sort;

import main.file.FileRecord;
import main.record.GenerateRecord;
import main.record.Record;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class LoserTreeTest {

    @Test
    void sortTest() {
        //假设当前有 4 个归并段
        List<Record<Integer, String>> records = GenerateRecord.generateRecord(40);
        FileRecord.writeRecord("data", records);
        MergeSort.splitSort("data", 10);
        List<Record<Integer, String>> records1 = FileRecord.readRecord("tmp/data0");
        List<Record<Integer, String>> records2 = FileRecord.readRecord("tmp/data1");
        List<Record<Integer, String>> records3 = FileRecord.readRecord("tmp/data2");
        List<Record<Integer, String>> records4 = FileRecord.readRecord("tmp/data3");
        Queue<Record<Integer, String>> queue0 = new LinkedList<>(records1);
        Queue<Record<Integer, String>> queue1 = new LinkedList<>(records2);
        Queue<Record<Integer, String>> queue2 = new LinkedList<>(records3);
        Queue<Record<Integer, String>> queue3 = new LinkedList<>(records4);

        Queue<Record<Integer, String>>[] sources = new Queue[4];
        sources[0] = queue0;
        sources[1] = queue1;
        sources[2] = queue2;
        sources[3] = queue3;

        //进行 4 路归并
        ArrayList<Record<Integer, String>> initValues = new ArrayList<>(sources.length);
        for (Queue<Record<Integer, String>> source : sources) {
            initValues.add(source.poll());
        }
        //初始化败者树
        LoserTree<Record<Integer, String>> loserTree = new LoserTree<>(initValues);
        //输出胜者
        Integer s = loserTree.getWinner();
        System.out.println(loserTree.getLeaf(s));
        while (true) {
            //新增叶子节点
            Record<Integer, String> newLeaf = sources[s].poll();
            if (newLeaf == null) {
                // sources[s] 对应的队列已经为空，删除队列并调整败者树
                loserTree.del(s);
            } else {
                loserTree.add(newLeaf, s);
            }

            s = loserTree.getWinner();
            if (s == null) {
                break;
            }
            //输出胜者
            System.out.println(loserTree.getLeaf(s));
        }
    }
}