package de.o4.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public abstract class TextReader {
    public static List<String> read(String filepath){
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)))) {
            return reader.lines().toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
