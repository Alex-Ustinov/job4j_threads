package ru.job4j.thread;

public class DCLSingleton {
    private static volatile DCLSingleton inst;

    public static DCLSingleton instOf() {
        DCLSingleton localDCLSingleton = inst;
        if (localDCLSingleton == null) {
            synchronized (DCLSingleton.class) {
                localDCLSingleton = inst;
                if (localDCLSingleton == null) {
                    localDCLSingleton = inst = new DCLSingleton();
                }
            }
        }
        return localDCLSingleton;
    }

    private DCLSingleton() {
    }
}
