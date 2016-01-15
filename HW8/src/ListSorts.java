/* ListSorts.java */



public class ListSorts {

  private final static int SORTSIZE = 1000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
 * @throws QueueEmptyException 
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) throws QueueEmptyException {
	int length=q.size();
    LinkedQueue takeout=new LinkedQueue();
    
    for(int i=0;i<length;i++){
    takeout.enqueue(q.front());
    q.dequeue();
    }
    return takeout;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
 * @throws QueueEmptyException 
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) throws QueueEmptyException {
    LinkedQueue merge=new LinkedQueue();
    int i=0;
    int j=0;
    
    while(i<q1.size()||j<q2.size()){
    	if( (Integer)q1.front()<(Integer)q2.front()){
    		merge.enqueue(q1.front());
    		q1.dequeue();
    		i++;
    	}else{
    		merge.enqueue(q2.front());
    		q2.dequeue();
    		j++;
    	}
    }
    return null;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
 * @throws QueueEmptyException 
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) throws QueueEmptyException {
    int length=qIn.size();
    
    for(int i=0;i<length;i++){
    	if((Integer)qIn.front()<(Integer)pivot){
    		qSmall.enqueue(qIn.front());
    		qIn.dequeue();
    	}else if((Integer)qIn.front()==(Integer)pivot){
    		qEquals.enqueue(qIn.front());
    		qIn.dequeue();
    	}else{
    		qLarge.enqueue(qIn.front());
    		qIn.dequeue();
    	}
    }
	  // Your solution here.
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
 * @throws QueueEmptyException 
   **/
  public static void mergeSort(LinkedQueue q) throws QueueEmptyException {
	  LinkedQueue q1=new LinkedQueue();
	  LinkedQueue q2=new LinkedQueue();
	  
	  
	  
	  
	  
    // Your solution here.
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
 * @throws QueueEmptyException 
   **/
  public static void quickSort(LinkedQueue q) throws QueueEmptyException {
	  int pivot=(Integer)q.front();
	  int big=q.size()-1;
	  int small=0;
	  int i=0;
	  int temp=0;
	  
	  if(small<big)return;
	  while(i<=big){
		  if((Integer)q.nth(i)<pivot){
			  i++;
			  small++;
		  }else if((Integer)q.nth(i)>pivot){
			  temp=(Integer)q.nth(i);
			  exchange(q,i,big);
			  big--;
		  }else{
			  i++;
		  }
	  }
	  LinkedQueue qS=new LinkedQueue();
	  LinkedQueue qL=new LinkedQueue();
	  for(int k=0;k<small;k++){
		  qS.enqueue(q.front());
	  }
	  for(int k=big;k<q.size();k++){
		  qL.enqueue(q.nth(k));
	  }
	  quickSort(qS);
	  quickSort(qL);
	  for(int k=0;k<q.size();k++){
		  q.dequeue();
	  }
	  for(int k=0;k<qS.size();k++){
		  q.enqueue(qS.front());
		  qS.dequeue();
	  }
	  for(int k=0;k<(big-small);k++){
		  q.enqueue(pivot);
	  }  
	  for(int k=0;k<qL.size();k++){
		  q.enqueue(qL.front());
		  qL.dequeue();
	  }
    // Your solution here.
  }
  
public static void exchange(LinkedQueue q,int i, int j){
	int temp=(Integer)q.nth(i);
	
	SListNode node = q.head;
    for (; i > 1; i--) {
      node = node.next;
    }
    node.item=q.nth(j);
    SListNode node2 = q.head;
    for (; j > 1; j--) {
      node2 = node2.next;
    }
    node2.item=temp;
    return;
}
  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
 * @throws QueueEmptyException 
   **/
  public static void main(String [] args) throws QueueEmptyException {

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

    /* Remove these comments for Part III.
    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
    */
  }

}
