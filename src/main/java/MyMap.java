public interface MyMap<K, V> {
    public V put(K key, V value);
    public V get(K key);
    public V remove(K key);
    public interface Entry<K, V> {
        public K getKey();
        public V getValue();
    }
}
