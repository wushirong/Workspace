import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A treemap data structure. */
public class TreeMap {
    private static final double SPLIT_RATIO= 0.4;

    private Node root;        // Root of the hierarchy

    private int frameWidth;   // Size in pixels of display frame for which
    private int frameHeight;  // treemap was computed. May be invalidated (-1).

    private Node selection;   // Currently selected node, if any

    /** Create a treemap with no children, just a root node. */
    public TreeMap() {
        root= this.new Node();
    }

    /** Return the root node of the hierarchy. */
    public Node getRoot() {
        return root;
    }

    /** Return the node of the treemap that contains p (null if none) */
    public Node getNodeContaining(PointD v) {
        return getNodeContaining(getRoot(), v);
    }

    /** Return the leaf of tree n that contains v.
     * Precondition: n contains v */
    public Node getNodeContaining(Node n, PointD v) {
        for (Node nn : n.children) {
            if (nn.contains(v)) {
                return getNodeContaining(nn, v);
            }
        }

        return n;
    }

    /** Clear the treemap and mark it for lazy recomputation. */
    public void clear() {
        selectNone();
        root= new Node();
        invalidate();
    }

    /** Return true iff the treemap has width w and height h. */
    public boolean isValid(int w, int h) {
        return frameWidth == w  &&  frameHeight == h;
    }

    /** Invalidate the current treemap layout. */
    private void invalidate() {
        frameWidth= -1;
        frameHeight= -1;
    }

    /** Recompute the treemap for width w and height h, if necessary. */
    public void recompute(int w, int h) {
        if (!isValid(w, h)) {
            root.recompute(w, h, 0);

            frameWidth= w;
            frameHeight= h;
        }
    }

    /** Clear any current selection. */
    public void selectNone() {
        selection= null;
    }

    /** Display the treemap on a w*h pixel frame, using g.  */
    public void paint(Graphics g, int w, int h) {
        if (root == null)
            return;

        if (!isValid(w, h))
            recompute(w, h);

        root.paint(g, w, h, 0, false);

        // Redraw the outermost boundary with a little inset; otherwise
        // part of the thickness is cut off by the frame.
        PointD pixelSize= new PointD(w > 0 ? 1.0/w : 0, h > 0 ? 1.0/h : 0);
        Block outer= new Block(
                root.block.low.add(pixelSize),
                root.block.high.minus(pixelSize));
        outer.paint(g, w, h, false, 0, false);
    }

    /** Print the structure of the tree. */
    public void printTree() {
        if (root != null)
            root.printTree("");
    }

    /** Create and return a new Node with parent p, children c,
     *  weight w, and block b.*/
    public Node getNewNode(Node p, ArrayList<Node> c, double w, Block b) {
        Node n= new Node();
        n.parent= p;
        n.children= c;
        n.weight= w;
        n.block= b;
        return n;
    }

    /** A node of the hierarchy. Contains pointers to the parent and children,
     * as well as the weight and block representing this node. */
    public class Node implements Comparable<Node> {
        private Node parent;
        private ArrayList<Node> children;
        private double weight;
        private Block block;

        /** Constructor: a Node with no parent, no children, no block, and weight 0. */
        public Node() {
            parent= null;
            children= new ArrayList<Node>();
            weight= 0;
            block= null;
        }

        /** Constructor: a Node with parent p, no children, no block, and weight 0.  */
        public Node(Node p) {
            parent= p;
            children= new ArrayList<Node>();
            weight= 0;
            block= null;
        }

        /** Return a description of this Node. */
        @Override
        public String toString() {
            return "Node weight: " + weight + ", block: " + block;
        }

        /** Return the set of children of this tree node */
        public ArrayList<Node> getChildren() {
            return children;
        }

        /** Add an initially empty child with weight zero. */
        public Node addChild() {
            Node child= new Node(this);
            children.add(child);
            return child;
        }

        /** Return "this node's block contains v". */
        public boolean contains(PointD v) {
            return block.contains(v);
        }

        /** Return the total weight of the subtree rooted at this node. */
        public double getWeight() {
            return weight;
        }

        /** Set the weight of this leaf node to w.
         * Throw a RuntimeException if this node has a child. */
        public void setLeafWeight(double w) {
            if (!children.isEmpty()) {
                throw new RuntimeException(
                        "Cannot explicitly set the weight of a non-leaf node");
            }

            weight= w;
            invalidate();

            if (parent != null)
                parent.updateWeightsFromChildren();
        }

        /** Compute the weight of this node from the weights of its children
         * and update its parent's weights accordingly. */
        private void updateWeightsFromChildren() {
            weight= 0;
            for (Node c : children)
                weight= weight + c.getWeight();

            invalidate();

            if (parent != null)
                parent.updateWeightsFromChildren();
        }

        /** Return -1, 0, or 1 depending on whether subtree rhs has greater,
         * equal or less weight than this subtree, respectively. */
        @Override
        public int compareTo(Node rhs) {
            return weight > rhs.weight ? -1 : (weight < rhs.weight ? 1 : 0);
        }

        /** Set the selection state of the subtree to state. */
        public void setSelected(boolean state) {
            selection= this;
        }

        /** Get the selection state of the subtree. */
        public boolean isSelected() {
            return selection == this;
        }

        /** Display this subtree at depth d and with ancestral selection state
         * sel, on a w*h pixel frame, using g. */
        private void paint(Graphics g, int w, int h, int d, boolean sel) {
            // Is this node or an ancestor selected?
            sel= sel  ||  selection == this;

            for (Node c : children)
                c.paint(g, w, h, d + 1, sel);

            if (block != null)
                block.paint(g, w, h, children.isEmpty(), d, sel);
        }

        /** Recompute the treemap at depth d for width w and height h. */
        private void recompute(int w, int h, int d) {
            if (d == 0) {
                block= new Block(new PointD(0, 0), new PointD(1, 1));

                System.out.println("Recomputing treemap, width= " + w
                        + ", height= " + h);
            }

            assert block != null;  // either created above or set by parent

            ArrayList<Node> sorted= new ArrayList<Node>(children);
            Collections.sort(sorted);

            sliceAndDice(sorted, 0, sorted.size()-1, block, w, h);

            for (Node c : children)
                c.recompute(w, h, d + 1);
        }




        /** Print the structure of the tree with a prefix for each line. */
        private void printTree(String prefix) {
            String childPrefix= prefix.isEmpty() ? "+- " : "   " + prefix;
            //System.out.println("In printTree. # children is: " + children.size());
            for (Node c : children) {
                System.out.println(prefix + "w= " + c.getWeight() + ", "
                        + c.block);
                c.printTree(childPrefix);
            }
        }
    }

    /** Select the smallest h, m <= h < n, such that the weights of b[m..h] sum to at
     * least SPLIT_RATIO times the weight of b[m..n] , with the following caveat:
     * If the weight of b[m..n] is <= 0, use h= (m+n)/2 and split-ratio 0.5.
     *
     * Return a pair of values: h described above and the actual
     * split ratio: the double value
     *            (weight of b[m..k]) / (weight of b[m..n]).
     *
     * Precondition: m < n and b[m..n] is in descending order by weight. */
    public static Wrapper2 getSplit(List<Node> b, int m, int n) {
        assert m < n;
        // TODO 2. This is the second method to implement.
        double ratio = 0;
        double weight = 0;
        double mTok = 0;
        int h = m;
        for(int i = m; i <= n; i++) {
        	weight += b.get(i).weight;
        }
        if(weight < 0) return new Wrapper2(h, 0.5);
    	while(h < n) {
    		mTok += b.get(h).getWeight();
    		if(mTok >= (weight * SPLIT_RATIO)) {
    			ratio = mTok / weight;
    			break;
    		}
    		h++;
    	}
        return new Wrapper2(h, ratio);
    }

    /** Recursively lay out nodes in sorted array b[m..n] as a flat
     * (single-level) treemap within bounding box bbox, assuming the overall
     * output window has width w and height h.
     *
     * Each node is assigned a block within bbox -- the size of the block is
     * proportional to the weight of the node. The blocks do not overlap,
     * and together they perfectly cover bbox.
     * 
     * In reading below, note that SPLIT_RATIO is a static var of this class.
     *
     * The layout follows the "slice-and-dice" algorithm (described
     * step-by-step below and in the handout). In brief, the nodes are split
     * into two groups so that the total weights of the two groups are
     * roughly in the ratio SPLIT_RATIO : 1 - SPLIT_RATIO; bbox is split
     * into two parts according to this ratio and each part assigned the
     * corresponding group; and the process is repeated within each part.
     * 
     * If you are looking for class Node, it is an inner class.
     *
     * Precondition: b[m..n] is sorted in descending order by weight.  */
    private void sliceAndDice(List<Node> b, int m, int n,
            BoundingBox bbox, int w, int h) {
        // Slice-and-dice algorithm (exposition adapted from
        // https://visualign.wordpress.com/2011/11/09/implementation-of-treemap/):
         
        // TODO 3: This is the third method to write.
        // The default implementation just assigns the same block to each
        // node. Overwrite this by implement steps (q)..(e).
        //
        // (a) Assume b is in descending order. [precondition]
         
        // (b) Base case. For b[m..n] of size 0, do nothing.
        //        For b[m..n] of size 1, store a new block in the block
        //        field of b[m] with color Color(0, 0, 127).
        
        // (c) Store in k the smallest value that satisfies one of the following
        //     two conditions:
        //  (1) m <= k < n  (note: b[m..k] and b[k+1..n]
        //                  each contains at least 1 value)
        //  (2) (sum of the weights of b[m..k]) >= 
        //                 SPLIT_RATIO * (sum of the weights of b[m..n])
        // and store in splitRatio the value
        //     (sum of weights of b[m..k]) / (sum of weights of b[m..n]).
        
        // (d) Create two BoundingBoxes for the left and right parts --split
        //     bbox along its longer side (to avoid very narrow shapes).
        
        // (e) Recursively allocate nodes b[m..k] in the split-off part,
        //     and nodes b[k+1..n] in the rest of the rectangle.
    	if(m > n) return;
        if(m == n) {
        	b.get(m).block= new Block(bbox, new Color(0, 0, 127));
        	return;
        }
        
        boolean horizontal = false;
        if(w * bbox.getWidth() > h * bbox.getHeight()) horizontal = true;
        
        Wrapper2 wrapB = getSplit(b, m, n);
        int k = wrapB.k;
        double splitRatio = wrapB.d;
       
        PointD p1 = null;
        PointD p2 = null;
        if(horizontal) {
        	p1 = new PointD(splitRatio * bbox.getWidth() + bbox.low.x, bbox.high.y);
        	p2 = new PointD(splitRatio * bbox.getWidth() + bbox.low.x, bbox.low.y);
        	BoundingBox box1 = new BoundingBox(bbox.low, p1);
        	BoundingBox box2 = new BoundingBox(p2, bbox.high);
        	sliceAndDice(b, m, k, box1, (int)(w * splitRatio), h);
        	sliceAndDice(b, k + 1, n, box2, (int)(w * splitRatio), h);
        }
        else {
        	p1 = new PointD(bbox.high.x, splitRatio * bbox.getHeight() + bbox.low.y);
        	p2 = new PointD(bbox.low.x, splitRatio * bbox.getHeight() + bbox.low.y);
        	BoundingBox box1 = new BoundingBox(bbox.low, p1);
        	BoundingBox box2 = new BoundingBox(p2, bbox.high);
        	sliceAndDice(b, m, k, box1, w, (int)(h * splitRatio));
        	sliceAndDice(b, k + 1, n, box2, w, (int)(h * splitRatio));
        }
        
    }

    /** An instance wraps an int and a double. */
    public static class Wrapper2 {
        public int k;
        public double d;

        /** Constructor: an instance with k and d. */
        public Wrapper2(int k, double d) {
            this.k= k;
            this.d= d;
        }
    }

    /** Main function, used to run tests for the treemap. */
    public static void main(String[] args) {
        //TODO  if you want
        System.out.println("TreeMap.main: starting.");
        String directory= FileTreePanel.getDirectory();
        FileTreeMap ftmap= new FileTreeMap(new File(directory));
        ((TreeMap)ftmap).root.printTree("A4GriesSolution");
    }
}
