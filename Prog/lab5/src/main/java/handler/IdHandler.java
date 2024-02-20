package handler;

import java.util.LinkedList;
import java.util.Random;

/**
 * Класс для выдачи, валидации и хранения ID
 */
public class IdHandler {

    /**
     * Коллекция для хранения всех выданных ID
     */
    private static LinkedList<Long> queue;

    static {
        queue = new LinkedList<>();
    }

    /**
     * Метод для генерации нового ID и добавления его в коллекцию выданных
     * @return новый ID
     */
    public static Long generateId() {
        Long id;
        do {
            id = new Random().nextLong();
        } while (queue.contains(id));
        queue.add(id);
        return id;
    }

    /**
     * Метод для добавления в коллекцию выданных команд элемента (используется при ручном добавлении ID, а также при загрузки из файла)
     * @param id ID, который нужно добавить
     */
    public static void addId(long id) {
        queue.add(id);
    }

    /**
     * Метод для валидации ID на наличие в списке уже выданных
     * @param id ID, который нужно проверить
     * @return true, если ID уникальный и новый, false -- в обратном случае
     */
    public static boolean validateId(Long id) {
        return !queue.contains(id);
    }
}
