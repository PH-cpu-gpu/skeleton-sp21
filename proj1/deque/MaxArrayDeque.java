package deque;
import java.util.Comparator;
public class MaxArrayDeque<T> extends ArrayDeque<T>{
    Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> comparator) {
        super();
        this.comparator = comparator;
    }
    public T max() {
        return findMax(this.comparator);
    }
    public T max(Comparator<T> comparator) {
        return findMax(comparator);
    }
    private T findMax(Comparator<T> comparator) {
        if (isEmpty()) {
            return null;
        }
        T maxValue = array[0];
        for (int i = 1; i < size(); i++) {
            maxValue = comparator.compare(maxValue, array[i]) <= 0 ? maxValue : array[i];
        }
        return maxValue;
    }
}
