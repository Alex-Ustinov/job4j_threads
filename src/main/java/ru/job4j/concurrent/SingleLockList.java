package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = (List) list;
    }

    public void add(T value) {
        synchronized (this) {
        }
    }

    public T get(int index) {
        return null;
    }

    public List<T> copy (List<T> forCopy) {
        List<T> listCopy = new ArrayList<>();
        synchronized (this) {
        }
        return listCopy;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}
