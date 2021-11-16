package ru.job4j.thread;

import java.util.function.Predicate;

public class ParseUnicode implements Parse {
    @Override
    public Boolean execute(Character character) {
        Predicate<Character> predicate = simbol -> simbol != 0x80;
        if (predicate.test(character)) {
            return false;
        }
        return true;
    }
}
