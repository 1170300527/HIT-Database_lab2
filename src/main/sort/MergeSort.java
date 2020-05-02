package main.sort;

import main.file.FileRecord;
import main.record.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSort {

    /**
     * 每次读取部分记录在内存中排序并写入临时文件
     * @param filename 要排序的文件名
     * @param number 内存中可存放记录条数
     */
    public static void splitSort(String filename, int number) {
        try (InputStream inputStream = new FileInputStream(filename)) {
            byte[] bytes = new byte[16];
            List<Record<Integer, String>> records = new ArrayList<>();
            int i = 0;
            while (inputStream.read(bytes) != -1) {
                Record<Integer, String> record = FileRecord.bytes2Record(bytes);
                records.add(record);
                if (records.size() % number == 0) {
                    records.sort(Comparator.comparingInt(Record::getId));
                    FileRecord.writeRecord("tmp/data" + i, records);
                    records.clear();
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
