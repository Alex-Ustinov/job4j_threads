package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ParallelSearchTest {
    @Test
    public void searchTest() {
        Integer[] testArray = new Integer[50];
        for (int i = 0; i < 50; i++) {
            testArray[i] = i;
        }
        ParallelSearch parallelSearch = new ParallelSearch(testArray, 34, 0, testArray.length - 1);
        assertThat(parallelSearch.search(), is(-1));
    }
}