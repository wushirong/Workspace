/** An implementation implements a priority queue whose elements are of type E.
 *  Below, N is used as the number of elements currently in the priority queue.
 *  Duplicate elements are not allowed.
 *  The priorities are double values. */
public interface PCue<E> {

	/** Return a string that represents this priority queue, in the format:
	 * [item0:priority0, item1:priority1, ..., item(N-1):priority(N-1)]
	 * Thus, the list is delimited by '['  and ']' and ", " (i.e. a
	 * comma and a space char) separate adjacent items. */
	String toString();
	
	/** Return the number of elements in the priority queue. */
	int size();
    
    /** Add e with priority p to the priority queue.
     *  Throw an illegalArgumentException if e is already in the queue. */ 
    void add(E e, double priority) throws IllegalArgumentException;

    /** Return the element of the priority queue with lowest priority, without
     *  changing the priority queue.
     *  Throw a PCueException with message "priority queue is empty" if the
     *     priority queue is empty. */
    E peek();

    /** Remove and return the element of the priority queue with lowest priority.
	 *  Throw a PCueException with message "priority queue is empty" if the
     *     priority queue is empty. */
	E poll();
	
	/** Change the priority of element e to p.
	 *  Throw an illegalArgumentException if e is not in the priority queue. */
	void changePriority(E e, double p);
}
