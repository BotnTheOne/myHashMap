public class MyHashMapTest {
    public static void main(String[] args) {
        MyMap<String, String> dnmap = new MyHashMap<String, String>();

        Long t1 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            dnmap.put("key" + i, "value" + i);
        }

        for (int i = 0; i < 10000; i++) {
            System.out.println("key: " + "key" + i + "  value:"
                               + dnmap.get("key" + i));
        }
    }
}
