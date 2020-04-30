package main.record;

import java.util.*;

public class GenerateRecord {

    public static List<Record> generateRecord(Integer number) {
        List<Record> records = new ArrayList<>();
        Random rand = new Random();
        Set<Integer> ids = new HashSet<>();
        do {
            ids.add(rand.nextInt());
        } while (ids.size() < number);
        int i = 0;
        for (Integer id : ids) {
            Record record = new Record();
            record.setId(rand.nextInt());
            record.setInfo("THISIS" + String.format("%06d", i) + "");
            records.add(record);
            i++;
        }
        return records;
    }
}
