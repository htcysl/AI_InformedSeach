import java.util.*;

public class AstarSearch extends Solver {
	private HashMap<Integer, Node> frontierMap = new HashMap<Integer, Node>(); 		// map for modifying data in frontier
	private PriorityQueue<Node> frontier = new PriorityQueue<Node>();				// open set
	private HashSet<Node> explored = new HashSet<Node>(); 							// closed list
	private Heuristic heuristic;

	private int maximumFrontierSize;
	private int maximumExploredDepth;
	
	AstarSearch(Heuristic heuristic)
	{
		super();
		
		this.heuristic = heuristic;
	}
	
	@Override
	List<State> solve(State initialState) throws Exception
	{
		maximumFrontierSize = 0;
		maximumExploredDepth = 0;
		
		// initialize the frontier using the initial state of problem
		Node initialNode = new Node(initialState);
		frontier.clear();
		frontier.add(initialNode);
		frontierMap.clear();
		frontierMap.put(initialNode.hashCode(), initialNode);
		visitedCount = 1;
		
		// initialize the explored set to be empty
		explored.clear();

		// exploring with LIFO strategy
		while (frontier.size() > 0) {
			// choose a leaf node and remove it from the frontier
			Node node = frontier.poll();
			frontierMap.remove(node.hashCode());

			maximumExploredDepth = Math.max(maximumExploredDepth, node.depth);
			
			// check if the node is a goal state
			if (node.state.isGoal()) {
				return createSolutionSequence(node);
			}		

			// add the node to the explored set
			explored.add(node);
			
			if (node.depth < depthLimit) {
				// expand the chosen node, adding the resulting nodes to the frontier
				for(Action action : node.state.getActionList()) {
					visitedCount++;
					
					State newState = node.state.clone().doAction(action);
                    double g_n = node.pathCost;
                    double h_n = heuristic.calculate(newState);
                    double f_n = g_n + h_n;
					
					Node newNode = new Node(newState, action, node, f_n);
					if (newNode.state.isGoal()) {
						return createSolutionSequence(newNode);
					}

					// add only if not in the frontier or explored set
					// if child.STATE is not in explored or frontier then
					// 		frontier <-- INSERT(child, frontier)
					if (!frontier.contains(newNode) && !explored.contains(newNode)) {
						frontier.add(newNode);
						frontierMap.put(newNode.hashCode(), newNode);
					}
					else {
						// if child.STATE is in frontier with higher PATH-COST then
						//		replace that frontier node with child
						// Note that, HashMap<Integer, Node> frontierMap to access data in the PriorityQueue<Node> frontier
						Node frontierNode = frontierMap.get(newNode.hashCode());
						if (frontierNode != null && frontierNode.pathCost > newNode.pathCost) {
	                        frontier.remove(frontierNode);
	                        frontier.add(newNode);							
						}
					}
				}
				maximumFrontierSize = Math.max(maximumFrontierSize, frontier.size());
			}
		}

		// no solution is found!
		return null;	
	}
	
	@Override
	int getMaximumFrontierSize() 
	{
		return maximumFrontierSize;
	}
	
	@Override
	int getFrontierSize() 
	{
		return frontier.size();
	}

	@Override
	int getExploredSize()
	{
		return explored.size();		
	}
	
	@Override
	int getMaximumExploredDepth()
	{
		return maximumExploredDepth + 1;
	}
	
}
