package ru.job4j.thread;

import java.util.function.Predicate;

public interface Parser {
    public String content (Predicate predicate);
}
