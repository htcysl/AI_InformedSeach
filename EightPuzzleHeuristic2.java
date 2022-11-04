public class EightPuzzleHeuristic2 extends Heuristic {

    @Override
    public int calculate(State state) 
    {
        EightPuzzleState eightPuzzleState = (EightPuzzleState)state;
        
    	int boardSize = eightPuzzleState.boardSize;
        int boardState[][] = eightPuzzleState.getBoardState();
        
        int h = 0;
		for (int i=0; i<boardSize*boardSize-1; i++) {			
			int x = i % boardSize;
			int y = i / boardSize;			
			int cellValue = boardState[y][x];
			
			if (cellValue != i + 1) {
                int cell_x = (cellValue - 1) % boardSize;
                int cell_y = (cellValue - 1) / boardSize;
                
                h += Math.abs(x - cell_x) + Math.abs(y - cell_y);
			}
		}        
        
		return h;    	
    }

}
