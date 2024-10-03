import java.util.ArrayList;
import java.util.List;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private static int defaultLength = 16;
    private static double defaultLoader = 0.75;
    private Entry<K, V>[] table = null;
    private int size = 0;

    public MyHashMap(int length, double loader) {
        defaultLength = length;
        defaultLoader = loader;
        table = new Entry[defaultLength];
    }

    public MyHashMap() {
        this(defaultLength, defaultLoader);
    }

    @Override
    public V put(K k, V v) {
        if (size >= defaultLength * defaultLoader) {
            up2size();
        }

        int index = getIndex(k);

        Entry<K, V> entry = table[index];

        if (entry == null) {
            table[index] = newEntry(k, v, null);
            size++;
        } else {
            table[index] = newEntry(k, v, entry);
        }
        return table[index].getValue();
    }

    private void up2size() {
        Entry<K, V>[] newTable = new Entry[2 * defaultLength];
        againHash(newTable);
    }

    private void againHash(Entry<K, V>[] newTable) {

        List<Entry<K, V>> list = new ArrayList<>();

        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }
            findEntryByNext(table[i], list);
        }

        if (list.size() > 0) {
            size = 0;
            defaultLength = defaultLength * 2;
            table = newTable;

            for (Entry<K, V> entry : list) {
                if (entry.next != null) {
                    entry.next = null;
                }
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    private void findEntryByNext(Entry<K, V> entry, List<Entry<K, V>> list) {

        if (entry != null && entry.next != null) {
            list.add(entry);
            findEntryByNext(entry.next, list);
        } else {
            list.add(entry);
        }
    }

    private Entry<K, V> newEntry(K k, V v, Entry<K, V> next) {
        return new Entry(k, v, next);
    }

    private int getIndex(K k) {
        int m = defaultLength;
        int index = k.hashCode() % m;
        return index >= 0 ? index : -index;
    }

    public V get(K k) {
        int index = getIndex(k);
        if (table[index] == null) {
            return null;
        }
        return findValueByEqualKey(k, table[index]);
    }

    @Override
    public V remove(K k) {
        throw new UnsupportedOperationException("Данная операция пока недоступна");
    }

    public V findValueByEqualKey(K k, Entry<K, V> entry) {

        if (k == entry.getKey() || k.equals(entry.getKey())) {
            return entry.getValue();
        } else {
            if (entry.next != null) {
                return findValueByEqualKey(k, entry.next);
            }
        }

        return null;
    }

    public int size() {
        return size;
    }


    class Entry<K, V> implements MyMap.Entry<K, V> {
        K k;
        V v;
        Entry<K, V> next;

        public Entry(K k, V v, Entry<K, V> next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }

        public K getKey() {
            return k;
        }

        public V getValue() {
            return v;
        }
    }
}