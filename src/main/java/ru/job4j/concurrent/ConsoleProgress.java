package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        String flag = "\\";
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
                System.out.print("\r load: " + flag);

                if (flag.equals("\\")) {
                    flag = "|";
                } else if (flag.equals("|")) {
                    flag = "/";
                } else if (flag.equals("/")) {
                    flag = "\\";
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
