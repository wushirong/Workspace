/* NetId(s): nnnnn, nnnnn. Time spent: hh hours, mm minutes.

 * Name(s):
 * What I thought about this assignment:
 *
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** An instance of DiseaseTree represents the spreading of a Disease among a Network of people.
 * In this model, each person can "catch" the disease from only a single person.
 * The  root of the DiseaseTree is the person who first got the disease. From there,
 * each person in the DiseaseTree is the child of the person who gave them the disease.
 * For example, for the tree:
 * 
 *        A
 *       / \
 *      B   C
 *       / \
 *      D   E
 * 
 * Person A originally got the disease, B and C caught the disease from A,
 *  and D and E caught the disease from C.
 *  
 *  Important note: The name of each person in the disease tree is unique. 
 * @author Mshnik */
public class DiseaseTree {

    /** The String to be used as a separator in toString() */
    public static final String SEPARATOR= " - ";

    /** The String that marks the start of children in toString() */
    public static final String START_CHILDREN_DELIMITER= "[";

    /** The String that divides children in toString() */
    public static final String DELIMITER= ", ";

    /** The String that marks the end of children in toString() */
    public static final String END_CHILDREN_DELIMITER= "]";

    /** The String that is the space increment in toStringVerbose() */
    public static final String VERBOSE_SPACE_INCREMENT= "\t";

    /**  The person at the root of this DiseaseTree.
     * This is the disease ancestor of everyone in this DiseaseTree, the
     * person who got sick first and indirectly caused everyone in it to get sick.
     * root is non-null. 
     * All Person's in a DiseaseTree have different names. There are no duplicates
     * */
    private Person root;

    /** The immediate children of this DiseaseTree node.
     * Each element of children got the disease from the person at this node.
     * root is non-null but will be an empty set if this is a leaf. */
    private Set<DiseaseTree> children;

    /** Constructor: a new DiseaseTree with root p and no children.
     * Throw an IllegalArgumentException if p is null. */
    public DiseaseTree(Person p) throws IllegalArgumentException {
        if (p == null)
            throw new IllegalArgumentException("Can't construct DiseaseTree with null root");
        root= p;
        children= new HashSet<>();
    }

    /** Constructor: a new DiseaseTree that is a copy of tree p.
     * Tree p and its copy have no node in common (but nodes can share a Person).
     * Throw an IllegalArgumentException if p is null.  */
    public DiseaseTree(DiseaseTree p) throws IllegalArgumentException {
        if (p == null)
            throw new IllegalArgumentException("Can't construct DiseaseTree as copy of null");
        root= p.root;
        children= new HashSet<>();

        for (DiseaseTree dt : p.children) {
            children.add(new DiseaseTree(dt));
        }
    }

    /** Return the person that is at the root of this DiseaseTree */
    public Person getRoot() {
        return root;
    }

    /** Return the number of DiseaseTrees that are direct children of this DiseaseTree */
    public int getChildrenCount() {
        return children.size();
    }

    /** Return a COPY of the set of children of this DiseaseTree. */
    public Set<DiseaseTree> getChildren(){
        return new HashSet<>(children);
    }
    
    /** Add c to this DiseaseTree as a child of p
     * and return the DiseaseTree whose root is the new child.
     * Throw an IllegalArgumentException if:<br>
     *      -- p or c is null,<br>
     *      -- c is already in this DiseaseTree, or<br>
     *      -- p is not in this DiseaseTree */
    public DiseaseTree add(Person p, Person c) throws IllegalArgumentException {
        //TODO 1
        if(p == null || c == null) throw new IllegalArgumentException("adding failure");
        if(contains(c)) throw new IllegalArgumentException("adding failure");
        if(!contains(p)) throw new IllegalArgumentException("adding failure");
        DiseaseTree ct = new DiseaseTree(c);
        this.helpAdding(ct, p);        
        return ct;
    }
    
    private DiseaseTree helpAdding(DiseaseTree ct, Person p){
    	if(this.root == p) {
    		children.add(ct);
    		return ct;
    	}
    	for(DiseaseTree child: this.children) {
    		child.helpAdding(ct, p);
    	}
    	return ct;
    }

    /** Return the number of people contained in this DiseaseTree.
     * Note: If this is a leaf, the size is 1 (just the root) */
    public int size(){
        //TODO 2
    	if(this.root == null) return 0;
        int sum = 1;
        for(DiseaseTree child: children){
        	sum += child.size();
        }
        return sum;
    }
 

    /** Return the depth at which p occurs in this DiseaseTree, or -1
     * if p is not in the DiseaseTree.
     * Note: depthOf(root) is 0.
     * If p is a child of this DiseaseTree, then depthOf(p) is 1. etc. */
    public int depthOf(Person p){
        //TODO 3
    	int depth = -1;
        if(this.root == p) return 0;
        for(DiseaseTree child: this.children){
        	if(child.depthOf(p) != -1) {
        		depth = child.depthOf(p)+1;
        		return depth;
        	}
        }
        return depth;
    }
  

    /** If p is in this tree, return the DiseaseTree object in this tree that
     * contains p. If p is not in this tree, return null.
     * 
     * Example: Calling getTree(root) should return this. */
    public DiseaseTree getTree(Person p){
        if (root == p) return this; //Base case - look here

        // Recursive case - ask children to look
        for (DiseaseTree dt : children) {
            DiseaseTree search= dt.getTree(p);
            if (search != null) return search;
        }

        return null; // Not found
    }

    /** Return true iff this DiseaseTree contains p. */
    public boolean contains(Person p){
        /* Note: This DiseaseTree contains p iff the root of this DiseaseTree is
         * p or if one of p's children contains p. */
        if (root == p) return true;
        for (DiseaseTree dt : children) {
            if (dt.contains(p)) return true;
        }
        return false;
    }


    /** Return the maximum depth of this DiseaseTree, i.e. the longest path from
     * the root to a leaf. Example. If this DiseaseTree is a leaf, return 0. */
    public int maxDepth() {
        int maxDepth= 0;
        for (DiseaseTree dt : children) {
            maxDepth= Math.max(maxDepth, dt.maxDepth() + 1);
        }
        return maxDepth;
    }

    /** Return the width of this tree at depth d (i.e. the number of diseaseTrees that
     * occur at depth d, where the depth of the root is 0. 
     * Throw an IllegalArgumentException if depth < 0.
     * Thus, for the following tree :
     * Depth level:
     *       0       A
     *              / \
     *       1     B   C
     *            /   / \
     *       2   D   E   F
     *                \
     *       3         G
     * 
     * A.widthAtDepth(0) = 1,  A.widthAtDepth(1) = 2,
     * A.widthAtDepth(2) = 3,  A.widthAtDepth(3) = 1,
     * A.widthAtDepth(4) = 0. 
     * C.widthAtDpth(0) = 1,   C.widthAtDepth(1) = 2
     * */
    public int widthAtDepth(int d) throws IllegalArgumentException {
        //TODO 4
        if(maxDepth() < 0) throw new IllegalArgumentException();
        int sum = 0;
        if(d == 0) return 1;
        for(DiseaseTree child: children) {
        	sum += child.widthAtDepth(d-1);
        }
        return sum;
    }

    /** Return the maximum width of all the widths in this tree, i.e. the
     * maximum value that could be returned from widthAtDepth for this tree. */
    public int maxWidth() {
        return maxWidthImplementationOne(this);
    }

    // Simple implementation of maxWith. Relies on widthAtDepth.
    // Takes time proportional the the square of the size of the t.
    static int maxWidthImplementationOne(DiseaseTree t) {
        int width= 0;
        int depth= t.maxDepth();
        for (int i= 0; i <= depth; i++) {
            width= Math.max(width, t.widthAtDepth(i));
        }
        return width;
    }

    /* Better implementation of maxWidth. Caches results in an array.
     * Takes time proportional to the size of t. */
    static int maxWidthImplementationTwo(DiseaseTree t) {
        // For each integer d, 0 <= d <= maximum depth of t, store in
        // widths[d] the number of nodes at depth d in t.
        // The calculation is done by calling recursive procedure addToWidths.
        int[] widths= new int[t.maxDepth() + 1];   // initially, contains 0's
        t.addToWidths(0, widths);
        
        int max= 0;
        for (int width : widths) {
            max= Math.max(max, width);
        }
        return max;
    }

    /* For each node of this DiseaseTree, which is at some depth d in this DiseaseTree,
     * add 1 to widths[depth + d]. */
    private void addToWidths(int depth, int[] widths) {
        widths[depth]++;        //the root of this DiseaseTree is at depth d = 0
        for (DiseaseTree dt : children) {
            dt.addToWidths(depth+1, widths);
        }
    }

    /* Better implementation of maxWidth. Caches results in a HashMap.
     * Takes time proportional to the size of t. */
    static int maxWidthImplementationThree(DiseaseTree t) {
        // For each possible depth d >= 0 in tree t, widthMap will contain the 
        // entry (d, number of nodes at depth d in t). The calculation is
        // done using recursive procedure addToWidthMap.
        
        // For each integer d, 0 <= d <= maximum depth of t, add to
        // widthMap an entry <d, 0>.
        HashMap<Integer, Integer> widthMap= new HashMap<>();
        for (int d= 0; d <= t.maxDepth() + 1; d++) {
            widthMap.put(d, 0);
        }
        
        t.addToWidthMap(0, widthMap);
        
        int max= 0;
        for (Integer w : widthMap.values()) {
            max= Math.max(max, w);
        }
        return max;
    }

    /* For each node of this DiseaseTree, which is at some depth d in this DiseaseTree,
     * add 1 to the value part of entry <depth + d, ...> of widthMap. */
    private void addToWidthMap(int depth, HashMap<Integer, Integer> widthMap) {
        widthMap.put(depth, widthMap.get(depth) + 1);  //the root is at depth d = 0
        for (DiseaseTree dt : children) {
            dt.addToWidthMap(depth + 1, widthMap);
        }
    }

    /** Return the route the disease took to get from "here" (the root of this 
     * DiseaseTree) to child c.
     * For example, for this tree:
     * 
     * Depth level:
     *       0      A
     *             / \
     *       1    B   C
     *           /   / \
     *       2  D   E   F
     *           \
     *       3    G
     * 
     * A.getDiseaseRouteTo(E) should return a list of [A,C,E].
     * A.getDiseaseRouteTo(A) should return [A].
     * A.getDiseaseRouteTo(X) should return null.
     * 
     * B.getDiseaseRouteTo(C) should return null.
     * B.getDiseaseRouteTo(D) should return [B,D]
     */
    public List<Person> getDiseaseRouteTo(Person c){
        //TODO 5
        // Hint: You have to return a List<Person>. But List is an
        // interface, so use something that implements it: ArrayList<Person>.
        //Base Case - this is the child. Route is just [child]        
        ArrayList<Person> route = new ArrayList<Person>();
        boolean containC = helperRoute(c, route, this);
        return containC? route : null;
    }
    
    //helper function for recursive find c
    private boolean helperRoute(Person c, ArrayList<Person> path, DiseaseTree dt){
    	path.add(dt.root);
    	if(c == dt.root){
    		return true;
    	}
    	else{
    		for(DiseaseTree child: dt.children) {
    			if(helperRoute(c, path, child)) return true;
    		}
    	}
    	path.remove(path.size() - 1);
    	return false;
    }
    /** Return the immediate parent of c (null if c is not in this
     *  DiseaseTree).
     * 
     * Thus, for the following tree:
     * Depth level:
     *       0      A
     *             / \
     *       1    B   C
     *           /   / \
     *       2  D   E   F
     *           \
     *       3    G
     * 
     * A.getParentOf(E) returns C.
     * C.getParentOf(E) returns C.
     * A.getParentOf(B) returns A.
     * E.getParentOf(F) returns null. */
    public Person getParentOf(Person c) {
        // Base case
        for (DiseaseTree dt : children) {
            if (dt.root == c) return root;
        }

        // Recursive case - ask children to look
        for (DiseaseTree dt : children) {
            Person parent= dt.getParentOf(c);
            if (parent != null) return parent;
        }

        return null; //Not found
    }

    /**  If child1 is null or child2 is null, return null. Otherwise,
     * Return the most direct shared ancestor of child1 and child2 in this DiseaseTree.
     * If the true ancestor of the two people is not in this subtree, return null instead.
     * If either child is the true root (A, below), return null.
     * 
     * Thus, for the following tree:
     * Depth level:
     *       0      A
     *             / \
     *       1    B   C
     *           /   / \
     *       2  D   E   F
     *           \
     *       3    G
     * 
     * A.getSharedAncestorOf(B,C) is A
     * A.getSharedAncestorOf(A,C) is null
     * A.getSharedAncestorOf(E,F) is C
     * A.getSharedAncestorOf(G,F) is C
     * A.getSharedAncestorOf(null,C) is null
     * B.getSharedAncestorOf(D,F) is null
     * C.getSharedAncestorOf(C,C) is null  */
    public Person getSharedAncestorOf(Person child1, Person child2) {
        //TODO 6
    	List<Person> path1 = this.getDiseaseRouteTo(child1);
    	List<Person> path2 = this.getDiseaseRouteTo(child2);
    	Person sharedL = null;
    	if(path1 == null || path2 == null){
    		return null;
    	}
    	for(int i = 0; i < path1.size() && i <path2.size(); i++){
    		if(path1.get(i) == path2.get(i)) sharedL = path1.get(i);
    		if(path1.get(i) != path2.get(i)) break;
    	}
    	
        return sharedL;
    }

    /** Return a (single line) String representation of this DiseaseTree.
     * If this DiseaseTree has no children (it is a leaf), return the root's substring.
     * Otherwise, return
     *    root's substring + SEPARATOR + START_CHILDREN_DELIMITER + each child's 
     *    toString, separated by DELIMITER, followed by END_CHILD_DELIMITER.
     * Make sure there is not an extra DELIMITER following the last child.
     * 
     * Finally, make sure to use the static final fields declared at the top of
     * DiseaseTree.java.
     * 
     * Thus, for the following tree:
     * Depth level:
     *       0      A
     *             / \
     *       1    B  C
     *           /  / \
     *       2  D  E   F
     *           \
     *       3    G
     * A.toString() should print:
     * (A) - HEALTHY - [(C) - HEALTHY - [(F) - HEALTHY, (E) - HEALTHY - [(G) - HEALTHY]], (B) - HEALTHY - [(D) - HEALTHY]]
     *
     * C.toString() should print:
     * (C) - HEALTHY - [(F) - HEALTHY, (E) - HEALTHY - [(G) - HEALTHY]]
     */
    public String toString() {
        if (children.isEmpty()) return root.toString();
        String s= root.toString() + SEPARATOR + START_CHILDREN_DELIMITER;
        for (DiseaseTree dt : children) {
            s= s + dt.toString() + DELIMITER;
        }
        return s.substring(0, s.length() - 2) + END_CHILDREN_DELIMITER;
    }


    /** Return a verbose (multi-line) string representing this DiseaseTree. */
    public String toStringVerbose(){
        return toStringVerbose(0);
    }

    /** Return a verbose (multi-line) string representing this DiseaseTree.
     * Each person in the tree is on its own line, with indentation representing
     * what each person is a child of.
     * indent is the the amount of indentation to put before this line.
     * Should increase on recursive calls to children to create the above pattern.
     * Thus, for the following tree:
     * Depth level:
     *       0      A
     *             / \
     *       1    B   C
     *           /   / \
     *       2  D   E   F
     *           \
     *       3    G
     * 
     * A.toStringVerbose(0) should return:
     * (A) - HEALTHY
     *   (C) - HEALTHY
     *     (F) - HEALTHY
     *    (E) - HEALTHY
     *      (G) - HEALTHY
     *  (B) - HEALTHY
     *    (D) - HEALTHY
     * 
     * Make sure to use VERBOSE_SPACE_INCREMENT for indentation.  */
    private String toStringVerbose(int indent){
        String s= "";
        for (int i= 0; i < indent; i++) {
            s= s + VERBOSE_SPACE_INCREMENT;
        }
        s= s +  root.toString();

        if (children.isEmpty()) return s;

        for (DiseaseTree dt : children) {
            s= s + "\n" + dt.toStringVerbose(indent + 1);
        }
        return s;
    }

    /** Return true iff this is equal to ob.
     * Two DiseaseTrees are equal if they are the same object (==) or:
     *  <br> - they have the same root Person object (==)
     *  <br> - their children sets are equal, which requires:
     *  <br> --- the two sets are of the same size
     *  <br> --- for every DiseaseTree dt in one set, there is a DiseaseTree dt2 
     *       in the other set for which dt.equals(dt2) is true.
     *  
     * Otherwise the two DiseaseTrees are not equal. */
    public boolean equals(Object ob){
        //TODO 7
        // Hint about checking whether each child of one tree equals SOME
        // other tree of the other tree's children.
        // First, you have to check them all until you find an equal one (or
        // return false if you don't.)
        // Second, you know that a child of one tree cannot equal more than one
        // child of another tree because the names of Person's are all unique;
        // there are no duplicates. 
        if(this == ob) return true;
        if(!(ob instanceof DiseaseTree)) return false;
        DiseaseTree that = (DiseaseTree) ob;
        if(this.root != that.root) return false;
        if(this.getChildrenCount() != that.getChildrenCount()) return false;
        for(DiseaseTree child1: this.children){
        	boolean flag = false;
        	for(DiseaseTree child2: that.children) {
        		if(child1.equals(child2)) {
        			flag = true;
        			break;
        		}
        	}
        	if(!flag) return false;
        }
        return true;
    }
    
    /** Return true iff c equals some tree in trees. */
    public static boolean equalsAChild(DiseaseTree t, Set<DiseaseTree> trees) {
        for (DiseaseTree ch2 : trees) {
            if (t.equals(ch2)) {
                return true;
            }
        }
        return false;
    }

    /** Optional: You may implement this method if you want to.
     * Doing so (correctly) allows for a very simple implementation of equals.
     * This method will not be tested in any way.
     * 
     * For rules on how to implement a hashCode() method correctly,
     * see {@linkplain Object#hashCode() Object.hashCode()} */
    public int hashCode() {
        return super.hashCode();
    }

    /** Create a population of 7 people with health 10, create a disease tree
     * with certain edges, and then print the resulting tree on the console. */
    public static void main(String[] args) {
        Network n= new Network();
        Person a= new Person("A", n, 10);
        Person b= new Person("B", n, 10);
        Person c= new Person("C", n, 10);
        Person d= new Person("D", n, 10);
        Person e= new Person("E", n, 10);
        Person f= new Person("F", n, 10);
        Person g= new Person("G", n, 10);

        DiseaseTree dt= new DiseaseTree(a);
        dt.add(a, b);
        DiseaseTree dt2= dt.add(a, c);
        dt.add(b, d);
        dt.add(c, e);
        dt.add(c, f);
        dt.add(e, g);

        System.out.println(dt.toStringVerbose());
        System.out.println(dt2.toString());
    }
}
