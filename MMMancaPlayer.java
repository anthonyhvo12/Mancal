import java.util.ArrayList;

// https://github.com/skalakm/dickinsonaifall2020/blob/master/ainotes.txt

/*
 * A class that implement a player for the Othello game that uses the MiniMax algorithm 
 * to select its move.
 */
public class MMMancaPlayer extends MancalaPlayer implements MiniMax {
	
	private final long MAX_DEPTH;
	private double generatedNodes = 0;
	// internal nodes
	private double expandedNodes = 0;
	private double staticEvaluations = 0;
	
	public MMMancaPlayer(String name) {
		super.verbose = false;
		super.name = name;
		MAX_DEPTH = Integer.MAX_VALUE;
	}
	public MMMancaPlayer(String name, int depthLimit) {
		super.name = name;
		MAX_DEPTH = depthLimit;
	}

	@Override
	/**
	 * Return the player's move
	 * @g the GameState to make a move
	 * @deadline the system time when it must return by
	 */
	public Move getMove(GameState g, long deadline) {
		Move bestMove = null;
		expandedNodes += 1;
//		System.out.println("calling get move");
		if (g.isBottomTurn) {
			double bestVal = Double.NEGATIVE_INFINITY;
			ArrayList<Move> validMoves =g.getLegalMoves();
//			System.out.println(validMoves.size());
			for (Move move: validMoves) {
//				System.out.println("checking move");
				GameState next = g.makeMove(move);
				generatedNodes += 1;
				// pass the depth
				double curVal = minPlayer(next, 0);
				if (curVal > bestVal) {
					bestMove = move;
					bestVal = curVal;
				}
			}
		}
		else {
			double bestVal = Double.POSITIVE_INFINITY;
			ArrayList<Move> validMoves =g.getLegalMoves();
//			System.out.println(validMoves.size());
			for (Move move: validMoves) {
//					System.out.println("checking move");
				GameState next = g.makeMove(move);
				generatedNodes += 1;
				// pass the depth
				double curVal = maxPlayer(next, 0);
				if (curVal < bestVal) {
					bestMove = move;
					bestVal = curVal;
				}
			}
		}
		return bestMove;
	}
	
	/**
	 * Return the min value of nodes starting from a certain depth.
	 * @curState the current state 
	 * @depth current depth in the game tree
	 */
	private double minPlayer(GameState curState, long depth) {
		if (verbose) {
//			System.out.println("Reaching depth " + depth);
		}
		if (curState.isGameOver()) {
			return curState.getFinalNetScore();
		}
		if (depth == MAX_DEPTH) {
//			System.out.println("Gameover at depth " + depth);
			return staticEvaluator(curState);
		}
		expandedNodes += 1;
		double bestVal = Double.POSITIVE_INFINITY;
		
		ArrayList<Move> validMoves =curState.getLegalMoves();
		for (Move move: validMoves) {
			GameState next = curState.makeMove(move);
			generatedNodes += 1;
			// pass the depth
			double curVal = maxPlayer(next, depth + 1);
			if (curVal < bestVal) {
				bestVal = curVal;
			}
		}
		
		return bestVal;
	}
	
	/**
	 * Return the max value of nodes starting from a certain depth.
	 * @curState the current state 
	 * @depth current depth in the game tree
	 */
	private double maxPlayer(GameState curState, long depth) {
		if (verbose) {
			System.out.println("Reaching depth " + depth);
		}
		if (curState.isGameOver()) {
			return curState.getFinalNetScore();
		}
		if (depth == MAX_DEPTH) {
//			System.out.println("Gameover at depth " + depth);
			return staticEvaluator(curState);
		}
		expandedNodes += 1;
		double bestVal = Double.NEGATIVE_INFINITY;
		
		ArrayList<Move> validMoves =curState.getLegalMoves();
		for (Move move: validMoves) {
			GameState next = curState.makeMove(move);
			generatedNodes += 1;
			// pass the depth
			double curVal = minPlayer(next, depth + 1);
			if (curVal > bestVal) {
				bestVal = curVal;
			}
		}
		
		return bestVal;
	}
	
	/*
	 *  This function returns the correct number of generated nodes - the one from the 
	 *  MiniMax interface will overflow
	 */
	public double getCorrectNodesGenerated() {
		return generatedNodes;
	}
	
	@Override
	public double staticEvaluator(GameState state) { //is this correct?
		// TODO Auto-generated method stub
		// number of pieces to own
		staticEvaluations += 1;
		return state.currentScore();
	}
	@Override
	public int getNodesGenerated() {
		// TODO Auto-generated method stub
		return (int) generatedNodes;
	}
	@Override
	public int getStaticEvaluations() {
		// TODO Auto-generated method stub
		return (int) staticEvaluations;
	}
	@Override
	public double getAveBranchingFactor() {
		// TODO Auto-generated method stub
		return generatedNodes / expandedNodes;
	}
	@Override
	public double getEffectiveBranchingFactor() {
		// TODO Auto-generated method stub
		return getAveBranchingFactor();
	}

}
