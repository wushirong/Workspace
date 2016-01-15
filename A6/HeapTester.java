import java.util.*;
import static org.junit.Assert.*;

import org.junit.Test;


public class HeapTester {

    @Test
    /** Test add with no duplicate priorities. */
    public void testAdd() {
        Heap<Integer> mh= new Heap<Integer>();
        mh.add(5, 5.0);
        assertEquals("[5:5.0]", mh.toString());
        assertEquals(1, mh.size());

        mh.add(7, 7.0);
        assertEquals("[5:5.0, 7:7.0]", mh.toString());
        assertEquals(2, mh.size());
        mh.add(3, 3.0);
        assertEquals("[3:3.0, 7:7.0, 5:5.0]", mh.toString());
        assertEquals(3, mh.size());
        mh.add(1, 1.0);
        assertEquals("[1:1.0, 3:3.0, 5:5.0, 7:7.0]", mh.toString());
        assertEquals(4, mh.size());
        mh.add(9, 9.0);
        assertEquals("[1:1.0, 3:3.0, 5:5.0, 7:7.0, 9:9.0]", mh.toString());
        assertEquals(5, mh.size());
        mh.add(2, 2.0);
        assertEquals("[1:1.0, 3:3.0, 2:2.0, 7:7.0, 9:9.0, 5:5.0]", mh.toString());
        assertEquals(6, mh.size());
        mh.add(0, 0.0);
        assertEquals("[0:0.0, 3:3.0, 1:1.0, 7:7.0, 9:9.0, 5:5.0, 2:2.0]", mh.toString());
        assertEquals(7, mh.size());
        
        // Test that exception is correctly thrown. Must be last test in this method
        mh= new Heap<Integer>();
        mh.add(5, 5.0);
        try {
            mh.add(5, 5.0);
        } catch (IllegalArgumentException e) {
            return;
        } catch (Throwable e){
            fail("Threw something other than IllegalArgumentException");
        }
        fail("Didn't throw an IllegalArgumentException");
    }

    @Test
    /** Test peek. */
    public void testPeek() {
        Heap<Integer> mh= new Heap<Integer>();
        mh.add(3, 3.0);
        assertEquals(new Integer(3), mh.peek());
        assertEquals("[3:3.0]", mh.toString());
        assertEquals(1, mh.size());
        mh.add(1, 1.0);
        assertEquals(new Integer(1), mh.peek());
        assertEquals("[1:1.0, 3:3.0]", mh.toString());
        assertEquals(2, mh.size());
        mh.add(-5, -5.0);
        assertEquals(new Integer(-5), mh.peek());
        assertEquals("[-5:-5.0, 3:3.0, 1:1.0]", mh.toString());
        assertEquals(3, mh.size());
        
        // Test peek throwing an exception. Must be last test in method
        mh= new Heap<Integer>();
        try {
            Integer k= mh.peek();
        } catch (PCueException e) {
            // This is supposed to happen
            return;
        } catch (Throwable e){
            fail("Threw something other than PCueException");
        }
        fail("Peek did not throw a PCueException");
    }

    @Test
    /** Test poll with no duplicate priorities. */
    public void testPollNoDups() {
        Heap<Integer> mh= new Heap<Integer>();
        mh.add(5, 5.0);
        Integer res= mh.poll();
        assertEquals(new Integer(5), res);
        assertEquals("[]", mh.toString());
        assertEquals("[]", mh.toStringPriorities());
        assertEquals(0, mh.size());

        mh= new Heap<Integer>();
        mh.add(5, 5.0);
        mh.add(3, 3.0);
        assertEquals("[3:3.0, 5:5.0]", mh.toString());
        assertEquals("[3.0, 5.0]", mh.toStringPriorities());
        res= mh.poll();
        assertEquals(new Integer(3), res);
        assertEquals("[5:5.0]", mh.toString());
        assertEquals("[5.0]", mh.toStringPriorities());
        assertEquals(1, mh.size());

        mh= new Heap<Integer>();
        mh.add(3, 3.0);
        mh.add(5, 5.0);
        mh.add(7, 7.0);
        assertEquals("[3:3.0, 5:5.0, 7:7.0]", mh.toString());
        assertEquals("[3.0, 5.0, 7.0]", mh.toStringPriorities());
        res= mh.poll();
        assertEquals(new Integer(3), res);
        assertEquals("[5:5.0, 7:7.0]", mh.toString());
        assertEquals(2, mh.size());

        mh= new Heap<Integer>();
        mh.add(3, 3.0);
        mh.add(7, 7.0);
        mh.add(5, 5.0);
        assertEquals("[3:3.0, 7:7.0, 5:5.0]", mh.toString());
        res= mh.poll();
        assertEquals(new Integer(3), res);
        assertEquals("[5:5.0, 7:7.0]", mh.toString());
        assertEquals(2, mh.size());

        int[] b= new int[] {0, 1, 2, 3, 7, 6, 4, 5, 8, 9, 10, 11, 15, 14, 13, 12};
        mh= new Heap<Integer>();
        for (int k= 0; k < b.length; k= k+1) {
            mh.add(b[k], b[k]);
        }

        for (int k= 0; k < b.length; k= k+1) {
            Integer result= mh.poll();
            assertEquals(new Integer(k), result);
        }
        
        // Test poll throwing an exception. Must be last test in procedure
        mh= new Heap<Integer>();
        try {
            Integer k= mh.poll();
        } catch (PCueException e) {
            // This is supposed to happen
            return;
        } catch (Throwable e){
            fail("Threw something other than PCueException");
        }
        fail("poll failed to throw a PCueExcetion");
    }

    @Test
    /** Test add and poll with duplicate priorities. */
    public void testAddAndPollWithDuplicatePriorites() {
        // The values to put in HeapSolution
        int[] b= new int[1000];
        for (int k= 0; k < b.length; k= k+1) {
            b[k]= k;
        }

        Random rand= new Random(52);

        // bp: priorities of the values
        double[] bp= new double[b.length];
        for (int k= 0; k < bp.length; k= k+1) {
            bp[k]= (int)(rand.nextDouble()*bp.length);
        }

        // Build the HeapSolution and map to be able to get priorities easily
        Heap<Integer> mh= new Heap<Integer>();
        HashMap<Integer, Double> hashMap= new HashMap<Integer, Double>();
        for (int k= 0; k < b.length; k= k+1) {
            mh.add(b[k], bp[k]);
            hashMap.put(b[k], bp[k]);
        }

        // poll values one by one, check that priorities are in order, store
        // in dups the number of duplicate priorities, and save polled value
        // in array bpoll.
        double prevPriority= -1;
        int dups= 0; //Number of duplicate keys,
        int[] bpoll= new int[b.length];
        for (int k= 0; k < b.length; k= k+1) {
            bpoll[k]= mh.poll();
            Double p= hashMap.get(bpoll[k]);
            if (p == prevPriority) {
                dups= dups + 1;
            }
            assertEquals(true, prevPriority <= p);
            prevPriority= p;
        }

        // Sort bpoll and check that it contains 0..bpoll.length-1
        Arrays.sort(bpoll);
        for (int k= 0; k < b.length; k= k+1) {
            assertEquals(k, bpoll[k]);
        }
        // System.out.println("duplicate priorities: " + dups);
    }

    @Test
    /** Test changePriority. */
    public void testchangePriority() {
        Heap<Integer> mh= new Heap<Integer>();
        mh.add(5, 5.0);
        mh.add(7, 7.0);
        mh.add(3, 3.0);
        mh.add(1, 1.0);
        mh.add(9, 9.0);
        mh.add(2, 2.0);
        mh.add(0, 0.0);
        assertEquals("[0:0.0, 3:3.0, 1:1.0, 7:7.0, 9:9.0, 5:5.0, 2:2.0]", mh.toString());
        assertEquals(7, mh.size());
        mh.changePriority(0, -1.0);
        assertEquals("[0:-1.0, 3:3.0, 1:1.0, 7:7.0, 9:9.0, 5:5.0, 2:2.0]", mh.toString());
        assertEquals(7, mh.size());
        mh.changePriority(0, 0.5);
        assertEquals("[0:0.5, 3:3.0, 1:1.0, 7:7.0, 9:9.0, 5:5.0, 2:2.0]", mh.toString());
        assertEquals(7, mh.size());
        mh.changePriority(3, 2.5);
        assertEquals("[0:0.5, 3:2.5, 1:1.0, 7:7.0, 9:9.0, 5:5.0, 2:2.0]", mh.toString());
        assertEquals(7, mh.size());
        mh.changePriority(3, 0);
        assertEquals("[3:0.0, 0:0.5, 1:1.0, 7:7.0, 9:9.0, 5:5.0, 2:2.0]", mh.toString());
        assertEquals(7, mh.size());
        mh.changePriority(3, 2.5);
        assertEquals("[0:0.5, 3:2.5, 1:1.0, 7:7.0, 9:9.0, 5:5.0, 2:2.0]", mh.toString());
        assertEquals(7, mh.size());
        mh.changePriority(3, 6.5);
        assertEquals("[0:0.5, 3:6.5, 1:1.0, 7:7.0, 9:9.0, 5:5.0, 2:2.0]", mh.toString());
        assertEquals(7, mh.size());
        mh.changePriority(3, 8.0);
        assertEquals("[0:0.5, 7:7.0, 1:1.0, 3:8.0, 9:9.0, 5:5.0, 2:2.0]", mh.toString());
        assertEquals(7, mh.size());
        mh.changePriority(1, 9.5);
        assertEquals("[0:0.5, 7:7.0, 2:2.0, 3:8.0, 9:9.0, 5:5.0, 1:9.5]", mh.toString());
        assertEquals(7, mh.size());

        // Make up an array of ints 1.1000, each k with priority -k.
        mh= new Heap<Integer>();
        for (int k= 1; k <= 1000; k= k+1) {
            mh.add(new Integer(k), -k);
        }

        // Change the priority of each k from -k to k
        for (int k= 1; k <= 1000; k= k+1) {
            mh.changePriority(new Integer(k), k);
        }

        // Now, the HeapSolution values should come out in increasing order
        for (int k= 1; k <= 1000; k= k+1) {
            assertEquals(new Integer(k), mh.poll());
        }
        
        // Check changePriority throwing an exception. Must be last test in procedure
        mh= new Heap<Integer>();
        mh.add(5, 5.0);
        try {
            mh.changePriority(6, 5.0);
        } catch (IllegalArgumentException e) {
            // This is supposed to happen
            return;
        } catch (Throwable e){
            fail("Threw something other than IllegalArgumentException");
        }
        fail("failed to throw an IllegalArgumentException");
    }

    @Test
    /** Try to test an Info error. This attempts to test something that is difficult to test:
     * that the Info.index fields are set properly. The index field is private, so
     * it can't be seen from the outside. In some people's programs, after bubbling
     * up or down, the index for the bubbled-up/down was not set. */
    public void testInfoError() {
        Heap<Integer> mh= new Heap<Integer>();
        mh.add(5, 5.0);
        mh.add(7, 7.0);
        assertEquals("[5:5.0, 7:7.0]", mh.toString());
        Integer p= mh.poll();
        assertEquals(new Integer(5), p);
        assertEquals("[7:7.0]", mh.toString());

        mh.changePriority(7, -1.0);
        assertEquals("[7:-1.0]", mh.toString());
        mh.changePriority(7, 2.0);
        assertEquals("[7:2.0]", mh.toString());

        mh.add(2, 1.0);
        assertEquals("[2:1.0, 7:2.0]", mh.toString());
    }
}