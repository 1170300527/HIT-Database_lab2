package main.record;

public class Record {

    private int id;
    private String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", info='" + info + '\'' +
                '}';
    }
}
