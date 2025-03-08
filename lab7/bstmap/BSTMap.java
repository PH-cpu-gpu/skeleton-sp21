package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    //节点内部类
    private class BSTNode {
        public K key;
        public V value;
        public BSTNode left;
        public BSTNode right;
        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    //实例字段
    BSTNode root;
    int size;

    //空参构造方法
    public BSTMap() {
        root = null;
        size = 0;
    }

    //增删改查
    public void put(K key, V value) {
        root = putNode(root, key, value);
    }
    public V get(K key) {
        BSTNode node = getNode(root, key);
        return node == null ? null : node.value;
    }
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        V removedValue = get(key);
        root = removeNode(root, key);
        size--;
        return removedValue;
    }
    public V remove(K key, V value) {
        V oldValue = get(key);
        if (oldValue == null) {
            return null;
        }
        if (!oldValue.equals(value)) {
            return null;
        }
        return remove(key);
    }

    //各种方法
    public void clear() {
        root = null;
        size = 0;
    }
    public boolean containsKey(K key) {
        BSTNode node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return true;
            }
        }
        return false;
    }
    public void printInOrder() {
        print(root);
    }
    public int size() {
        return size;
    }
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        getKeySets(root, keySet);
        return keySet;
    }

    //辅助方法
    private BSTNode putNode(BSTNode node, K key, V value) {
        if (node == null) {
            size++;
            return new BSTNode(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = putNode(node.left, key, value);
        } else if (cmp > 0) {
            node.right = putNode(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }
    private BSTNode getNode(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return getNode(node.left, key);
        } else if (cmp > 0) {
            return getNode(node.right, key);
        } else {
            return node;
        }
    }
    private void print(BSTNode node) {
        if (node == null) {
            return;
        }
        print(node.left);
        System.out.println(node.key + ": " + node.value);
        print(node.right);
    }
    private BSTNode removeNode(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = removeNode(node.left, key);
        } else if (cmp > 0) {
            node.right = removeNode(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            BSTNode minNode = minNode(node.right);
            node.key = minNode.key;
            node.value = minNode.value;
            node.right = removeNode(node.right, node.key);
        }
        return node;
    }
    private BSTNode minNode(BSTNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }
    private BSTNode getKeySets(BSTNode root, Set<K> sets) {
        if (root == null) {
            return null;
        }
        getKeySets(root.left, sets);
        sets.add(root.key);
        getKeySets(root.right, sets);
        return root;
    }

    //迭代器内部类和相关方法
    private class BSTIterator implements Iterator<K> {
        Stack<BSTNode> stack = new Stack<>();
        private void pushNode(BSTNode node) {
            if (node == null) {
                return;
            }
            pushNode(node.right);
            stack.push(node);
            pushNode(node.left);
        }
        public BSTIterator() {
            pushNode(root);
        }
        public boolean hasNext() {
            return !stack.isEmpty();
        }
        public K next() {
            if (stack.isEmpty()) {
                return null;
            }
            BSTNode node = stack.pop();
            return node.key;
        }
    }
    public Iterator<K> iterator() {
        return new BSTIterator();
    }
}