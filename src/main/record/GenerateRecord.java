package main.record;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateRecord {

    public static List<Record> generateRecord(Integer number) {
        List<Record> records = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < number; i++) {
            Record record = new Record();
            record.setId(rand.nextInt());
            record.setInfo("THISIS" + String.format("%06d", i) + "");
            records.add(record);
        }
        return records;
    }
}
