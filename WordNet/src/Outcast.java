public class Outcast {
	private WordNet wordNet;
	public Outcast(WordNet wordnet) {
		// constructor takes a WordNet object
		this.wordNet = wordnet;
	}

	public String outcast(String[] nouns) {
		// given an array of WordNet nouns, return an outcast
		if(nouns == null || nouns.length == 0) return null;
		int maxD = 0;
		int index = 0;
		for(int i = 0; i < nouns.length; i++) {
			int distance = 0;
			for(int j = 0; j < nouns.length; j++) {
				if(j == i) continue;
				distance += wordNet.distance(nouns[i], nouns[j]);
			}
			if(maxD < distance) {
				index = i;
				maxD = distance;
			}
		}
		return nouns[index];
	}

	public static void main(String[] args) {
	    WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
	}
}
