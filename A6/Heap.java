/* Time spent on a6:  hh hours and mm minutes.

 * Name(s):
 * Netid(s): 
 * What I thought about this assignment: 
 * 
 *
 *
 */

import java.util.*;

/** An instance is a priority queue of elements of type E
 * implemented as a min-heap. */
public class Heap<E> implements PCue<E> {

    private int size; // number of elements in the priority queue (and heap)

    /** The heap invariant is given below. Note that / denotes int division.
     * 
     *  b[0..size-1] is viewed as a min-heap, i.e. 
     *  1. Each array element in b[0..size-1] contains a value of the heap.
     *  2. The children of each b[i] are b[2i+1] and b[2i+2].
     *  3. The parent of each b[i] (except b[0]) is b[(i-1)/2].
     *  4. The priority of the parent of each b[i] is <= the priority of b[i].
     *  5. Priorities for the b[i] used for the comparison in point 4
     *     are given in map. map contains one entry for each element of
     *     the heap, and map and b have the same size.
     *     For each element e in the heap, the map entry contains in the
     *     Info object the priority of e and its index in b.
     */
    private ArrayList<E> b= new ArrayList<E>();
    private HashMap<E, Info> map= new HashMap<E, Info>();

    /** Constructor: an empty heap. */
    public Heap() {
    }

    /** Return a string that gives this priority queue, in the format:
     * [item0:priority0, item1:priority1, ..., item(N-1):priority(N-1)]
     * Thus, the list is delimited by '['  and ']' and ", " (i.e. a
     * comma and a space char) separate adjacent items. */
    @Override public String toString() {
        String s= "[";
        for (E t : b) {
            if (s.length() > 1) {
                s = s + ", ";
            }
            s = s + t + ":" + map.get(t).priority;
        }
        return s + "]";
    }

    /** Return a string that gives the priorities in this priority queue,
     * in the format: [priority0, priority1, ..., priority(N-1)]
     * Thus, the list is delimited by '['  and ']' and ", " (i.e. a
     * comma and a space char) separate adjacent items. */
    public String toStringPriorities() {
        String s= "[";
        for (E t : b) {
            if (s.length() > 1) {
                s = s + ", ";
            }
            s = s + map.get(t).priority;
        }
        return s + "]";
    }

    /** Return the number of elements in the priority queue.
     *  This operation takes constant time. */
    @Override public int size() {
        return size;
    }

    /** Add e with priority p to the priority queue.
     *  Throw an illegalArgumentException if e is already in the queue.
     *  The expected time is O(log N) and the worst-case time is O(N). */ 
    @Override public void add(E e, double p) throws IllegalArgumentException {
        // TODO  First: Do add and bubbleUp together.
    	b.add(e);
    	Info newInfo = new Info(b.size(), p);
    	size++;
    	if(map.containsKey(e)) throw new IllegalArgumentException();
    	map.put(e, newInfo);
    	bubbleUp(b.size() - 1);
    }

    /** Return the element of the priority queue with lowest priority, without
     *  changing the queue. This operation takes constant time.
     *  Throw a PCueException with message "priority queue is empty" if the
     *     priority queue is empty. */
    @Override public E peek() {
        // TODO  Second: Do peek.
    	if(b == null || b.size() == 0) throw new PCueException("priority queue is empty");  	
        return b.get(0);
    }
    
    /** Remove and return the element of the priority queue with lowest priority.
     *  The expected time is O(log n) and the worst-case time is O(N).
     *  Throw a PCueException with message "priority queue is empty" if the
     *     priority queue is empty. */
    @Override public E poll() {
        // TODO  THIRD: Do poll and bubbleDown together.
    	if(b == null || b.size() == 0) throw new PCueException("priority queue is empty"); 
    	E res = b.remove(0);
    	size --;
    	if(b.size() == 0) return res;
    	b.add(0, b.remove(b.size() - 1));
    	bubbleDown(0);
        return res;
    }

    /** Change the priority of element e to p.
     *  The expected time is O(log N) and the worst-case is time O(N).
     *  Throw an illegalArgumentException if e is not in the priority queue. */
    @Override public void changePriority(E e, double p) {
        // TODO  FOURTH: Do changePriority.
        if(!b.contains(e)) throw new IllegalArgumentException();
        map.get(e).priority = p;
        bubbleUp(map.get(e).index);
        bubbleDown(map.get(e).index);
    }


    /** Bubble b[k] up in heap to its right place.
     * Precondition: Every b[i] satisfies the heap property except perhaps
     *       k's priority < parent's priority */
    private void bubbleUp(int k) {
        // TODO  First: Do add and bubbleUp together.
        // Do not use recursion; iteration is best.
    	int index = k;  
    	if(b.size() <= 1) return;
    	while(index >= 0) {
    		int paIndex = (index - 1)/2;
    		E cur = b.get(index);
        	E par = b.get(paIndex);
        	if(map.get(cur).priority < map.get(par).priority){
        		E temp = b.get(paIndex);
        		b.set(paIndex, cur);
        		b.set(index, temp);
        		map.get(cur).index = b.indexOf(cur);
        		map.get(par).index = b.indexOf(par);
        		index = paIndex;
        	}
        	else return;
    	}
    }

    /** Bubble b[k] down in heap until it finds the right place.
     * Precondition: Every b[i] satisfies the heap property except perhaps
     * k's priority > a child's priority. */
    private void bubbleDown(int k) {
        // TODO  THIRD: Do poll and bubbleDown together.
        // Do not use recursion; iteration is best.
    	if(b.size() <= 1 || b.size() == k + 1) return;
    	E cur= b.get(k);
    	int index = k;
    	while(b.size() > 2 * index + 1) {
    		int child = getSmallerChild(index);
    		E childE = b.get(child);
    		if(getPriority(cur) > getPriority(childE)) {
        		E temp = childE;
        		b.set(child, cur);
        		b.set(index, temp);
        		map.get(cur).index = b.indexOf(cur);
        		map.get(temp).index = b.indexOf(temp);
        		index = child;
        		cur = b.get(index);
    		}
    		else return;
//    		E child = b.get(2 * index + 1);
//    		if(getPriority(cur) > getPriority(child)) {
//        		
//        		E temp = child;
//        		b.set(2 * index + 1, cur);
//        		b.set(index, temp);
//        		map.get(cur).index = b.indexOf(cur);
//        		map.get(temp).index = b.indexOf(temp);
//        		index = 2 * index + 1;
//        		cur = b.get(index);
//        	}
//    		else if(b.size() > 2 * index + 1 ) {
//    			child = b.get(2 * index + 2);
//    			if(getPriority(cur) > getPriority(child)) {
//    				
//    				E temp = child;
//            		b.set(2 * index + 1, cur);
//            		b.set(index, temp);
//            		map.get(cur).index = b.indexOf(cur);
//            		map.get(temp).index = b.indexOf(temp);
//            		index = 2 * index + 1;
//            		cur = b.get(index);
//    			}
//    			else return;
//    		}
    	}
    }

    /** Return the index of the smaller child of b[q]
     * Precondition: left child exists: 2q+1 < size of heap */
    private int getSmallerChild(int q) {
    	E res = null;
        if(2 * q + 1 < b.size()) res = b.get(2 * q + 1);
        if(2 * q + 2 < b.size() && map.get(res).priority > map.get(b.get(2 * q + 2)).priority)
        	res = b.get(2 * q + 2);
        return b.indexOf(res);
    }

    /** An instance contains the index, value, and priority of an element of the heap. */
    private static class Info {
        private int index;  // index of this element in map
        private double priority; // priority of this element

        /** Constructor: an instance in b[i] with priority p. */
        private Info(int i, double p) {
            index= i;
            priority= p;
        }
        
    }
    
    private double getPriority(E e) {
    	double res = map.get(e).priority;
    	return res;
    }
    
}