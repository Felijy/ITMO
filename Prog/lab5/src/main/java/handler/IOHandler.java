package handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.*;

import java.io.*;

import data.Ticket;

import java.util.HashMap;
import java.util.Map;

public class IOHandler {

    /**
     * Переменная, которая хранит название JSON файла
     */
    private final String fileName;

    /**
     * Конструктор, устанавливающий название файла, которое поступает из переменной окружения
     * @param fileName
     */
    public IOHandler(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Метод для создания и сохранения JSON файла
     */
    public void makeJSON() {
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            byte[] buffer = objectMapper.writeValueAsBytes(mapHandler.getCollection());
            FileOutputStream fos = new FileOutputStream(System.getenv("lab5"));
            fos.write(buffer);
        } catch (IOException | NullPointerException e) {
            terminalHandler.printlnA("!!! Файл с коллекцией не может быть создан.");
        }
    }

    /**
     * Метод для чтения JSON файла
     *
     * @return HashMap, который был сохранен в JSON
     */
    public Map<String, Ticket> getJSON() {
        Map<String, Ticket> mapFromFile = new HashMap<>();
        try {
            BufferedReader bufr = new BufferedReader(new FileReader(System.getenv("lab5")));
            String finalLine = "";
            String line;
            while ((line = bufr.readLine()) != null) {
                finalLine = finalLine + line;
            }
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            mapFromFile = objectMapper.readValue(finalLine, new TypeReference<HashMap<String, Ticket>>() {});
        } catch (IOException | NullPointerException e) {
            terminalHandler.printlnA("!!!Файл с коллекцией не может быть загружен, либо содержит неверные данные. Будет использована пустая коллекция.");
        }
        return mapFromFile;
    }
}
