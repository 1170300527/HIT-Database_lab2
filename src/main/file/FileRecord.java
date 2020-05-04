package main.file;

import main.record.Record;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileRecord {

    /**
     * 将记录写道文件
     * @param filename 文件名
     * @param records 记录
     */
    public static void writeRecord(String filename, @NotNull List<Record<Integer, String>> records) {
        try(OutputStream outputStream = new FileOutputStream(filename)) {
            for (Record<Integer, String> record : records) {
                byte[] bytes = int2Bytes(record.getId());
                outputStream.write(bytes);
                outputStream.write(record.getInfo().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读取记录
     * @param filename 文件名
     * @return 记录
     */
    public static @NotNull List<Record<Integer, String>> readRecord(String filename) {
        List<Record<Integer, String>> records = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(filename)) {
            byte[] bytes = new byte[16];
            while (inputStream.read(bytes) != -1) {
                records.add(bytes2Record(bytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    /**
     * 从start位置读取number条记录
     * @param file 文件
     * @param start 起始位置
     * @param number 记录条数
     * @return 记录
     */
    public static List<Record<Integer, String>> readRecord(File file, int start, int number) {
        List<Record<Integer, String>> records = new ArrayList<>();
        int all = 0;
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[16];
            if (inputStream.skip(start * 16) != start * 16)
                return records;
            while (inputStream.read(bytes) != -1) {
                records.add(bytes2Record(bytes));
                all++;
                if (all >= number)
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    /**
     * byte数组转记录
     * @param bytes 16位byte数组
     * @return 记录
     */
    public static Record<Integer, String> bytes2Record(byte[] bytes) {
        Record<Integer, String> record = new Record<>();
        byte[] idBytes = Arrays.copyOfRange(bytes, 0, 4);
        int id = bytes2Int(idBytes);
        record.setId(id);
        byte[] infoBytes = Arrays.copyOfRange(bytes, 4, bytes.length);
        String info = new String(infoBytes);
        record.setInfo(info);
        return record;
    }

    /**
     * 通过记录获取索引
     * @param filename 文件名
     * @return 索引格式记录
     */
    public static List<Record<Integer, Integer>> readAllIndex(String filename) {
        List<Record<Integer, Integer>> records = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(filename)) {
            byte[] bytes = new byte[16];
            int i = 0;
            while (inputStream.read(bytes) != -1) {
                Record<Integer, Integer> record = new Record<>();
                byte[] idBytes = Arrays.copyOfRange(bytes, 0, 4);
                int id = bytes2Int(idBytes);
                record.setId(id);
                record.setInfo(i);
                records.add(record);
                i = i + 16;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    /**
     * 通过索引读取文件
     * @param filename 文件名
     * @param index 索引
     * @return 记录
     */
    public static Record<Integer, String> readByIndex(String filename, Integer index) {
        if (index == null)
            return null;
        try (InputStream inputStream = new FileInputStream(filename)) {
            byte[] bytes = new byte[16];
            if (inputStream.skip(index) == index && inputStream.read(bytes) != -1) {
                return bytes2Record(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * int转byte数组
     * @param id 记录中的id
     * @return 四字节byte数组
     */
    @Contract(pure = true)
    public static byte @NotNull [] int2Bytes(int id)
    {
        byte[] bytes=new byte[4];
        bytes[3]=(byte) (id>>24);
        bytes[2]=(byte) (id>>16);
        bytes[1]=(byte) (id>>8);
        bytes[0]=(byte) id;

        return bytes;
    }

    /**
     * bytes数组转int
     * @param bytes 记录的前4字节
     * @return 还原的id属性
     */
    public static int bytes2Int(byte @NotNull [] bytes )
    {
        //如果不与0xff进行按位与操作，转换结果将出错，有兴趣的同学可以试一下。
        int int1=bytes[0]&0xff;
        int int2=(bytes[1]&0xff)<<8;
        int int3=(bytes[2]&0xff)<<16;
        int int4=(bytes[3]&0xff)<<24;

        return int1|int2|int3|int4;
    }
}
