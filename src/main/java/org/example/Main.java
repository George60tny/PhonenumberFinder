package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        validatePhoneNumbers();
        convertUsersToJson();
        countWordsFrequency();
    }

    public static void validatePhoneNumbers() {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("file.txt");

        if (inputStream == null) {
            System.err.println("Файл file.txt не найден.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (PhoneNumberValidator.isValid(line)) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void convertUsersToJson() {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("users.txt");

        if (inputStream == null) {
            System.err.println("Файл users.txt не найден.");
            return;
        }

        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            br.readLine(); // Пропустить первую строку с заголовками
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                User user = new User(tokens[0], Integer.parseInt(tokens[1]));
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(Paths.get("users.json").toFile(), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void countWordsFrequency() {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("words.txt");

        if (inputStream == null) {
            System.err.println("Файл words.txt не найден.");
            return;
        }

        Map<String, Integer> wordFrequency = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");

                for (String word : words) {
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        wordFrequency.entrySet().stream()
                .sorted((e1, e2) -> -e1.getValue().compareTo(e2.getValue()))
                .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }
}