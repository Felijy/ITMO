package org.example;

import java.util.ArrayList;
import java.util.List;

public class Task2 {
    private final Integer[] keys;
    private final String[] values;
    private final int capacity;

    public final List<String> trace = new ArrayList<>();

    public Task2(int capacity) {
        this.capacity = capacity;
        this.keys = new Integer[capacity];
        this.values = new String[capacity];
    }

    public void put(int key, String value) {
        trace.add("START");

        int index = key % capacity;

        int startIndex = index;

        do {
            if (keys[index] == null) {
                trace.add("INSERT");
                keys[index] = key;
                values[index] = value;
                return;
            }

            if (keys[index].equals(key)) {
                trace.add("MATCH");
                values[index] = value;
                return;
            }

            trace.add("COLLISION");

            index = (index + 1) % capacity;

        } while (index != startIndex);

        trace.add("FULL");
    }

    public void clearTrace() {
        trace.clear();
    }
}
