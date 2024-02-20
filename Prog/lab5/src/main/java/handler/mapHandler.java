package handler;

import data.Coordinates;
import data.Ticket;
import data.Venue;
import data.VenueType;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Класс для работы непосредственно с коллекцией билетов
 */
public class mapHandler {
    /**
     * Поле, хранящее коллекцию со всеми элементами Ticket
     */
    private static HashMap<String, Ticket> collection;

    /**
     * Поле для хранения даты и времени инициализации коллекции
     */
    private static final LocalDateTime initTime;

    /**
     * Поле для хранения даты и времени последнего обращения к коллекции
     */
    private static LocalDateTime accessTime;

    static {
        setCollection(new HashMap<>());
        initTime = LocalDateTime.now();
    }

    /**
     * @return коллекцию
     */
    public static HashMap<String, Ticket> getCollection() {
        accessTime = LocalDateTime.now();
        return collection;
    }

    /**
     * Устанавливает новую коллекцию
     * @param collection новая коллекция
     */
    public static void setCollection(HashMap<String, Ticket> collection) {
        mapHandler.collection = collection;
    }

    /**
     * Метод для проверки наличия/отсутствия ключа в коллекции
     * @param key ключ, который нужно проверить
     * @return true, если ключ корректен и ещё не использовался в коллекции, false -- в обратом случае
     */
    public static boolean checkKey(String key) {
        return !collection.containsKey(key);
    }

    /**
     * Метод для создания нового элемента в коллекции (создание нового Ticket)
     * @param key ключ, который будет присвоен данному элементу
     * @param obj непосредственно элемент, который должен быть добавлен
     */
    public static void makeNewTicket(String key, Ticket obj) {
        collection.put(key, obj);
    }

    /**
     * Метод для удаления элемента из коллекции
     * @param key ключ элемента, который нужно удалить
     */
    public static void deleteElement(String key) {
        collection.remove(key);
    }

    public static LocalDateTime getInitTime() {
        return initTime;
    }

    public static LocalDateTime getAccessTime() {
        return accessTime;
    }
}
