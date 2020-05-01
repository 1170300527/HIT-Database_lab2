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
    public static void writeRecord(String filename, @NotNull List<Record> records) {
        try(OutputStream outputStream = new FileOutputStream(filename)) {
            for (Record record : records) {
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
    public static @NotNull List<Record> readRecord(String filename) {
        List<Record> records = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(filename)) {
            byte[] bytes = new byte[16];
            while (inputStream.read(bytes) != -1) {
                Record record = new Record();
                byte[] idBytes = Arrays.copyOfRange(bytes, 0, 4);
                int id = bytes2Int(idBytes);
                record.setId(id);
                byte[] infoBytes = Arrays.copyOfRange(bytes, 4, bytes.length);
                String info = new String(infoBytes);
                record.setInfo(info);
                records.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
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
