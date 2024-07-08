package com.example.healthfirst;

import java.io.*;

public class FileHandler {

    public static void writeToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            return null;
        }
        return content.toString();
    }

    public static void writeMessageToFile(String fileName, String sender, String message) {
        String formattedMessage = String.format("%s: %s", sender, message);
        writeToFile(fileName, formattedMessage);
    }

    public static String readMessagesFromFile(String fileName) {
        return readFromFile(fileName);
    }
}
