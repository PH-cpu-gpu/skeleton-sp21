package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestThreeAddThreeRemove {
    @Test
    public void test() {
        AListNoResizing correct = new AListNoResizing();
        BuggyAList broken = new BuggyAList();
        correct.addLast(4);
        correct.addLast(5);
        correct.addLast(6);
        broken.addLast(4);
        broken.addLast(5);
        broken.addLast(6);
        assertEquals(correct.size(), broken.size());
        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
    }
    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> b = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                b.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size2 = b.size();
                System.out.println("size: " + size);
                assertEquals(size, size2);
            } else if (operationNumber == 2) {
                if (L.size() == 0) {
                    continue;
                }
                Integer removeNumber = L.removeLast();
                Integer removeNumber2 = b.removeLast();
                assertEquals(removeNumber, removeNumber2);
            }
        }
    }
}
