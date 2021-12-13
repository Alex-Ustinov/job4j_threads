package ru.job4j.pool;

import com.mchange.v1.util.ArrayUtils;

import java.util.Arrays;

public class SearchIndex<T> {
    private T user;

    SearchIndex (T user) {
        this.user = user;
    }
    public Integer search (T[] searchArea) {
        for (int i = 0; i < searchArea.length; i++) {
            if (searchArea[i] == user) {
                return i;
            }
        }
        return -1;
    }
}
