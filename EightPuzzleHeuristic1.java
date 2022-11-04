public class EightPuzzleHeuristic1 extends Heuristic {

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
				h++;
			}
		}        
        
		return h;
    }

}
