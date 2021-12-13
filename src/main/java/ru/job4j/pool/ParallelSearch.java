package ru.job4j.pool;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private T[] users;
    private T user;
    private SearchIndex searchIndex;

    ParallelSearch(T[] users, T user, SearchIndex searchIndex) {
        this.users = users;
        this.user = user;
        this.searchIndex = searchIndex;
    }

    @Override
    protected Integer compute() {
        if (users.length <= 10) {
            for (int i = 0; i < users.length; i++) {
                if (users[i] == user) {
                    return i;
                }
            }
        }

        int middle =  Math.round(users.length / 2);

        T[] firstPartSearch = Arrays.copyOfRange(users, 0, middle);

        T[] rowBack = (T[])(new Object[users.length - 1 - middle]);
        for (int i  = users.length - 1;  middle <= i; i--) {
            rowBack[i] = users[i];
        }
        ParallelSearch parallelSearch1 = new ParallelSearch(firstPartSearch, user, new SearchIndex(user));
        ParallelSearch parallelSearch2 = new ParallelSearch(rowBack, user, new SearchIndex(user));
        parallelSearch1.fork();
        parallelSearch2.fork();

        parallelSearch1.join();
        parallelSearch1.join();

        int rsl1 = searchIndex.search(firstPartSearch);
        int rsl2 = searchIndex.search(rowBack);
        if(rsl1 > 0) {
            return rsl1;
        } else {
           return  rsl2;
        }
    }
}
