package ru.job4j.thread;

public final class Node<T> {
    private final Node<T> next;
    private final T value;

    Node(final Node<T> next, final T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }

}
