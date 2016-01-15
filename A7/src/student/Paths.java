/* Time spent on a7:  hh hours and mm minutes.

 * Name:
 * Netid: 
 * What I thought about this assignment:
 *
 *
 */

package student;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import graph.Edge;
import graph.Node;

/** This class contains Dijkstra's shortest-path algorithm and some other methods. */
public class Paths {


    /** Return a list of the nodes on the shortest path from start to 
     * end, or the empty list if a path does not exist. */
    public static List<Node> dijkstra(Node start, Node end) {
        /* TODO Implement Dijkstras's shortest-path algorithm presented
         in the slides titled "Final Algorithm" (slide 32 or so) in the slides for
         lecture 20.
         In particular, a min-heap (as implemented in assignment A6) should be
         used for the frontier set. We provide a declaration of the frontier.

         Maintaining information about shortest paths will require maintaining
         for each node in the settled and frontier sets the backpointer for
         that node, as described in the A7 handout, along with the length of the
         shortest path (thus far, for nodes in the frontier set). For this
         purpose, we provide static class NodeInfo. We leave it to you to
         declare the HashMap variable for this and describe carefully what it
         means. Write a precise definition of it.

         Note 1: Do not create a data structure to contain the far-off set.
         Note 2: Read the notes on pages 2..3 of the handout carefully.
         Note 3: The method should return as soon as the shortest path to node
                 end has been calculated. In that case, do NOT continue to find
                 shortest paths.
                 
         Finally, the only graph methods you should call are these:
         
            1. f.getExits():  Return a List<Edge> of edges that leave Node f.
            3. e.getOther(n): n must be one of the Nodes of Edge e.
                              Return the other Node.
            4. e.length():    Return the length of Edge e (an int).
         */

        // The frontier set, as discussed in lecture
        Heap<Node> frontier= new Heap<Node>();
        HashMap<Node, NInfo> map = new HashMap<Node, NInfo>();
        NInfo startInfo = new NInfo(null, 0);
        frontier.add(start, 0.0);
        map.put(start, startInfo);
        boolean exist = false;
        while(frontier.size() != 0) {
        	Node cur = frontier.poll();
        	if(cur == end) {
        		exist = true;
        		break;
        	}
        	for(Edge e: cur.getExits()) {
        		Node other = e.getOther(cur);
        		int distance = map.get(cur).distance + e.length;
        		NInfo otherInfo = null;
        		if(map.containsKey(other)) {
        			otherInfo = new NInfo(cur, distance);
        			map.put(other, otherInfo);
        			frontier.add(other, distance);
        		}
        		else {
        			otherInfo = map.get(other);
        			if(otherInfo.distance > distance) {
        				otherInfo.distance = distance;
        				otherInfo.backPointer = cur;
        				map.put(other, otherInfo);
        				frontier.updatePriority(other, distance);
        			}
        		}
        	}
        }
       
        if(exist) return buildPath(end, map); 
        return new LinkedList<Node>();// no path found
    }
    	
  
    /** Return the path from the start node to end.
     * Precondition: nodeInfo contains all the necessary information about
     * the path. */
    public static List<Node> buildPath(Node end, HashMap<Node, NInfo> nodeInfo) {
        LinkedList<Node> path= new LinkedList<Node>();
        Node p= end;
        while (p != null) {
            path.addFirst(p);
            p= nodeInfo.get(p).backPointer;
     
        }
        return path;
    }

    /** Return the sum of the weight of the edges on path p. */
    public static int pathLength(LinkedList<Node> path) {
        synchronized(path){
            if (path.size() == 0) return 0;

            Iterator<Node> iter= path.iterator();
            Node p= iter.next();  // First node on path
            int s= 0;
            // invariant: s = sum of weights of edges from start up to p
            while (iter.hasNext()) {
                Node q= iter.next();
                s= s + p.getEdge(q).length;
                p= q;
            }
            return s;
        }
    }

    /** An instance contains information about a node: the previous
     * node on a shortest path from the start node to this node and the distance
     * of this node from the start node. */
    private static class NInfo {
        private Node backPointer;
        private int distance;

        /** Constructor: an instance with distance d from the start node and
         * backpointer p.*/
        private NInfo(Node p, int d) {
            backPointer= p;  // Backpointer on the path (null if start node)
            distance= d; //Distance from start node to this one.
        }

        /** Constructor: an instance with a null previous node and distance 0. */
        private NInfo() {}

        /** return a representation of this instance. */
        public String toString() {
            return "distance " + distance + ", bckptr " + backPointer;
        }
    }


}
