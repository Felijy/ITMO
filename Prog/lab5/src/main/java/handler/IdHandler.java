package handler;

import java.util.LinkedList;
import java.util.Random;

public class IdHandler {

    private static LinkedList<Long> queue;

    static {
        queue = new LinkedList<>();
    }

    public static Long generateId() {
        Long id;
        do {
            id = new Random().nextLong(1, Long.MAX_VALUE);
        } while (queue.contains(id));
        queue.add(id);
        return id;
    }

    public static void addId(long id) {
        queue.add(id);
    }

    public static boolean validateId(Long id) {
        return !queue.contains(id);
    }

    public static Long getPrevId() {
        return queue.getLast();
    }
}
