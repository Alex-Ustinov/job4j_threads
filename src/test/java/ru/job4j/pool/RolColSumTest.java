package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RolColSumTest {
    @Test
    public void sumColTest() {
        int[] one = {1, 1, 1};
        int[] two = {2, 2, 2};
        int[] three = {3, 3, 3};
        int[][] fore = {one, two, three};
        RolColSum.Sums[] res = RolColSum.sum(fore);
        Integer rowSum = res[2].getRowSum();
        Integer colSum =  res[2].getColSum();
        assertThat(rowSum, is(9));
        assertThat(colSum, is(6));
    }

    @Test
    public void asyncSumColTest() throws ExecutionException, InterruptedException {
        int[] one = {1, 1, 1};
        int[] two = {2, 2, 2};
        int[] three = {3, 3, 3};
        int[][] fore = {one, two, three};
        RolColSum.Sums[] res = RolColSum.asyncSum(fore);
        Integer rowSum = res[2].getRowSum();
        Integer colSum =  res[2].getColSum();
        assertThat(rowSum, is(9));
        assertThat(colSum, is(6));
    }
}