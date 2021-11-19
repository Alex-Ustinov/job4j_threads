package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = (List) list.clone();
    }

    public void add(T value) {
        synchronized (this) {
            //list.add(new Object<T>(value));
        }
    }

    public T get(int index) {
        return null;
    }

    public List<T> copy (List<T> forCopy) {
        synchronized () {
            List<T> listCopy = new ArrayList<>();
            forCopy.forEach(item -> new T ().getClass());
        }
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}
