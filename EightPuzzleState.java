import java.util.*;

public class EightPuzzleState extends State {
	static final private Random rand = new Random(System.currentTimeMillis());
	
	final int boardSize;
	private int emptySlotX; 
	private int emptySlotY;
	static final int emptySlotValue = 0;

	static final int X = 0;
	static final int Y = 1;
	static final int neighbour[][] = new int[][] {{0,-1},{0,1},{-1,0},{1,0}};
	
	private int boardState[][];
	
	private String cellFormat;

	
	EightPuzzleState(int boardSize) 
	{			
		boardSize = Math.max(boardSize, 2);
		this.boardSize = boardSize;

		createBoard();
		
		for (int i=0; i<boardSize*boardSize-1; i++) {
			int x = i % boardSize;
			int y = i / boardSize;			
			boardState[y][x] = i + 1;
		}
	}
	
	EightPuzzleState(EightPuzzleState state)
	{
		this.boardSize = state.boardSize;
		
		createBoard();
		
		this.emptySlotX = state.emptySlotX;
		this.emptySlotY = state.emptySlotY;

		for (int y=0; y<boardSize; y++) {			
			for (int x=0; x<boardSize; x++) {
				this.boardState[y][x] = state.boardState[y][x];
			}
		}		
	}
	
	private void createBoard()
	{		
		int maxDigit = (int)(Math.log10(boardSize * boardSize - 1) + 1);
		
		cellFormat = "%" + maxDigit + "d";
		
		boardState = new int[boardSize][boardSize];
		
		emptySlotX = boardSize - 1; 
		emptySlotY = boardSize - 1;		
	}
	
	public int[][] getBoardState() 
	{
		return this.boardState;
	}
	
	@Override
    public State clone() throws CloneNotSupportedException 
    { 
    	return new EightPuzzleState(this);
    }
    
    @Override
    public boolean equals(Object obj)
    {
    	if (this == obj) {
    		return true;
    	}
    	
    	if (obj instanceof EightPuzzleState) { 
    		EightPuzzleState state = (EightPuzzleState)obj;
    		for (int y=0; y<boardSize; y++) {			
    			for (int x=0; x<boardSize; x++) {
    				if (this.boardState[y][x] != state.boardState[y][x]) {
    					return false;
    				}
    			}
    		}
    		return true;
    	}
    	
    	return false;
    }
    
    @Override
    public int hashCode() 
    {
    	StringBuilder sb = new StringBuilder(); 
		for (int i=0; i<boardSize*boardSize; i++) {
			int x = i % boardSize;
			int y = i / boardSize;
			sb.append(" " + boardState[y][x]);
		}    	

		return sb.toString().hashCode();
    }	

	@Override
	public boolean isGoal()
	{	
		for (int i=0; i<boardSize*boardSize-1; i++) {
			int x = i % boardSize;
			int y = i / boardSize;
			
			if (boardState[y][x] != i + 1) {
				return false;
			}
		}
		
		return (emptySlotX == boardSize - 1 && emptySlotY == boardSize - 1);
	}
	
	public String getCellAsString(int x, int y)
	{
		if (x == emptySlotX && y == emptySlotY)
			return String.format(cellFormat, 0).replace('0', ' ');
		else
			return String.format(cellFormat, boardState[y][x]);	
	}

	public int getEmptySlotX()
	{
		return emptySlotX;
	}
	
	public int getEmptySlotY()
	{
		return emptySlotY;
	}	
	
	public void updateEmptySlotPosition()
	{
		emptySlotX = boardSize - 1;
		emptySlotY = boardSize - 1;
		
		for (int y=0; y<boardSize; y++) {
			for (int x=0; x<boardSize; x++) {		
				if (boardState[y][x] == emptySlotValue) {
					emptySlotX = x; 
					emptySlotY = y;
					return;
				}
			}
		}		
	}
	
	public boolean isInBoard(int x, int y)
	{
		return (x >= 0 && x < boardSize && y >= 0 && y < boardSize);
	}	
	
	public void swap(int x1, int y1, int x2, int y2) 
	{
		int temp = boardState[y1][x1];
		boardState[y1][x1] = boardState[y2][x2];
		boardState[y2][x2] = temp;					
	}
	
	public EightPuzzleState shuffle(int count) throws CloneNotSupportedException
	{
		EightPuzzleState shuffled = (EightPuzzleState)this.clone();
		
		for (int i=0; i<count; i++) {
			
			List<Action> actions = shuffled.getActionList();
			
			int actionIndex = rand.nextInt(actions.size());
			shuffled.doAction(actions.get(actionIndex));			
		}
		
		return shuffled;
	}	

	public EightPuzzleState shuffleIllogical(int count) throws CloneNotSupportedException
	{
		EightPuzzleState shuffled = (EightPuzzleState)this.clone();
		
		for (int i=0; i<count; i++) {
			int x1 = rand.nextInt(boardSize);
			int y1 = rand.nextInt(boardSize);
			int x2 = rand.nextInt(boardSize);
			int y2 = rand.nextInt(boardSize);
			
			shuffled.swap(x1, y1, x2, y2);			
		}
		
		shuffled.updateEmptySlotPosition();
		
		return shuffled;
	}
	
	@Override
	public List<Action> getActionList()
	{
		List<Action> actions = new ArrayList<Action>();
		
		for (int ni=0; ni<neighbour.length; ni++) {
			int slideSlotX = emptySlotX + neighbour[ni][X];
			int slideSlotY = emptySlotY + neighbour[ni][Y];
			
			if (isInBoard(slideSlotX, slideSlotY)) {
				actions.add(new EightPuzzleAction(slideSlotX, slideSlotY, emptySlotX, emptySlotY));				
			}
		}
		
		return actions;
	}
		
	@Override
	public State doAction(Action action)
	{
		EightPuzzleAction eightPuzzleAction = (EightPuzzleAction)action;
		
		swap(eightPuzzleAction.slideSlotX, eightPuzzleAction.slideSlotY, eightPuzzleAction.emptySlotX, eightPuzzleAction.emptySlotY);

		emptySlotX = eightPuzzleAction.slideSlotX;
		emptySlotY = eightPuzzleAction.slideSlotY;
				
		return this;
	}	
	
	@Override
	public State undoAction(Action action)
	{
		EightPuzzleAction eightPuzzleAction = (EightPuzzleAction)action;
		
		swap(eightPuzzleAction.emptySlotX, eightPuzzleAction.emptySlotY, eightPuzzleAction.slideSlotX, eightPuzzleAction.slideSlotY);

		emptySlotX = eightPuzzleAction.emptySlotX;
		emptySlotY = eightPuzzleAction.emptySlotY;
		
		return this;
	}	
		
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int y=0; y<boardSize; y++) {
			for (int x=0; x<boardSize; x++) {
				sb.append(getCellAsString(x, y) + " ");
			}
			sb.append('\n');
		}	
		
		return sb.toString();
	}
}
