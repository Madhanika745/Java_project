package datastructures;

import java.util.HashMap;
import java.util.Map;

import model.CollegeActivity;

public class MyHashMap {

    private Map<Integer, CollegeActivity> map;

    // Constructor
    public MyHashMap() {
        map = new HashMap<>();
    }

    // PUT (replaces value if key already exists)
    public void put(int key, CollegeActivity value) {
        map.put(key, value);
    }

    // GET (null if not found)
    public CollegeActivity get(int key) {
        return map.get(key);
    }

    // REMOVE
    public boolean remove(int key) {

        if (map.containsKey(key)) {
            map.remove(key);
            return true;
        }

        return false;
    }
}