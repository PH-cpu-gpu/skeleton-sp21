package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private static final int INITIAL_CAPACITY = 8; // 初始数组大小
    private static final double SHRINK_THRESHOLD = 0.25; // 缩容阈值

    private T[] items;
    private int size;
    private int front;
    private int rear;

    public ArrayDeque() {
        items = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
        front = 0;
        rear = 0;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2); // 扩容
        }
        front = (front - 1 + items.length) % items.length;
        items[front] = item;
        size++;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2); // 扩容
        }
        items[rear] = item;
        rear = (rear + 1) % items.length;
        size++;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T removedItem = items[front];
        items[front] = null; // 帮助 GC
        front = (front + 1) % items.length;
        size--;
        checkAndShrink();
        return removedItem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        rear = (rear - 1 + items.length) % items.length;
        T removedItem = items[rear];
        items[rear] = null; // 帮助 GC
        size--;
        checkAndShrink();
        return removedItem;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(front + index) % items.length];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
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

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
        items = newItems;
        front = 0;
        rear = size;
    }

    private void checkAndShrink() {
        if (items.length >= 16 && size < items.length * SHRINK_THRESHOLD) {
            resize(items.length / 2);
        }
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return get(index++);
        }
    }
}
