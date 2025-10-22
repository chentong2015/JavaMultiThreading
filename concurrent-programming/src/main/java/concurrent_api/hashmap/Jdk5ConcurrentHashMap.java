package concurrent_api.hashmap;

import java.util.concurrent.ConcurrentHashMap;

// TODO. ConcurrentHashMap诞生自JDK 5版本, 在7&8版本做了加强
public class Jdk5ConcurrentHashMap {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        System.out.println(map.get("key1"));
    }
}
