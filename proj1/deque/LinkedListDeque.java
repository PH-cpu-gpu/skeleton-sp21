package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private class LinkedListIterator implements Iterator<T> {
        Node currentNode = sentinel.next;
        @Override
        public boolean hasNext() {
            return currentNode != sentinel;
        }
        @Override
        public T next() {
            T result = currentNode.item;
            currentNode = currentNode.next;
            return result;
        }
    }
    private class Node {
        Node prev;
        T item;
        Node next;
        public Node(Node prev, T item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
    private int size;
    private Node sentinel;
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    public void addFirst(T item) {
        Node newNode = new Node(sentinel, item, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }
    public void addLast(T item) {
        Node newNode = new Node(sentinel.prev, item, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        Node currentNode = sentinel.next;
        while (currentNode != sentinel) {
            System.out.print(currentNode.item + " ");
            currentNode = currentNode.next;
        }
        System.out.println();
    }
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node removedNode = sentinel.next;
        sentinel.next = removedNode.next;
        removedNode.next.prev = sentinel;
        T result = removedNode.item;
        removedNode.prev = null;
        removedNode.item = null;
        removedNode.next = null;
        size--;
        return result;
    }
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node removedNode = sentinel.prev;
        sentinel.prev = removedNode.prev;
        removedNode.prev.next = sentinel;
        T result = removedNode.item;
        removedNode.prev = null;
        removedNode.item = null;
        removedNode.next = null;
        size--;
        return result;
    }
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node currentNode = sentinel.next;
        while (index > 0) {
            currentNode = currentNode.next;
            index--;
        }
        return currentNode.item;
    }
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<?> other = (Deque<?>) o;
        if (size() != other.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (!get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursive(sentinel.next, index);
    }
    private T getRecursive(Node currentNode, int index) {
        if (index == 0) {
            return currentNode.item;
        }
        return getRecursive(currentNode.next, index - 1);
    }
}
