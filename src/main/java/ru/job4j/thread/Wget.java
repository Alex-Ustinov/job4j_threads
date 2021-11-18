package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }
    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long bytesWrote = 0;
            long deltaTime = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                bytesWrote = bytesWrote + in.read();
                if (bytesWrote >= speed) {
                    System.out.println("bytesWrote = " + bytesWrote);
                    int spentTime = (int) System.nanoTime() / 1000000 - (int) deltaTime / 1000000 ;
                    System.out.println("spentTime = " + spentTime);
                    if (spentTime < 1000) {
                        int sleep = 1000 - spentTime;
                        System.out.println("Sleep = " + sleep);
                        Thread.sleep(1000 - spentTime);
                    }
                    deltaTime = System.nanoTime();
                    bytesWrote = 0;
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        System.out.println("The file was downloaded");
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
