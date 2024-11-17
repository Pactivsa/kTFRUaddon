package cn.kuzuanpa.ktfruaddon.code;

import java.util.Map;

public class SingleEntry<K, V> implements Map.Entry<K, V> {
    K key;
    V value;

    public SingleEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public V setValue(V value) {
        V ret = this.value;
        this.value = value;
        return ret;
    }

    public boolean equals(Object paramObject) {
        if (!(paramObject instanceof SingleEntry)) {
            return super.equals(paramObject);
        } else {
            return ((SingleEntry<?, ?>)paramObject).getKey().equals(this.getKey()) && ((SingleEntry<?, ?>)paramObject).getValue().equals(this.getValue());
        }
    }
}
