package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        Thread second = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        first.start();
        second.start();
        Boolean flag = true;

        while (flag) {
            if (first.getState() == Thread.State.TERMINATED && second.getState() == Thread.State.TERMINATED) {
                System.out.println("Both threads were finished");
                flag = false;
            }
        }
    }
}
