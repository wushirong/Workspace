 import java.lang.instrument.IllegalClassFormatException;
import java.util.*;

public class WordNet {
	private ArrayList<Queue<String>> nouns=null;
	Digraph G;
	private HashMap<String,Bag<Integer>> hashmap;
	private SAP sap;

	
	
	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) throws NullPointerException, IllegalArgumentException{
		if(synsets == null || hypernyms == null) return;
		In in = new In(synsets);
		while(in.hasNextLine()) {
			String readin = in.readLine();
			String[] splitin = readin.split(",");
			String[] noun = splitin[1].split(" ");
			Queue<String> q = new Queue<String>();
			for(String synnoun: noun) {
				q.enqueue(synnoun);
			}
			nouns.add(q);
		}
		In inH = new In(hypernyms);
		while(inH.hasNextLine()) {
			String readH = inH.readLine();
			String[] splitH = readH.split(",");
			for(int i = 1; i < splitH.length; i++) {
				G.addEdge(Integer.parseInt(splitH[0]), Integer.parseInt(splitH[i]));
			}
		}
		
		DirectedCycle dc = new DirectedCycle(G);
		int root = 0;
		for(int i = 0; i < G.V(); i++) {
			int adj = 0;
			for(int adjVertice: G.adj(i)) adj++;
			if(adj == 0) root++;
		}
		if(root != 1) throw new IllegalArgumentException();
		findNounsAndTheirVertex();
		sap = new SAP(G);
	}

	private void findNounsAndTheirVertex() {
		hashmap=new HashMap<String,Bag<Integer>>();
		int i = 0;
		for(Queue<String> q: nouns) {
			for(String str: q) {
				Bag<Integer> bag = hashmap.get(str);
				if(bag == null) {
					bag = new Bag<Integer>();
					bag.add(i);
				}
				else bag.add(i);
			}
			i++;
		}
		
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return hashmap.keySet();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) throws NullPointerException{
		if(word == null || word.length() == 0) throw new NullPointerException();
		return hashmap.containsKey(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) throws IllegalArgumentException{
		if(nounA == null || nounB == null) throw new IllegalArgumentException();
		Bag<Integer> nounAbag = hashmap.get(nounA);
		Bag<Integer> nounBbag = hashmap.get(nounB);
		return sap.length(nounAbag, nounAbag);
	}

	// a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) throws IllegalArgumentException{
		if(nounA == null || nounB == null) throw new IllegalArgumentException();
		Bag<Integer> nounAbag = hashmap.get(nounA);
		Bag<Integer> nounBbag = hashmap.get(nounB);
		int ancester = sap.ancestor(nounAbag, nounBbag);
		return join(nouns.get(ancester), " ");
	}

	private String join(Queue<String> queue, String delimiter) {
		StringBuilder res = new StringBuilder();
		for(String noun: queue) {
			res.append(noun);
			res.append(delimiter);
		}
		return res.toString();
	}

	// do unit testing of this class
	public static void main(String[] args) {

	}
}
