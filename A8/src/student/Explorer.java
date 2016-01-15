package student;

import java.util.*;

import game.EscapeState;
import game.ExplorationState;
import game.NodeStatus;
import game.Node;;

public class Explorer {

    /** Explore the cavern, trying to find the 
     * orb in as few steps as possible. Once you find the 
     * orb, you must return from the function in order to pick
     * it up. If you continue to move after finding the orb rather 
     * than returning, it will not count.
     * If you return from this function while not standing on top of the orb, 
     * it will count as a failure.
     * 
     * There is no limit to how many steps you can take, but you will receive
     * a score bonus multiplier for finding the orb in fewer steps.
     * 
     * At every step, you only know your current tile's ID and the ID of all 
     * open neighbor tiles, as well as the distance to the orb at each of these tiles
     * (ignoring walls and obstacles). 
     * 
     * In order to get information about the current state, use functions
     * getCurrentLocation(), getNeighbors(), and getDistanceToTarget() in ExplorationState.
     * You know you are standing on the orb when getDistanceToTarget() is 0.
     * 
     * Use function moveTo(long id) in ExplorationState to move to a neighboring 
     * tile by its ID. Doing this will change state to reflect your new position.
     * 
     * A suggested first implementation that will always find the orb, but likely won't
     * receive a large bonus multiplier, is a depth-first search.
     * 
     * @param state the information available at the current state
     */
    public void explore(ExplorationState state) {
    	if(state.getDistanceToTarget() == 0) return;   	
    	HashSet<Long> set = new HashSet<Long>();
    	helper(state, set);
    	
        //TODO : Explore the cavern and find the orb
    }
    private boolean helper(ExplorationState state, HashSet<Long> set) {
    	if (state.getDistanceToTarget() == 0) return true;
    	set.add(state.getCurrentLocation());
    	Heap<Long> heap = new Heap<Long>();
    	for(NodeStatus neighbor: state.getNeighbors()) {
    		heap.add(neighbor.getId(), neighbor.getDistanceToTarget());
    	}
    	while(!heap.isEmpty()) {
    		long nextID = heap.poll();
    		if(!set.contains(nextID)){
    			long curID = state.getCurrentLocation();
    			state.moveTo(nextID);
    			if(helper(state, set)) return true;
    			else state.moveTo(curID);
    		}
    	}
    	return false;
    	
    }
    /**  Escape from the cavern before the ceiling collapses, trying to collect as much
     * gold as possible along the way. Your solution must ALWAYS escape before time runs
     * out, and this should be prioritized above collecting gold.
     * 
     * You now have access to the entire underlying graph, which can be accessed through EscapeState.
     * getCurrentNode() and getExit() will return you Node objects of interest, and getVertices()
     * will return a collection of all nodes on the graph. 
     * 
     * Note that time is measured entirely in the number of steps taken, and for each step
     * the time remaining is decremented by the weight of the edge taken. You can use
     * getTimeRemaining() to get the time still remaining, pickUpGold() to pick up any gold
     * on your current tile (this will fail if no such gold exists), and moveTo() to move
     * to a destination node adjacent to your current node.
     * 
     * You must return from this function while standing at the exit. Failing to do so before time
     * runs out or returning from the wrong location will be considered a failed run.
     * 
     * You will always have enough time to escape using the shortest path from the starting
     * position to the exit, although this will not collect much gold. But for this reason, using 
     * Dijkstra's to plot the shortest path to the exit is a good starting solution.
     * 
     * @param state the information available at the current state
     */

    public void escape(EscapeState state) {
        //TODO: Escape from the cavern before time runs out
    	Node end = state.getExit();
    	Node start = state.getCurrentNode();
    	LinkedList<Node> vertices = new LinkedList<Node>(state.getVertices());
    	
    	//pick up gold at start node
    	if(start.getTile().getGold() > 0) {
    		state.pickUpGold();
    	}
    	
    	int timeRemain = state.getTimeRemaining();
    	while(timeRemain > 0) {
    		//Find average gold max paths in descending order
    		List<List<Node>> paths = findAverageMaxPath(vertices, start);
    		//Add to end path if all the path can be used
    		List<Node> directToEnd = Paths.dijkstra(start, end);
    		paths.add(directToEnd);
    		Node mid = null;
    		for(List<Node> path: paths) {
    			mid = path.get(path.size() - 1);
    			int timeTo = Paths.pathLengthAndGold(path)[0];
    			int timeBack = Paths.pathLengthAndGold(Paths.dijkstra(mid, end))[0];
    			//check if time is enough to go this path
    			if(timeTo + timeBack > timeRemain) {
    				continue;
    			}
    			timeRemain -= timeTo;
    			Node cur = null;
    			for(int j = 1; j < path.size(); j++) {
    				cur = path.get(j);
    				state.moveTo(cur);
    				if(cur.getTile().getGold() > 0) {
    					state.pickUpGold();
    				}
    			}
    			start = mid;
    			break;	
    		}
    		//if it reaches end, just break
    		if(mid.equals(end)) break; 
    	}
    }
    
    private List<List<Node>> findAverageMaxPath(List<Node> vertices, Node start) {
    	List<List<Node>> paths = new ArrayList<List<Node>>();
    	for(int i =0; i < vertices.size(); i++) {
    		   if(vertices.get(i).getTile().getGold() > 0) {
    			   List<Node> path = Paths.dijkstra(start, vertices.get(i));
    			   paths.add(path);
    		   }
    	}
    	//sort path by the average gold contained by this path
    	Collections.sort(paths, new Comparator<List<Node>>() {
    		public int compare(List<Node> path1, List<Node> path2) {
    			int[] lengthAndGold1 = Paths.pathLengthAndGold(path1);
    			int[] lengthAndGold2 = Paths.pathLengthAndGold(path2);
    			double diff = (double)(lengthAndGold2[1] / lengthAndGold2[0]) - 
    						  (double)(lengthAndGold1[1] / lengthAndGold1[0]);
    			if(diff > 0) return 1;
    			else if(diff < 0) return -1;
    			else return 0;
    		}
    	});
    	return paths;
    }
//    public void escape(EscapeState state) {
//    	Node start = state.getCurrentNode();
//    	Node end = state.getExit();
//    	List<Node> path = Paths.dijkstra(start, end);
//    	if(start.getTile().getGold() > 0) {
//    		state.pickUpGold();
//    	}
//    	for(int i = 1; i < path.size(); i++) {
//    		Node n = path.get(i);
//    		state.moveTo(n);
//    		if(n.getTile().getGold() > 0) {
//    			state.pickUpGold();
//    		}
//    	}
//    }
}
