package main.sort;

import main.file.FileRecord;
import main.record.Record;

import java.io.*;
import java.util.*;

public class MergeSort {

    /**
     * 每次读取部分记录在内存中排序并写入临时文件
     * @param filename 要排序的文件名
     * @param number 内存中可存放记录条数
     */
    public static void splitSort(String filename, int number) {

        File file = new File("tmp");
        if (!file.exists()) {
            if (!file.mkdir())
                System.out.println("创建文件夹失败");
        } else {
            File[] files = file.listFiles();
            for (File childFile : files) {
                if (!childFile.delete())
                    System.out.println("文件删除失败: " + childFile.getName());
            }
        }

        try (InputStream inputStream = new FileInputStream(filename)) {
            byte[] bytes = new byte[16];
            List<Record<Integer, String>> records = new ArrayList<>();
            int i = 0;
            while (inputStream.read(bytes) != -1) {
                Record<Integer, String> record = FileRecord.bytes2Record(bytes);
                records.add(record);
                if (records.size() % number == 0) {
                    Collections.sort(records);
                    FileRecord.writeRecord("tmp/data" + i, records);
                    records.clear();
                    i++;
                }
            }
            if (records.size() > 0) {
                Collections.sort(records);
                FileRecord.writeRecord("tmp/data" + i, records);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多路归并排序
     * @param path 已经排序好的临时文件夹路径
     * @param number 每次读取条数
     * @param resultFilename 结果文件名
     */
    public static void mergeSort(String path, int number, String resultFilename) {
        File rootFile = new File(path);
        if (!rootFile.exists()) {
            System.out.println("文件夹不存在");
            System.exit(1);
        }
        File[] files = rootFile.listFiles();
        int[] start = new int[files.length];
        //归并次数
        List<List<Record<Integer, String>>> records = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            records.add(FileRecord.readRecord(files[i], start[i], number));
            start[i] += number;
        }
        List<Queue<Record<Integer, String>>> sources = new ArrayList<>();
        for (List<Record<Integer, String>> record : records) {
            sources.add(new LinkedList<>(record));
        }
        ArrayList<Record<Integer, String>> initValues = new ArrayList<>(sources.size());
        for (Queue<Record<Integer, String>> source : sources) {
            initValues.add(source.poll());
        }
        //初始化败者树
        LoserTree<Record<Integer, String>> loserTree = new LoserTree<>(initValues);
        //输出胜者
        Integer s = loserTree.getWinner();
        try (OutputStream outputStream = new FileOutputStream(resultFilename)) {
            Record<Integer, String> record = loserTree.getLeaf(s);
            byte[] bytes = FileRecord.int2Bytes(record.getId());
            outputStream.write(bytes);
            outputStream.write(record.getInfo().getBytes());
            while (true) {
                //新增叶子节点
                Record<Integer, String> newLeaf = sources.get(s).poll();
                if (newLeaf == null) {
                    List<Record<Integer, String>> recordList = FileRecord.readRecord(files[s], start[s], number);
                    if (recordList.isEmpty()) { //返回空说明已经读完该文件，删除叶子
                        loserTree.del(s);
                        sources.remove((int) s);
                    }
                    else { //未读完将新的数据加入到待排序序列中
                        start[s] += recordList.size();
                        sources.set(s, new LinkedList<>(recordList));
                        newLeaf = sources.get(s).poll();
                        loserTree.add(newLeaf, s);
                    }
                } else {
                    loserTree.add(newLeaf, s);
                }

                s = loserTree.getWinner();
                if (s == null) {
                    break;
                }
                //输出胜者
                record = loserTree.getLeaf(s);
                bytes = FileRecord.int2Bytes(record.getId());
                outputStream.write(bytes);
                outputStream.write(record.getInfo().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
