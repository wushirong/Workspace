/* Set.java */



/**
 *  A Set is a collection of Comparable elements stored in sorted order.
 *  Duplicate elements are not permitted in a Set.
 **/
public class Set extends DList {
  /* Fill in the data fields here. */
	protected int size;

  /**
   * Set ADT invariants:
   *  1)  The Set's elements must be precisely the elements of the List.
   *  2)  The List must always contain Comparable elements, and those elements 
   *      must always be sorted in ascending order.
   *  3)  No two elements in the List may be equals().
   **/

  /**
   *  Constructs an empty Set. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public Set() { 
	  super();
	  this.size=0;
  }

  /**
   *  cardinality() returns the number of elements in this Set.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int cardinality() {
    // Replace the following line with your solution.
    return size;
  }

  /**
   *  insert() inserts a Comparable element into this Set.
   *
   *  Sets are maintained in sorted order.  The ordering is specified by the
   *  compareTo() method of the java.lang.Comparable interface.
   *
   *  Performance:  runs in O(this.cardinality()) time.
   **/
  public void insert(Comparable c) {
	  if(!this.front().isValidNode()) {
		  this.insertFront(c);
		  size++;
		  return;
	  }
	  
	  DListNode dn=(DListNode)this.front();
	  while(dn.isValidNode()){
		  if(dn.item.equals(c)){
			  return;
		  }
		  else if(((Comparable)(dn.item)).compareTo(c)<0){
			  dn=dn.next;
		  }else{
			  DListNode newDnode=newNode(c,this,dn.prev,dn);
			  dn.prev.next=newDnode;
			  dn.prev=newDnode;
			  size++;
			  return;
		  }
	  }
	  this.insertBack(c);
	  size++;
    // Your solution here.
  }

  /**
   *  union() modifies this Set so that it contains all the elements it
   *  started with, plus all the elements of s.  The Set s is NOT modified.
   *  Make sure that duplicate elements are not created.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Your implementation should NOT copy elements of s or "this", though it
   *  will copy _references_ to the elements of s.  Your implementation will
   *  create new _nodes_ for the elements of s that are added to "this", but
   *  you should reuse the nodes that are already part of "this".
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
 * @throws InvalidNodeException 
   **/
  public void union(Set s) throws InvalidNodeException {
	  DListNode sP=(DListNode)s.front();
	  DListNode thisP=(DListNode)this.front();
	  
	  while(sP.isValidNode()&&thisP.isValidNode()){
		  if(((Comparable)sP.item()).compareTo(thisP.item())<0){
			  DListNode temp=newNode(sP.item(),this,thisP.prev,thisP);
			  thisP.prev.next=temp;
			  thisP.prev=temp;
			  sP=sP.next;
			  size++;
		  }else if(((Comparable)sP.item()).compareTo(thisP.item())>0){
			  thisP=thisP.next;
		  }else{
			  thisP=thisP.next;
			  sP=sP.next;
		  }
	  }
	  if(!sP.isValidNode()){
		  return;
	  }
	

	  //if sP is null, it does nothing. if thisP is null, it will connect the rest of sP
	  if(!thisP.isValidNode()){
		  while(sP.isValidNode()){
			  DListNode tail=(DListNode)this.back();
			  DListNode temp=newNode(sP.item(),this,tail,head);
			  tail.next=temp;
			  tail=temp;
			  sP=sP.next;
			  size++;
		  }
	   
	  }
  }

  /**
   *  intersect() modifies this Set so that it contains the intersection of
   *  its own elements and the elements of s.  The Set s is NOT modified.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Do not construct any new ListNodes during the execution of intersect.
   *  Reuse the nodes of "this" that will be in the intersection.
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT CONSTRUCT ANY NEW NODES.
   *  DO NOT ATTEMPT TO COPY ELEMENTS.
   **/
  public void intersect(Set s) {
	  DListNode sP=(DListNode)s.front();
	  DListNode thisP=(DListNode)this.front();
	  DListNode lastP=thisP.prev;
	  this.size=0;
	  
	  while(sP.isValidNode()&&thisP.isValidNode()){
		  
		  if(sP.item.equals(thisP.item)){
			  lastP=thisP;
			  thisP=thisP.next;
			  sP=sP.next; 
			  size++;
		  }
		  else if(((Comparable)sP.item).compareTo(thisP.item)<0){
			  sP=sP.next;
		  }
		  else{
			  thisP=thisP.next;
			  lastP.next=thisP;
			  thisP.prev=null;
			  thisP.prev=lastP;
			
		  }
		  
	  }
	  if(!thisP.isValidNode()){
		  return;
	  }
	  if (!sP.isValidNode()){
		  thisP=null;
		  lastP.next=head;
		  return;
	  }
    // Your solution here.
  }

  /**
   *  toString() returns a String representation of this Set.  The String must
   *  have the following format:
   *    {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
   *            between them.
   *    {  1  2  3  } for a Set of three Integer elements.  No spaces before
   *            "{" or after "}"; two spaces before and after each element.
   *            Elements are printed with their own toString method, whatever
   *            that may be.  The elements must appear in sorted order, from
   *            lowest to highest according to the compareTo() method.
   *
   *  WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
   *            FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
   *            DEVIATIONS WILL LOSE POINTS.
   **/
  public String toString() {
	  
	  StringBuilder current=new StringBuilder();
	  current.append("{");
	  DListNode temp=(DListNode) this.front();
	  
	  if (!temp.isValidNode()){
		  return current.toString();
	  }
	  else{
	  while(temp.isValidNode()){
		current.append(temp.item+" ");
		temp=temp.next;
	  }
	  current.append("}");
	  return current.toString();
	  }
    // Replace the following line with your solution.
  }

  public static void main(String[] argv) {
    Set s = new Set();
    s.insert(new Integer(3));
    s.insert(new Integer(4));
    s.insert(new Integer(3));
    s.insert(new Integer(1));
    s.insert(new Integer(2));
    s.insert(new Integer(9));
    
    System.out.println("Set s = " + s);

    Set s2 = new Set();
    s2.insert(new Integer(4));
    s2.insert(new Integer(5));
    s2.insert(new Integer(5));
    s2.insert(new Integer(4));
    s2.insert(new Integer(3));
    s2.insert(new Integer(1));
    System.out.println("Set s2 = " + s2);

    Set s3 = new Set();
    s3.insert(new Integer(5));
    s3.insert(new Integer(3));
    s3.insert(new Integer(8));
    s3.insert(new Integer(3));
    s3.insert(new Integer(1));
    s3.insert(new Integer(2));
    System.out.println("Set s3 = " + s3);

    try {
		s.union(s2);
	} catch (InvalidNodeException e) {
		e.printStackTrace();
	}
    System.out.println("After s.union(s2), s = " + s);
    System.out.println(s.cardinality());

    s.intersect(s3);
    System.out.println("After s.intersect(s3), s = " + s);

    System.out.println("s.cardinality() = " + s.cardinality());
    // You may want to add more (ungraded) test code here.
  }
}
