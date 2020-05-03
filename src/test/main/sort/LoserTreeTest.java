package main.sort;

import main.file.FileRecord;
import main.record.Record;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LoserTreeTest {

    @Test
    void loserSort() {

        List<Record<Integer, String>> list = new ArrayList<>();

        Queue<Record<Integer, String>> queue0 = new LinkedList<>(FileRecord.readRecord(new File("tmp/data0"), 0, 4));
        Queue<Record<Integer, String>> queue1 = new LinkedList<>(FileRecord.readRecord(new File("tmp/data1"), 0, 4));
        Queue<Record<Integer, String>> queue2 = new LinkedList<>(FileRecord.readRecord(new File("tmp/data2"), 0, 4));
        Queue<Record<Integer, String>> queue3 = new LinkedList<>(FileRecord.readRecord(new File("tmp/data3"), 0, 4));

        List<Queue<Record<Integer, String>>> sources = new ArrayList<>();
        sources.add(queue0);
        sources.add(queue1);
        sources.add(queue2);
        sources.add(queue3);

        for (Queue<Record<Integer, String>> source : sources) {
            System.out.println(source);
        }

        //进行 4 路归并
        ArrayList<Record<Integer, String>> initValues = new ArrayList<>(sources.size());
        for (Queue<Record<Integer, String>> records : sources) {
            initValues.add(records.poll());
        }
        //初始化败者树
        LoserTree<Record<Integer, String>> loserTree = new LoserTree<>(initValues);
        //输出胜者
        Integer s = loserTree.getWinner();
        list.add(loserTree.getLeaf(s));
        while (true) {
            //新增叶子节点
            Record<Integer, String> newLeaf = sources.get(s).poll();
            for (Queue<Record<Integer, String>> source : sources) {
                System.out.println(source);
            }
            if (newLeaf == null) {
                // sources[s] 对应的队列已经为空，删除队列并调整败者树
                loserTree.del(s);
                sources.remove((int) s);
            } else {
                loserTree.add(newLeaf, s);
            }

            s = loserTree.getWinner();
            if (s == null) {
                break;
            }
            //输出胜者
            list.add(loserTree.getLeaf(s));
        }
        System.out.println(list.size());
    }

}