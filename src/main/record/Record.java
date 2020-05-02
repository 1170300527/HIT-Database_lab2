package main.record;

import org.jetbrains.annotations.NotNull;

public class Record<K extends Comparable<K>, V> implements Comparable<K>{

    private K id;
    private V info;

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    public V getInfo() {
        return info;
    }

    public void setInfo(V info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", info='" + info + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NotNull K o) {
        return this.id.compareTo(o);
    }
}
