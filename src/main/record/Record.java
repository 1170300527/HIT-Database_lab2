package main.record;

import org.jetbrains.annotations.NotNull;

public class Record<K extends Comparable<K>, V>  implements Comparable<Record<K, V>> {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record<?, ?> record = (Record<?, ?>) o;

        if (getId() != null ? !getId().equals(record.getId()) : record.getId() != null) return false;
        return getInfo() != null ? getInfo().equals(record.getInfo()) : record.getInfo() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getInfo() != null ? getInfo().hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@NotNull Record<K, V> o) {
        return this.id.compareTo(o.getId());
    }
}
