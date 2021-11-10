package ru.job4j.concurrent;

public class ThreadSecondStop {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println("start ...");
                            System.out.println(Thread.currentThread().getName());
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            System.out.println(Thread.currentThread().isInterrupted());
                            System.out.println(Thread.currentThread().getState());
                        }
                    }
                }
        );
        System.out.println(Thread.currentThread().getName());
        progress.start();
        System.out.println("---- " + Thread.currentThread().getName());
        Thread.sleep(1000);
        System.out.println("^^^^^^^ " + Thread.currentThread().getName());
        progress.interrupt();
        System.out.println("&&&&&& " + Thread.currentThread().getName());
        //progress.join();
    }
}
