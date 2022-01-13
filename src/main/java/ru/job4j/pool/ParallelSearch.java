package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private T[] items;
    private T item;
    private Integer searchIndexFrom;
    private Integer searchIndexTo;

    ParallelSearch(T[] items, T item, Integer searchIndexFrom, Integer searchIndexTo) {
        this.items = items;
        this.item = item;
        this.searchIndexFrom = searchIndexFrom;
        this.searchIndexTo = searchIndexTo;
    }
    private Integer find() {
        for (int i = searchIndexFrom; i <= searchIndexTo; i++) {
            if (items[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Integer compute() {
        if (searchIndexTo - searchIndexFrom <= 10) {
            return find();
        }
        Integer midl = (searchIndexFrom - searchIndexTo) / 2;
        ParallelSearch parallelSearchFrom = new ParallelSearch(items, item, searchIndexFrom, midl);
        ParallelSearch parallelSearchTo = new ParallelSearch(items, item, midl + 1, searchIndexTo);
        parallelSearchFrom.fork();
        parallelSearchTo.fork();

        Integer searchFor = (Integer) parallelSearchFrom.join();
        Integer searchTo = (Integer) parallelSearchTo.join();

        return searchTo > searchFor ? searchTo : searchTo;
    }
    public Integer search() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return  forkJoinPool.invoke(new ParallelSearch<T>(items, item, 0 , items.length - 1));
    }
}
