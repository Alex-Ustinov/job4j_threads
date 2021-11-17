package ru.job4j.thread;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {

    private final File file;
    private final Parser parser;
    private final ParserSave parserSave;

    ParseFile(final File file, Parser parser) {
        this.file = file;
        this.parser = parser;
        this.parserSave = new ParserSave(file);
    }

    public String getContent() {
        Predicate<Character> predicate = character -> true;
        return parser.content(predicate);
    }

    public String getContentWithoutUnicode() {
        Predicate<Character> predicate = character -> character != (char) 0x80;
        return parser.content(predicate);
    }

    public void saveContent (String string) {
         parserSave.saveContent(string);
    }

}
