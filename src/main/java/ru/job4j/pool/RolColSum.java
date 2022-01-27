package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public int getRowSum() {
            return rowSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums [] result = new Sums[matrix.length];
        for (int  i = 0; i < matrix.length; i++) {
            Sums sums = new Sums();
            for (int s = 0; s < matrix[i].length; s++) {
                int row = sums.getRowSum() + matrix[i][s];
                int col = sums.getColSum() + matrix[s][i];
                sums.setRowSum(row);
                sums.setColSum(col);
            }
            result[i] = sums;
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int matrixLength = matrix[0].length;
        Sums [] result = new Sums[matrixLength];
        Map<Integer, CompletableFuture<Sums>> tasks = new HashMap<>();

        for(int i = 0; i < matrixLength; i++) {
            tasks.put(i, taskCol(matrix, 0, matrixLength, i)
                    .thenCombine(taskRow(matrix, 0, matrixLength, i), (r1, r2) -> {
                        Sums sums = new Sums();
                        sums.setColSum(r1);
                        sums.setRowSum(r2);
                        return sums;
                    }
                )
            );
        }

        for (int key : tasks.keySet()) {
            result[key] = tasks.get(key).get();
        }
        return result;
    }

    public static CompletableFuture<Integer> taskCol(int[][] data, int startRow, int endRow, int col) {
        return CompletableFuture.supplyAsync(() -> {
            int sumCol = 0;
            for (int i = startRow; i < endRow; i++) {
                sumCol += data[i][col];
            }
            return sumCol;
        });
    }

    public static CompletableFuture<Integer> taskRow(int[][] data, int startCol, int endCol, int row) {
        return CompletableFuture.supplyAsync(() -> {
            int sumRow = 0;
            for (int i = startCol; i < endCol; i++) {
                sumRow += data[row][i];
            }
            return sumRow;
        });
    }
    
}
