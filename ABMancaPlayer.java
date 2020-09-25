import java.util.ArrayList;

/*
 * A class that implement a player for the Othello game that uses the alpha-beta 
 * pruning during search to select its move.
 */

public class ABMancaPlayer extends MancalaPlayer implements MiniMax {

	private final long MAX_DEPTH;
	// all generated nodes - same as MM
	private double generatedNodes = 0;
	// actually visited nodes - does not include pruned nodes
	private double visitedNodes = 0;
	// internal nodes
	private double expandedNodes = 0;
	private double staticEvaluations = 0;
	
	public ABMancaPlayer(String name) {
		super.verbose = false;
		super.name = name;
		MAX_DEPTH = Integer.MAX_VALUE;
	}
	public ABMancaPlayer(String name, long depthLimit) {
		super.name = name;
		MAX_DEPTH = depthLimit;
	}
	
	@Override
	public Move getMove(GameState g, long deadline) {
		// TODO Auto-generated method stub
		Move bestMove = null;
		expandedNodes += 1;
		System.out.println("calling get move");
		if (g.isBottomTurn) {
			//call minPlayer
			double bestVal = Double.NEGATIVE_INFINITY;
			ArrayList<Move> validMoves =g.getLegalMoves();
			for (Move move: validMoves) {
//					System.out.println("checking move");
				GameState next = g.makeMove(move);
				generatedNodes += 1; 
				visitedNodes += 1;
				// pass the depth
				// TOASK: depth = 0 or = 1
				double curVal = minPlayer(next, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
				if (curVal > bestVal) {
					bestMove = move;
					bestVal = curVal;
				}
			}
		}
		else {
			// call maxPlayer
			double bestVal = Double.POSITIVE_INFINITY;
			ArrayList<Move> validMoves =g.getLegalMoves();
			for (Move move: validMoves) {
//					System.out.println("checking move");
				GameState next = g.makeMove(move);
				generatedNodes += 1;
				visitedNodes += 1;
				// pass the depth
				double curVal = maxPlayer(next, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
				if (curVal < bestVal) {
					bestMove = move;
					bestVal = curVal;
				}
			}
		}
		System.out.println("best move found");
		return bestMove;
	}
	
	private double minPlayer(GameState  curState, long depth, double alpha, double beta) {
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

		ArrayList<Move> validMoves = curState.getLegalMoves();
		generatedNodes += validMoves.size();
		for (Move move: validMoves) {
			GameState next = curState.makeMove(move);
			visitedNodes += 1;
			// pass the depth
			double curVal = maxPlayer(next, depth + 1, alpha, beta);
			if (curVal <= alpha) {
				return curVal;
			}
			if (curVal < beta) {
				beta = curVal;
			}
		}
		
		return beta;
	}

	private double maxPlayer(GameState curState, long depth, double alpha, double beta) {
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
		
		ArrayList<Move> validMoves = curState.getLegalMoves();
		generatedNodes += validMoves.size();
		for (Move move: validMoves) {
			GameState next = curState.makeMove(move);
			visitedNodes += 1;
			// pass the depth
			double curVal = minPlayer(next, depth + 1, alpha, beta);
			if (curVal >= beta) {
				return curVal;
			}
			if (curVal > alpha) {
				alpha = curVal;
			}
		}
		
		return alpha;
	}
	
	@Override
	public double staticEvaluator(GameState state) {
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
		return visitedNodes / expandedNodes;
	}

}
