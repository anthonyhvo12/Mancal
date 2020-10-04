import java.util.ArrayList;

public class AEDaBestMancalaPlayer {
	
	private final long MAX_DEPTH;
	// all generated nodes - same as MM
	private double generatedNodes = 0;
	// actually visited nodes - does not include pruned nodes
	private double visitedNodes = 0;
	// internal nodes
	private double expandedNodes = 0;
	private double staticEvaluations = 0;
	
	boolean verbose;
	String name;
	
	public void setVerbosity(boolean verbose) {
        this.verbose = verbose;
    }
 
    /**
     * Returns the name of this player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string representation of this player.
     */
    public String toString() {
        return name;
    }
	
	public AEDaBestMancalaPlayer(String name) {
		verbose = false;
		this.name = name;
		MAX_DEPTH = Integer.MAX_VALUE;
	}
	public AEDaBestMancalaPlayer(String name, long depthLimit) {
		this.name = name;
		MAX_DEPTH = depthLimit;
	}
	
	/**
	 * Return the player's move
	 * @g the GameState to make a move
	 * @deadline the system time when it must return by
	 */
	public Move getMove(GameState g, long deadline) {
		// TODO Auto-generated method stub
		Move bestMove = null;
		expandedNodes += 1;
		/*
		 * Color represents the bottom / top player
		 */
		double color;
		if (g.isBottomTurn) {
			color = 1;
		}
		else {
			color = -1;
		}
		double bestVal = Double.NEGATIVE_INFINITY;
		ArrayList<Move> validMoves =g.getLegalMoves();
		if (validMoves.size() == 1) {
			return validMoves.get(0);
		}
		for (int i = 0; i < validMoves.size() - 1; i++) {
//					System.out.println("checking move");
			Move move = validMoves.get(i);
			GameState next = g.makeMove(move);
			generatedNodes += 1;
			visitedNodes += 1;
			// pass the depth
			double curVal = -negaMax(next, 0, Double.NEGATIVE_INFINITY, -bestVal, -color);
			if (curVal > bestVal) {
				bestMove = move;
				bestVal = curVal;
			}
		}
		// OPTIMIZATION 2
		Move move = validMoves.get(validMoves.size() - 1);
		GameState next = g.makeMove(move);
		generatedNodes += 1; 
		visitedNodes += 1;
		double curVal = -negaMax(next, 0, -bestVal - 1, -bestVal, -color);
		if (curVal > bestVal) {
			bestMove = move;
			bestVal = curVal;
		}
//		}
//		System.out.println("best move found");
		return bestMove;
	}
	
	/**
	 * Return the max value respective to the current player with alpha-beta pruning
	 * @curState the current state 
	 * @depth current depth in the game tree
	 * @alpha the lower limit
	 * @beta the upper limit
	 * @param color indicates the player
	 * @return
	 */
	private double negaMax(GameState curState, long depth, double alpha, double beta, double color) {
		if (verbose) {
			System.out.println("Reaching depth " + depth);
		}
		if (curState.isGameOver()) {
			return color * curState.getFinalNetScore();
		}
		if (depth == MAX_DEPTH) {
			return color * staticEvaluator(curState);
		}
		expandedNodes += 1;

		ArrayList<Move> validMoves = curState.getLegalMoves();
		generatedNodes += validMoves.size();
		for (int i = 0; i < validMoves.size() - 1; i++) {
			Move move = validMoves.get(i);
			GameState next = curState.makeMove(move);
			visitedNodes += 1;
			// pass the depth
			double curVal = -negaMax(next, depth + 1, -beta, -alpha, -color);
			if (curVal >= beta) {
				return curVal;
			}
			if (curVal > alpha) {
				alpha = curVal;
			}
		}
		// OPTIMIZATION 2
		Move move = validMoves.get(validMoves.size() - 1);
		GameState next = curState.makeMove(move);
		visitedNodes += 1;
		double curVal = -negaMax(next, depth + 1, -alpha-1, -alpha, -color);
		if (curVal > alpha) {
			alpha = curVal;
		}
		
		return alpha;
	}
	
	/*
	 *  This function returns the correct number of generated nodes - the one from the 
	 *  MiniMax interface will overflow
	 */
	public double getNodesGenerated() {
		return generatedNodes;
	}
	
	public double staticEvaluator(GameState state) {
		// TODO Auto-generated method stub
		// number of pieces to own
		staticEvaluations += 1;
		return state.currentScore();
	}
	
	public int getStaticEvaluations() {
		// TODO Auto-generated method stub
		return (int) staticEvaluations;
	}

	public double getAveBranchingFactor() {
		// TODO Auto-generated method stub
		return generatedNodes / expandedNodes;
	}

	public double getEffectiveBranchingFactor() {
		// TODO Auto-generated method stub
		return visitedNodes / expandedNodes;
	}

}
