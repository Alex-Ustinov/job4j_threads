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

    @Override
    protected Integer compute() {
        Integer result = -1;
        if (searchIndexTo - searchIndexFrom <= 10) {
            for (int i = searchIndexFrom; i < searchIndexTo; i++) {
                if (items[i] == item) {
                    result = i;
                    break;
                }
            }
        } else {
            Integer midl = (searchIndexFrom - searchIndexTo) / 2;
            ParallelSearch parallelSearchFrom = new ParallelSearch(items, item, searchIndexFrom, midl);
            ParallelSearch parallelSearchTo = new ParallelSearch(items, item, midl + 1, searchIndexTo);
            parallelSearchFrom.fork();
            parallelSearchTo.fork();
        }
        return result;
    }
    public Integer search() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return  forkJoinPool.invoke(new ParallelSearch<T>(items, item, 0 , items.length));
    }
}
