package main;

import main.btree.BTree;
import main.file.FileRecord;
import main.record.GenerateRecord;
import main.record.Record;
import main.sort.MergeSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("+++++++++++++++++++++++++++++++菜单+++++++++++++++++++++++++++++++");
        System.out.println("1.生成数据并构造b树");
        System.out.println("2.已有数据并构造b树");
        System.out.println("3.查询");
        System.out.println("4.删除");
        System.out.println("5.排序");
        System.out.print("请输入选项: ");
        Scanner scanner = new Scanner(System.in);
        BTree<Integer, Integer> bTree = new BTree<>();
        String filename = "data";
        List<Record<Integer, String>> records = new ArrayList<>();
        List<Record<Integer, Integer>> recordIndex;
        int input = scanner.nextInt();
        while (input> 0) {
            switch (input) {
                case 1:
                    records = GenerateRecord.generateRecord(1000000);
                    FileRecord.writeRecord(filename, records);
                    recordIndex = FileRecord.readAllIndex(filename);
                    for (Record<Integer, Integer> record : recordIndex) {
                        bTree.insert(record);
                    }
                    break;
                case 2:
                    records = FileRecord.readRecord(filename);
                    recordIndex = FileRecord.readAllIndex(filename);
                    for (Record<Integer, Integer> record : recordIndex) {
                        bTree.insert(record);
                    }
                    break;
                case 3:
                    System.out.print("输入查询第几条记录(结果如果正确则为[THISIS编号]，id随机无法预知，所以先通过编号获取id再查询): ");
                    int index = scanner.nextInt();
                    System.out.println(FileRecord.readByIndex(filename, bTree.search(records.get(index).getId())));
                    break;
                case 4:
                    System.out.print("输入删除第几条记录(id随机无法预知，为了演示所以先通过编号获取id再查询): ");
                    int delete = scanner.nextInt();
                    bTree.delete(records.get(delete).getId());
                    break;
                case 5:
                    MergeSort.splitSort(filename, 62500);
                    MergeSort.mergeSort("tmp", 4096, "sortResult");
                    break;
                default:
                    System.out.println("输入错误重新输入");
            }
            System.out.print("请再次输入: ");
            input = scanner.nextInt();
        }
    }
}
