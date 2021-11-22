package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = (List) copy(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    public synchronized List<T> copy (List<T> forCopy) {
        List<T> listCopy = new ArrayList<>();
        while (forCopy.iterator().hasNext()) {
            listCopy.add(forCopy.iterator().next());
        }
        return listCopy;
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }
}
