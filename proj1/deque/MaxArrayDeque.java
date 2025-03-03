package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> comparator) {
        super();
        this.comparator = comparator;
    }
    public T max() {
        return max(comparator);
    }
    public T max(Comparator<T> comparator) {
        if (comparator == null) {
            return null;
        }
        if (isEmpty()) {
            return null;
        }
        T result = get(0);
        for (int i = 1; i < size(); i++) {
            T other = get(i);
            result = comparator.compare(result, other) < 0 ? other : result;
        }
        return result;
    }
}
