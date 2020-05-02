# 实验二 数据库索引及查询算法实现

## 一、实验目的

掌握B树索引查找算法，多路归并排序算法，并用高级语言实现

## 二、实验环境

- java
- Windows 10
- IDEA

## 三、实验过程及结果

### 1. 数据生成

1. 数据结构：包含4字节int与16字节的字符串

   ```java
   private int id;
   private String info;
   ```

2. 随机生成数据函数：输入生成数据数量，返回生成的list，id由Random随机生成，字符串由THISIS与第几条数据构成

   ```java
   public static List<Record> generateRecord(Integer number) {
       List<Record> records = new ArrayList<>();
       Random rand = new Random();
       for (int i = 0; i < number; i++) {
           Record entry = new Record();
           entry.setId(rand.nextInt());
           entry.setInfo("THISIS" + String.format("%06d", i) + "");
           records.add(entry);
       }
       return records;
   }
   ```

3. 生成文本记录：将int与string转为byte数组（int2Bytes为自定义函数），通过OutputStream字节流写入文件

   ```java
   byte[] bytes = int2Bytes(entry.getId());
   outputStream.write(bytes);
   outputStream.write(entry.getInfo().getBytes());
   ```

4. 读取文本记录：使用InputStream读入文件，每次读入一条记录的长度16字节，将前4个字节转为int，剩余字节转为string（bytes2Int为自定义函数），恢复record

   ~~~java
   byte[] bytes = new byte[16];
   while (inputStream.read(bytes) != -1) {
       Record entry = new Record();
       byte[] idBytes = Arrays.copyOfRange(bytes, 0, 4);
       int id = bytes2Int(idBytes);
       entry.setId(id);
       byte[] infoBytes = Arrays.copyOfRange(bytes, 4, bytes.length);
       String info = new String(infoBytes);
       entry.setInfo(info);
       records.add(entry);
   }
   ~~~

### 2. B树

### 3. 多路归并排序

### 4. 实验结果

1. 数据生成：

   1. 1000000条数据每个16字节共15.2Mb

      ![data](https://s1.ax1x.com/2020/04/29/J7SPOI.jpg)

   2. 生成与读取数据10条为例：读写数据相同

      ![writeRecord](https://s1.ax1x.com/2020/04/29/J7Ck01.jpg)

      ![readRecord](https://s1.ax1x.com/2020/04/29/J7CFmR.jpg)

2. B树

3. 多路归并排序

### 5. 效率分析

1）截屏保留实验结果

 2）附上程序代码

 3）给出对程序效率的分析