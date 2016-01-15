/* HashTableChained.java */

package dict;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

	protected int size;
	protected EntryNode[] buckets;
	private int collisions;
	
  /**
   *  Place any data fields here.
   **/



  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
	  buckets=new EntryNode[sizeEstimate];
	  this.size=0;
    // Your solution here.
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
	  this(101);
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
	  int hashcode=Math.abs((127*code+13)% 1679917 % buckets.length);
	  
    // Replace the following line with your solution.
    return hashcode;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
	  
    // Replace the following line with your solution.
    return size==0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
	  Entry temp=new Entry();
	  temp.key=key;
	  temp.value=value;
	  
	  int hashcode=key.hashCode();
	  hashcode=compFunction(hashcode);
	  EntryNode exist=buckets[hashcode];
	  
	  if(exist==null){
	  buckets[hashcode]=new EntryNode(temp,null);
	  size++;
	  return temp;
	  }
	  while(exist.item!=null){
		  if(exist.item.equals(temp)){
			  return null;
		  }
		  else if(exist.next==null){
			  EntryNode newE=new EntryNode(temp,null);
			  exist.next=newE;
			  size++;
			  collisions++;
			  return temp;
		  }
	  }
	  
	  
    return null;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
	  int hashcode=key.hashCode();
	  hashcode=compFunction(hashcode);
	  EntryNode temp=buckets[hashcode];
	  
	  while(temp!=null){
		  if(temp.item.key.equals(key)){
			  return temp.item;
		  }
		  temp=temp.next;
	  }
    // Replace the following line with your solution.
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
	  int hashcode=key.hashCode();
	  hashcode=compFunction(hashcode);
	  
	  
	  while(buckets[hashcode]!=null){
		  EntryNode pre=buckets[hashcode];
		  if(pre.item.key.equals(key)){
			  buckets[hashcode]=pre.next;
			  size--;
			  return pre.item;
		  }
		  EntryNode cur=pre.next;
		  if(cur.item.key.equals(key)){
			  pre.next=cur.next;
			  size--;
			  return cur.item;
		  }
		  pre=pre.next;
		  cur=cur.next;
	  }
	  
    // Replace the following line with your solution.
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
	  buckets=new EntryNode[buckets.length];
  }
  
  public int collisions(){
	  return collisions;
  }
  
  public static void main(String[] args){
	  //test constructor without parameter
	  HashTableChained htc1=new HashTableChained();
	  System.out.println("No parameter's constructor size: "+htc1.size+"\n"
			  			+"buckets' length:"+htc1.buckets.length);
	  
	  
	  //test constructor with parameter
	  HashTableChained htc2=new HashTableChained(201);
	  System.out.println("With parameter's constructor size: "+htc2.size+"\n"
			  			+"buckets' length:"+htc2.buckets.length);
	  
	  //test insert
	  for(int i=0;i<26;i++){
		  char key=(char)('A'+i);
		  htc1.insert(key, i);  
	  }
	  System.out.println("\n\nAfter insert 26 Characters HTC1's size: "+htc1.size);
	  for(int i=0,count=0;i<htc1.buckets.length;i++,count++){
		  if(count!=0&&count%5==0)
			  System.out.println();
		  System.out.print("buckets["+i+"]: "+htc1.buckets[i]+" | ");
	  }
	  
	  htc1.insert('A', 1);
	  System.out.println("\n\nAfter insert [A,1] HTC1's size: "+htc1.size);
	  for(int i=0,count=0;i<htc1.buckets.length;i++,count++){
		  if(count!=0&&count%5==0)
			  System.out.println();
		  System.out.print("buckets["+i+"]: "+htc1.buckets[i]+" | ");
	  }
	  
	  htc1.remove('A');
	  System.out.println("\n\nAfter remove A HTC1's size: "+htc1.size);
	  for(int i=0,count=0;i<htc1.buckets.length;i++,count++){
		  if(count!=0&&count%5==0)
			  System.out.println();
		  System.out.print("buckets["+i+"]: "+htc1.buckets[i]+" | ");
	  }
	  
	  Entry findEntry=htc1.find('A');
	  System.out.println("\n\nHTC1's size: "+htc1.size);
	  System.out.println("Find A Entry is: "+findEntry+"\n");
  }

}
