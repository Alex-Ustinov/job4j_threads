package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

public class ParserData implements Parser {

    private final File file;

    ParserData(File file) {
        this.file = file;
    }

    @Override
    public String content(Predicate predicate) {
        String output = "";
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) > 0) {
                Character character = (char) data;
                if (predicate.test(character)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
