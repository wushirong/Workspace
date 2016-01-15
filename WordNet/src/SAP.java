import java.util.*;

public class SAP {
	
	private Digraph G;
	private int min;
	private int anc;
	
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) throws NullPointerException{
		if(G == null) throw new NullPointerException();
		this.G = G;
	}
	
	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) throws IndexOutOfBoundsException{
		if(v < 0 || w < 0 || v > G.V()-1 || w > G.V()-1) throw new IndexOutOfBoundsException();
		Set<Integer> vs = new HashSet<Integer>();
		vs.add(v);
		Set<Integer> ws = new HashSet<Integer>();
		ws.add(w);
		length(vs, ws);
		if(anc < 0) return -1;
		else return min;
	}
	
	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w) throws IndexOutOfBoundsException{
		if(v < 0 || w < 0 || v > G.V()-1 || w > G.V()-1) throw new IndexOutOfBoundsException();
		length(v,w);
		return anc < 0? -1:anc;
	}
	
	//length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) throws NullPointerException{
		if(v == null || w == null) throw new NullPointerException();
		min = Integer.MAX_VALUE;
		anc = -1;
		BreadthFirstDirectedPaths bfpV = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths bfpW = new BreadthFirstDirectedPaths(G, w);
		for(int i = 0; i < G.V(); i++) {
			if(bfpV.hasPathTo(i) && bfpW.hasPathTo(i)) {
				int len = bfpV.distTo(i) + bfpW.distTo(i);
				if(len < min) {
					min = len;
					anc = i;
				}
			}
		}
		if(anc == -1) return -1;
		return min;
		
	}
	
	//a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) throws NullPointerException{
		if(v == null || w == null) throw new NullPointerException();
		length(v, w);
		if(anc < 0) return -1;
		return anc;
	}

	// do unit testing of this class
	public static void main(String[] args) {
	    In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	        int v = 2;
	        int w = 6;
	        int length   = sap.length(v, w);
	        int ancestor = sap.ancestor(v, w);
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    
	}
}