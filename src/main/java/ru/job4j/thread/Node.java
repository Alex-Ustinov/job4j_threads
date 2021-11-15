package ru.job4j.thread;

public class Node<T> {
    private Node<T> next;
    private T value;

    Node () {}

    Node (Node<T> next, T value) {
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
