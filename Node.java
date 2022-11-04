
public class Node implements Comparable<Node> {

	public State state;
	public Action action;
	public Node parent;
	public double pathCost;
	public int depth;
	
	Node(State state)
	{
		this.state = state;
		this.action = null;
		this.parent = null;
		this.pathCost = 1.0;		
		this.depth = 0;
	}	
	
	Node(State state, Action action, Node parent)
	{
		this.state = state;
		this.action = action;
		this.parent = parent;
		this.pathCost = 1.0;
		
		this.depth = (parent == null ? 0 : parent.depth + 1);
	}	

	Node(State state, Action action, Node parent, double pathCost)
	{
		this.state = state;
		this.action = action;
		this.parent = parent;
		this.pathCost = pathCost;
		
		this.depth = (parent == null ? 0 : parent.depth + 1);
	}	
	
    @Override
    public int hashCode() 
    {
    	return state.hashCode();
    }
    
    @Override
    public boolean equals(Object obj)
    {
    	if (this == obj) {
    		return true;
    	}
    	
    	if (obj instanceof State) { 
    		State state = (State)obj;
    		return state.equals(this.state);
    	}
    	
    	return false;
    }   
    
	@Override
	public int compareTo(Node node) 
	{		
		return (int)Math.signum(this.pathCost - node.pathCost);
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder(); 
		sb.append(state);
		sb.append(parent == null ? "Has no parent \n" : "Has parent \n");
		sb.append("Path cost = " + pathCost + "\n");
		sb.append("Depth = " + depth + "\n");
		
		return sb.toString();
	}

}
