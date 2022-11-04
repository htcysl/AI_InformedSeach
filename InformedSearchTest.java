import java.util.List;

public class InformedSearchTest {

	public static void main(String[] args) throws Exception 
	{
		System.out.println("Informed (heuristic) search");
		System.out.println("---------------------------");		

		final int boardSize = 3;
		EightPuzzleState desiredGoalState = new EightPuzzleState(boardSize);

		System.out.println("Desired goal state:");
		System.out.println(desiredGoalState);	

		int shuffleCount = 10;
		System.out.println("Initial state (shuffled " + shuffleCount + " times):");
		EightPuzzleState initialState = (EightPuzzleState)desiredGoalState.shuffle(shuffleCount);
		//EightPuzzleState initialState = (EightPuzzleState)desiredGoalState.shuffleIllogical(shuffleCount);
		System.out.println(initialState);
		
		long startTime = System.nanoTime();
		//Heuristic heuristic = new EightPuzzleHeuristic1();
		Heuristic heuristic = new EightPuzzleHeuristic2();
		Solver solver = new AstarSearch(heuristic);
		List<State> solution = solver.solve(initialState);
		long endTime = System.nanoTime();
		
		if (solution == null || solution.size() == 0) {
			System.out.println("No solution is found!");
		}
		else {
			System.out.println("Solution step count : " + (solution.size() - 1));
			for (int i=0; i<solution.size(); i++) {
				System.out.print(solution.get(i));
				
				if (i==0)
					System.out.println("  --> Initial state\n");
				else
					System.out.println("  --> Step " + i + "\n");
			}

			System.out.println();
			System.out.println("Maximum frontier size : " + solver.getMaximumFrontierSize());
			System.out.println("Current frontier size : " + solver.getFrontierSize());
			System.out.println("Current explored size : " + solver.getExploredSize());		
			System.out.println("Visited node count : " + solver.getVisitedCount());

			System.out.println();
			System.out.println("Maximum explored depth : " + solver.getMaximumExploredDepth());
		}		

		System.out.println();
		System.out.println("Execution time : " + (endTime - startTime) / 1e6 + " milliseconds");		
	}

}

/*
In this homework you will work as groups of 3 students, actually this group will be your project group.

You will implement A*-search algorithms for any generic problem. Then you will test A* search algorithm on 8-puzzle. During development you should satisfy below requirements:
* Create an abstract State class
* State class method will have abstract method 'boolean isGoal( )'
* Create a concrete EightPuzzleState class
* Create an abstract Node class 
* Node class should contain a State, parent Node, and path cost (or other informations that you find relevant)
* Create an abstract Action class
* Create a concrete EightPuzzleAction class
* Create AstarSearchAlgorithm class with solve method
* Create an abstract Heuristic class
* Create a concrete EightPuzzleHeuristic1 class   (number of mismatches)
* Create a concrete EightPuzzleHeuristic2 class   (sum of manhattan distances)
* solve method will take initial state (as abstract State class) and a heuristic (as abstract Heuristic clas)
* Create a test class to show the performance of the AstarSearchAlgorithm with EightPuzzleHeuristic1 and EightPuzzleHeuristic2 for different scenarios. 
* Answer the question: Explain that with what modification AstarSearchAlgorithm can act as Uniform-Cost search?
*/
