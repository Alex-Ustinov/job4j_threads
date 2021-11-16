package ru.job4j.thread;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile implements Parser, ParserSave {
    private final File file;

    Parse parse;

    ParseFile(final File file, Parse parse) {
        this.file = file;
        this.parse = parse;
    }

    public String getContent() {
        String output = "";
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) > 0) {
                Character character = (char) data;
                if (parse.execute(character)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public void saveContent(String content) {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
