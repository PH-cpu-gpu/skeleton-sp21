package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private final int DEFAULT_CAPACITY = 8;
    private final double GROWTH_FACTOR = 2.0;
    private final double SHRINK_THRESHOLD = 0.25;
    private final double SHRINK_FACTOR = 0.5;
    private T[] array;
    private int size;
    //first指向首元素
    private int first;
    //last指向下一个要插入的位置
    private int last;
    public ArrayDeque() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
        first = 0;
        last = 0;
        size = 0;
    }
    private void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(first + i) % array.length];
        }
        array = newArray;
        first = 0;
        last = size;
    }
    public void addFirst(T item) {
        if (size == array.length) {
            resize((int)(array.length * GROWTH_FACTOR));
        }
        first = (first - 1 + array.length)% array.length;
        array[first] = item;
        size++;
    }
    public void addLast(T item) {
        if (size == array.length) {
            resize((int)(array.length * GROWTH_FACTOR));
        }
        array[last] = item;
        last = (last + 1) % array.length;
        size++;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(array[(first + i) % array.length] + " ");
        }
        System.out.println();
    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (size < array.length * SHRINK_THRESHOLD) {
            resize((int)(array.length * SHRINK_FACTOR));
        }
        T result = array[first];
        first = (first + 1) % array.length;
        size--;
        return result;
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (size < array.length * SHRINK_THRESHOLD) {
            resize((int)(array.length * SHRINK_FACTOR));
        }
        last = (last - 1 + array.length)% array.length;
        T result = array[last];
        size--;
        return result;
    }
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return array[(first + index) % array.length];
    }
    public class ArrayDequeIterator implements Iterator<T> {
        int now = first;
        @Override
        public boolean hasNext() {
            return first != last;
        }
        @Override
        public T next() {
            T result = array[now];
            now = (now + 1) % array.length;
            return result;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayDeque)) {
            return false;
        }
        ArrayDeque<?> other = (ArrayDeque<?>) o;
        if (size != other.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!(array[(first + i) % array.length].equals(other.array[(other.first + i) % array.length]))) {
                return false;
            }
        }
        return true;
    }
}
