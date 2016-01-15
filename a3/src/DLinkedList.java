/* NetId(s): sw782. Time spent: 3 hours, 0 minutes.

 * Name(s):ShirongWu
 * What I thought about this assignment:
 *
 *
 */

/** An instance is a doubly linked list. */
public class DLinkedList<E> {
    private int size;  // Number of nodes in the linked list.
    private Node head; // first node of linked list (null if none)
    private Node tail; // last node of linked list (null if none)

    /** Constructor: an empty linked list. */
    public DLinkedList() {
    	
    }

    /** Return the number of values in this list. */
    public int size() {
        return size;
    }

    /** Return the first node of the list (null if the list is empty). */
    public Node getHead() {

        return head;
    }

    /** Return the last node of the list (null if the list is empty). */
    public Node getTail() {

        return tail;
    }

    /** Return the data in node n of this list.
     * Precondition: n is a node of this list; it may not be null. */
    public E getData(Node n) {
        assert n != null;
        return n.data;
    }

    /** Return a representation of this list: its data, with adjacent
     * ones separated by ", ", "[" at the beginning, and "]" at the end. <br>
     * 
     * E.g. for the list containing 6 3 8 in that order, the result it "[6, 3, 8]". */
    public String toString() {
        //TODO: Write this method first. Do NOT use field size

    	StringBuilder res=new StringBuilder();
    	Node cur=this.head;
    	if(cur==null){
    		return "["+res+"]";
    	}
        while(cur.succ!=null){
        	res.append(cur.getData()+", ");
        	cur=cur.succ;
        }
        res.append(cur.getData());

        return "["+res+"]";

    }

    /** Return a representation of this list: its values in reverse, with adjacent
     * ones separated by ", ", "[" at the beginning, and "]" at the end. <br>
     * 
     * E.g. for the list containing 6 3 8 in that order, the result it "[8, 3, 6]".*/
    public String toStringRev() {
        //TODO: Write this method second. Do NOT use field size
    	Node cur=this.tail;
    	StringBuilder res=new StringBuilder();
    	
    	if(cur==null){
    		return "["+res+"]";
    	}
    	while(cur!=this.head){
    		res.append(cur.getData()+", ");
    		cur=cur.pred;
    	}
    	res.append(this.head.getData());
        return "["+res+"]"; 
    }

    /** Place data v in a new node at the beginning of the list and
     * return the new node */
    public Node prepend(E v) {
        //TODO: This is the third method to write. Then you can begin testing
    	
        Node addhead=new Node(null,v,this.head);
        if(size==0){
    		head=addhead;
    		tail=addhead;
    		size++;
    		return addhead;
    	}
        this.head.pred=addhead;
        this.head=addhead;
        size++;
        return addhead;
    }

    /** Place data v in a new node at the end of the list and
     * return the new node. */
    public Node append(E v) {
    	Node addtail=new Node(this.tail,v,null);
    	if(size==0){
    		head=addtail;
    		tail=addtail;
    		size++;
    		return addtail;
    	}
    	this.tail.succ=addtail;
    	this.tail=addtail;
        size++;
        return addtail;
    }

    /** Place data v in a new node after node n and
     * return the new node.
     * Precondition: n must be a node of this list; it may not be null. */
    public Node insertAfter(E v, Node n) {
    	assert n!=null;
    	if(n==this.tail){
    		return append(v);
    	}
    	Node addnew=new Node(n,v,n.succ);
    	n.succ.pred=addnew;
    	n.succ=addnew;
    	size++;
        return addnew;
    }

    /** Place data v in a new node before node n and
     * return the new node.
     * Precondition: n must be a node of this list; it may not be null. */
    public Node insertBefore(E v, Node n) {
        //TODO: This is the sixth method to write and test
    	assert n!=null;
    	if(n==this.head){
    		return prepend(v);
    	}
        Node addnew=new Node(n.pred,v,n);
        n.pred.succ=addnew;
        n.pred=addnew;
        size++;
        return addnew;
    }

    /** Remove node n from this list.
     * Precondition: n must be a node of this list; it may not be null. */
    public void remove(Node n) {
        //TODO: This is the seventh method to write and test
    	if(n==null){
    		return;
    	}
    	if(size==1){
    		this.head=null;
    		this.tail=null;
    		size--;
    		return;
    	}
    	if(n==this.head){
    		this.head=n.succ;
    		this.head.pred=null;
    		size--;
    		return;
    	}
    	if(n==this.tail){
    		this.tail=n.pred;
    		this.tail.succ=null;
    		size--;
    		return;
    	}
    	n.pred.succ=n.succ;
    	n.succ.pred=n.pred;
    	n=null;
    	size--;
    	return;

 
    } 


    /*********************/

    /** An instance is a node of this list. */
    public class Node {
        /** Predecessor of this node on list (null if this is the first node). */
        private Node pred;

        /** The data in this element. */
        private E data; 

        /** Successor of this node on list. (null if is the last node). */
        private Node succ; 

        /** Constructor: an instance with predecessor node p (can be null),
         * successor node s (can be null), and data v. */
        private Node(Node p, E v, Node s) {
            pred= p;
            succ= s;
            data= v;
        }

        /** Return the data in this node. */
        public E getData() {
            return data;
        }

        /** Return the predecessor of this node (null if this is the
         * first node of the list). */
        public Node pred() {
            return pred;
        }

        /** Return the successor of this node (null if this is the
         * last node of this list). */
        public Node succ() {
            return succ;
        }
    }

}
