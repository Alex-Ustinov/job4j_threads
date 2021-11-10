package ru.job4j.concurrent;

import java.util.ArrayList;
import java.util.List;

public class ThreadSecondStop {
    public static void main(String[] args) throws InterruptedException {
        List<String> symbols = new ArrayList(List.of("\\", "|", "/"));
        Thread progress = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            for (String symbol : symbols) {
                                Thread.sleep(500);
                                System.out.print("\r load: " + symbol);
                            }
                            //System.out.println(Thread.currentThread().getName());
                            //Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            System.out.println(Thread.currentThread().isInterrupted());
                            System.out.println(Thread.currentThread().getState());
                        }
                    }
                }
        );
//        System.out.println(Thread.currentThread().getName());
        progress.start();
//        System.out.println("---- " + Thread.currentThread().getName());
        Thread.sleep(1000);
//        System.out.println("^^^^^^^ " + Thread.currentThread().getName());
        progress.interrupt();
//        System.out.println("&&&&&& " + Thread.currentThread().getName());
        //progress.join();
    }
}
