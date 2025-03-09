package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author PulsarHaze
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private Collection<Node>[] buckets;
    private int size;
    private double maxLoadFactor;
    private Set<K> keySet;

    /** Constructors */
    public MyHashMap() {
        this(INITIAL_CAPACITY, LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        super();
        size = 0;
        buckets = createTable(initialSize);
        maxLoadFactor = maxLoad;
        keySet = new HashSet<>();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<Node>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] buckets = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            buckets[i] = createBucket();
        }
        return buckets;
    }
    //方法
    @Override
    public void clear() {
        keySet.clear();
        for (Collection<Node> bucket : buckets) {
            bucket.clear();
        }
        size = 0;
    }
    @Override
    public boolean containsKey(K key) {
        int index = hash(key);
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public V get(K key) {
        int index = hash(key);
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void put(K key, V value) {
        if (size + 1 > maxLoadFactor * buckets.length) {
            resize(buckets.length * 2);
        }
        int index = hash(key);
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        buckets[index].add(new Node(key, value));
        size++;
        keySet.add(key);
    }
    @Override
    public V remove(K key) {
        V value = get(key);
        return value == null ? null : remove(key, value);
    }
    @Override
    public V remove(K key, V value) {
        int index = hash(key);
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                buckets[index].remove(node);
                keySet.remove(node.key);
                size--;
                return node.value;
            }
        }
        return null;
    }
    @Override
    public Set<K> keySet() {
        return keySet;
    }
    //迭代器内部类及其方法
    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }
    //辅助方法
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % buckets.length;
    }
    private void resize(int newSize) {
        Collection<Node>[] newBuckets = createTable(newSize);
        for (K key : keySet) {
            int index = hash(key);
            newBuckets[index].add(new Node(key, get(key)));
        }
        buckets = newBuckets;
    }
}