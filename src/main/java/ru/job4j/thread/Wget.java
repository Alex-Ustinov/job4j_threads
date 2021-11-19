package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    public static void validateArguments(String[] arguments) throws Exception {
        try {
            if (arguments.length != 2) {
                throw new Exception("Array arguments does not contain enough arguments");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String[] partUrls = url.split("/");
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(partUrls[partUrls.length - 1])) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long bytesWrote = 0;
            long deltaTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                bytesWrote = bytesWrote + bytesRead;
                if (bytesWrote >= speed) {
                    long spentTime = System.currentTimeMillis() - deltaTime;
                    if (spentTime < 1000) {
                        long sleep = 1000 - spentTime;
                        Thread.sleep(sleep);
                    }
                    deltaTime = System.currentTimeMillis();
                    bytesWrote = 0;
                }
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        System.out.println("The file was downloaded");
    }

    public static void main(String[] args) throws InterruptedException, Exception {
        validateArguments(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
