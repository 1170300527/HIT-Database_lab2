package main.record;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GenerateRecord {

    /**
     * 生成数据，使用set保证key值没有重复再构造记录
     * @param number 数据条数
     * @return 数据的list集合
     */
    public static @NotNull List<Record<Integer, String>> generateRecord(Integer number) {
        List<Record<Integer, String>> records = new ArrayList<>();
        Random rand = new Random();
        Set<Integer> ids = new HashSet<>();
        do {
            ids.add(rand.nextInt());
        } while (ids.size() < number);
        int i = 0;
        for (Integer id : ids) {
            Record<Integer, String> record = new Record<>();
            record.setId(id);
            record.setInfo("THISIS" + String.format("%06d", i) + "");
            records.add(record);
            i++;
        }
        return records;
    }
}
