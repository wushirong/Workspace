/* Tree234.java */


/**
 *  A Tree234 implements an ordered integer dictionary ADT using a 2-3-4 tree.
 *  Only int keys are stored; no object is associated with each key.  Duplicate
 *  keys are not stored in the tree.
 *
 *  @author Jonathan Shewchuk
 **/
public class Tree234 extends IntDictionary {

  /**
   *  (inherited)  size is the number of keys in the dictionary.
   *  root is the root of the 2-3-4 tree.
   *
   *  You may add fields if you wish, but don't change anything that
   *  would prevent toString() or find() from working correctly.
   **/
  Tree234Node root;


  public Tree234() {
    root = null;
    size = 0;
    level=0;
  
  }

  /**
   *  toString() prints this Tree234 as a String.  Each node is printed
   *  in the form such as (for a 3-key node)
   *
   *      (child1)key1(child2)key2(child3)key3(child4)
   *
   *  where each child is a recursive call to toString, and null children
   *  are printed as a space with no parentheses.  Here's an example.
   *      ((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of the 2-3-4 tree.
   **/
  public String toString() {
    if (root == null) {
      return "";
    } else {
      return root.toString();
    }
  }

  /**
   *  printTree() prints this Tree234 as a tree, albeit sideways.
   *
   *  You're welcome to change this method if you like.  It won't be tested.
   **/
  public void printTree() {
    if (root != null) {
      root.printSubtree(0);
    }
  }

  /**
   *  find() prints true if "key" is in this 2-3-4 tree; false otherwise.
   *
   *  @param key is the key sought.
   *  @return true if "key" is in the tree; false otherwise.
   **/
  public boolean find(int key) {
    Tree234Node node = root;
    while (node != null) {
      if (key < node.key1) {
        node = node.child1;
      } else if (key == node.key1) {
        return true;
      } else if ((node.keys == 1) || (key < node.key2)) {
        node = node.child2;
      } else if (key == node.key2) {
        return true;
      } else if ((node.keys == 2) || (key < node.key3)) {
        node = node.child3;
      } else if (key == node.key3) {
        return true;
      } else {
        node = node.child4;
      }
    }
    return false;
  }

  /**
   *  insert() inserts the key "key" into this 2-3-4 tree.  If "key" is
   *  already present, a duplicate copy is NOT inserted.
   *
   *  @param key is the key sought.
   **/
  
  private boolean isfull(Tree234Node node){
	  if(node.keys==3){
		  return true;
	  }
	  return false;
  }
   private boolean isleaf(Tree234Node node){
	   if(node.child1==null&&node.child2==null&&node.child3==null&&node.child4==null){
		   return true;
	   }
	   return false;
   }
   
  private void insertkey(Tree234Node node,int key){
	  
		  if(node.key1==key||node.key2==key){
				  return;
		  }
		  if(node.key1>key){
			  node.key3=node.key2;
			  node.key2=node.key1;
			  node.key1=key;
		  }else if(node.key2>key||node.key2==0){
			  node.key3=node.key2;
			  node.key2=key;
			  }
		  else{
			  node.key3=key;
		  }
		  node.keys++;
		  return;
  }
  
  
  private Tree234Node nextlevel(Tree234Node parent,int key){
	  if(parent.child1==null&&parent.child2==null&&parent.child3==null&&parent.child4==null){
		  level++;
	  }
	  
		  if(parent.key1>key){
			  if(parent.child1==null){
				  parent.child1=new Tree234Node(parent,0);
			  }
			  
			  return parent.child1;
			  }
		  else if(parent.key2>key||parent.key2==0){
			  if(parent.child2==null){
				  parent.child2=new Tree234Node(parent,0);
			  }
			 
			  return parent.child2;
		  }
		  else if(parent.key3>key||parent.key3==0){
			  if(parent.child3==null){
				  parent.child3=new Tree234Node(parent,0);
			  }
			 
			  return parent.child3;
		  }
		  if(parent.child4==null){
			  parent.child4=new Tree234Node(parent,0);
		  }

		  return parent.child4;			  
  }

  private Tree234Node restructure(Tree234Node node){
	  Tree234Node temp=node.parent;

	  if(node.parent==null){
		  root=new Tree234Node(null,node.key2);
		  root.child1=new Tree234Node(root,node.key1);
		  root.child2=new Tree234Node(root,node.key3);
		  root.child1.child1=node.child1;
		  if(node.child1!=null)
		  node.child1.parent=root.child1;
		  root.child1.child2=node.child2;
		  if(node.child2!=null)
		  node.child2.parent=root.child1;
		  root.child2.child1=node.child3;
		  if(node.child3!=null)
		  node.child3.parent=root.child2;
		  root.child2.child2=node.child4;
		  if(node.child4!=null)
		  node.child4.parent=root.child2;
		  level++;
		  return root;
	  }else{
		  insertkey(temp, node.key2);
		  /*
		  temp.child1=new Tree234Node(temp,node.key1);
		  temp.child2=new Tree234Node(temp,node.key3);
		  temp.child1.child1=node.child1;
		  temp.child1.child2=node.child2;
		  temp.child2.child1=node.child3;
		  temp.child2.child2=node.child4;
		  if(node.child1!=null)
			  node.child1.parent=temp.child1;
		  if(node.child2!=null)
			  node.child2.parent=temp.child1;
		  if(node.child3!=null)
			  node.child3.parent=temp.child2;
		  if(node.child4!=null)
			  node.child4.parent=temp.child2;
		  */
		  if(node.equals(temp.child1)){
			  temp.child4=temp.child3;
			  if(temp.child4!=null)
				  temp.child4.parent=temp;
			  temp.child3=temp.child2; 
			  if(temp.child3!=null)
				  temp.child3.parent=temp;
			  temp.child2=new Tree234Node(temp,node.key3);
			  temp.child1=new Tree234Node(temp,node.key1);
			  
			  temp.child1.child1=node.child1;
			  temp.child1.child2=node.child2;
			  temp.child2.child1=node.child3;
			  temp.child2.child2=node.child4;
			  if(node.child1!=null)
				  node.child1.parent=temp.child1;
			  if(node.child2!=null)
				  node.child2.parent=temp.child1;
			  if(node.child3!=null)
				  node.child3.parent=temp.child2;
			  if(node.child4!=null)
				  node.child4.parent=temp.child2;
		  }else if(node.equals(temp.child2)){
			  temp.child4=temp.child3;
			  temp.child3=new Tree234Node(temp,node.key3);
			  temp.child2=new Tree234Node(temp,node.key1);
			  if(temp.child4!=null)
				  temp.child4.parent=temp;
			  
			  temp.child2.child1=node.child1;
			  temp.child2.child2=node.child2;
			  temp.child3.child1=node.child3;
			  temp.child3.child2=node.child4;
			  if(node.child1!=null)
				  node.child1.parent=temp.child2;
			  if(node.child2!=null)
				  node.child2.parent=temp.child2;
			  if(node.child3!=null)
				  node.child3.parent=temp.child3;
			  if(node.child4!=null)
				  node.child4.parent=temp.child3;
		  }else{
			  temp.child3=new Tree234Node(temp,node.key1);
			  temp.child4=new Tree234Node(temp,node.key3);
			  temp.child3.child1=node.child1;
			  temp.child3.child2=node.child2;
			  temp.child4.child1=node.child3;
			  temp.child4.child2=node.child4;
			  if(node.child1!=null)
				  node.child1.parent=temp.child3;
			  if(node.child2!=null)
				  node.child2.parent=temp.child3;
			  if(node.child3!=null)
				  node.child3.parent=temp.child4;
			  if(node.child4!=null)
				  node.child4.parent=temp.child4;
		  }
	  }
	  return temp;
	 // function to recomstruct node
  }
  public void insert(int key){
	  int curL=1;
	  Tree234Node node=root;
	  Tree234Node temp;
	  
	  if(isEmpty()){
		  root=new Tree234Node(null,key);
		  size++;
		  level++;
		  return;
	  }
	  
	  if(isfull(node)){
		  node=restructure(node);
	  }
	  while(curL<level){
		  if(isfull(node)){
			  node=restructure(node);
		  }
		  node=nextlevel(node,key);
		  curL++;
		  
	  }
	  if(isfull(node)){
		  node=restructure(node);  
		  node=nextlevel(node,key);
	  }
	  if(!isleaf(node)){
		  node=nextlevel(node,key);
	  }
	  insertkey(node,key);
	  return;
  }
  /**
   *  testHelper() prints the String representation of this tree, then
   *  compares it with the expected String, and prints an error message if
   *  the two are not equal.
   *
   *  @param correctString is what the tree should look like.
   **/
  public void testHelper(String correctString) {
    String treeString = toString();
    System.out.println(treeString);
    if (!treeString.equals(correctString)) {
      System.out.println("ERROR:  Should be " + correctString);
    }
  }

  /**
   *  main() is a bunch of test code.  Feel free to add test code of your own;
   *  this code won't be tested or graded.
   **/
  public static void main(String[] args) {
    Tree234 t = new Tree234();

    System.out.println("\nInserting 84.");
    t.insert(84);
    t.testHelper("84");

    System.out.println("\nInserting 7.");
    t.insert(7);
    t.testHelper("7 84");

    System.out.println("\nInserting 22.");
    t.insert(22);
    t.testHelper("7 22 84");

    System.out.println("\nInserting 95.");
    t.insert(95);
    t.testHelper("(7)22(84 95)");

    System.out.println("\nInserting 50.");
    t.insert(50);
    t.testHelper("(7)22(50 84 95)");

    System.out.println("\nInserting 11.");
    t.insert(11);
    t.testHelper("(7 11)22(50 84 95)");

    System.out.println("\nInserting 37.");
    t.insert(37);
    t.testHelper("(7 11)22(37 50)84(95)");

    System.out.println("\nInserting 60.");
    t.insert(60);
    t.testHelper("(7 11)22(37 50 60)84(95)");

    System.out.println("\nInserting 1.");
    t.insert(1);
    t.testHelper("(1 7 11)22(37 50 60)84(95)");

    System.out.println("\nInserting 23.");
    t.insert(23);
    t.testHelper("(1 7 11)22(23 37)50(60)84(95)");

    System.out.println("\nInserting 16.");
    t.insert(16);
    t.testHelper("((1)7(11 16)22(23 37))50((60)84(95))");

    System.out.println("\nInserting 100.");
    t.insert(100);
    t.testHelper("((1)7(11 16)22(23 37))50((60)84(95 100))");

    System.out.println("\nInserting 28.");
    t.insert(28);
    t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(95 100))");

    System.out.println("\nInserting 86.");
    t.insert(86);
    t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(86 95 100))");

    System.out.println("\nInserting 49.");
    t.insert(49);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))");

    System.out.println("\nInserting 81.");
    t.insert(81);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((60 81)84(86 95 100))");

    System.out.println("\nInserting 51.");
    t.insert(51);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86 95 100))");

    System.out.println("\nInserting 99.");
    t.insert(99);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86)95(99 100))");

    System.out.println("\nInserting 75.");
    t.insert(75);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(75 81)84(86)95" +
                 "(99 100))");

    System.out.println("\nInserting 66.");
    t.insert(66);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(66 75 81))84((86)95" +
                 "(99 100))");

    System.out.println("\nInserting 4.");
    t.insert(4);
    t.testHelper("((1 4)7(11 16))22((23)28(37 49))50((51)60(66 75 81))84" +
                 "((86)95(99 100))");

    System.out.println("\nInserting 80.");
    t.insert(80);
    t.testHelper("(((1 4)7(11 16))22((23)28(37 49)))50(((51)60(66)75" +
                 "(80 81))84((86)95(99 100)))");

    System.out.println("\nFinal tree:");
    t.printTree();
  }

}
