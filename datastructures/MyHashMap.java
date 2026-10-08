package datastructures;

import model.CollegeActivity;

public class MyHashMap {

    // Entry class for key-value pair
    private class Entry {

        int key;
        CollegeActivity value;
        Entry next;

        Entry(int key, CollegeActivity value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Entry[] table;

    // Constructor
    public MyHashMap() {

        table = new Entry[10];
    }

    // HASH FUNCTION
    private int hash(int key) {

        return Math.abs(key) % table.length;
    }

    // PUT
    public void put(int key, CollegeActivity value) {

        int index = hash(key);

        Entry current = table[index];

        // Check if key already exists
        while (current != null) {

            if (current.key == key) {

                current.value = value;

                return;
            }

            current = current.next;
        }

        // Create new entry
        Entry newEntry =
                new Entry(key, value);

        // Add to beginning of bucket
        newEntry.next = table[index];

        table[index] = newEntry;
    }

    // GET
    public CollegeActivity get(int key) {

        int index = hash(key);

        Entry current = table[index];

        while (current != null) {

            if (current.key == key) {

                return current.value;
            }

            current = current.next;
        }

        return null;
    }

    // REMOVE
    public boolean remove(int key) {

        int index = hash(key);

        Entry current = table[index];

        Entry previous = null;

        while (current != null) {

            if (current.key == key) {

                // Removing first entry
                if (previous == null) {

                    table[index] = current.next;

                } else {

                    previous.next = current.next;
                }

                return true;
            }

            previous = current;

            current = current.next;
        }

        return false;
    }
}