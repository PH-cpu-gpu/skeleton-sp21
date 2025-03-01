package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private class Node {
        public Node prev;
        public T item;
        public Node next;
        public Node(Node prev, T item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
    private Node sentinel;
    private int size;
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
        Node current = sentinel.next;
        while (current != sentinel) {
            System.out.print(current.item + " ");
            current = current.next;
        }
        System.out.println();
    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node resultNode = sentinel.next;
        sentinel.next = resultNode.next;
        resultNode.next.prev = sentinel;
        size--;
        return resultNode.item;
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node resultNode = sentinel.prev;
        sentinel.prev = resultNode.prev;
        resultNode.prev.next = sentinel;
        size--;
        return resultNode.item;
    }
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        if (index * 2 <= size) {
            Node current = sentinel.next;
            while (index-- > 0) {
                current = current.next;
            }
            return current.item;
        } else {
            int reverseIndex = size - index - 1;
            Node current = sentinel.prev;
            while (reverseIndex-- > 0) {
                current = current.prev;
            }
            return current.item;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }
    private class LinkedListDequeIterator implements Iterator<T> {
        private Node current = sentinel.next;
        @Override
        public boolean hasNext() {
            return current != sentinel;
        }
        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            T result = current.item;
            current = current.next;
            return result;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkedListDeque)) {
            return false;
        }
        LinkedListDeque<?> other = (LinkedListDeque<?>) o;
        if (size != other.size()) {
            return false;
        }
        Iterator<T> thisIterator = iterator();
        Iterator<?> otherIterator = other.iterator();
        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            if (!thisIterator.next().equals(otherIterator.next())) {
                return false;
            }
        }
        return !thisIterator.hasNext() && !otherIterator.hasNext();
    }
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveRecursive(sentinel.next, index);
    }
    private T getRecursiveRecursive(Node current, int index) {
        if (index == 0) {
            return current.item;
        }
        return getRecursiveRecursive(current.next, index - 1);
    }
}
