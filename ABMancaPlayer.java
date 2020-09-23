import java.util.ArrayList;

/*
 * A class that implement a player for the Othello game that uses the alpha-beta 
 * pruning during search to select its move.
 */

public class ABMancaPlayer extends MancalaPlayer implements MiniMax {

	private final long MAX_DEPTH;
	private int generatedNodes = 0;
	private int expandedNodes = 0;
	private int allNodesNoPruning = 0;
	
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
//		while( deadline-System.currentTimeMillis() > 500) {
			if (g.isBottomTurn) {
				//call minPlayer
				double bestVal = Double.NEGATIVE_INFINITY;
				ArrayList<Move> validMoves =g.getLegalMoves();
				for (Move move: validMoves) {
//					System.out.println("checking move");
					GameState next = g.makeMove(move);
					generatedNodes += 1; 
					allNodesNoPruning += 1;
					// pass the depth
					// TOASK: depth = 0 or = 1
					double curVal = minPlayer(next, 0, Long.MIN_VALUE, Long.MAX_VALUE);
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
					allNodesNoPruning += 1;
					// pass the depth
					double curVal = maxPlayer(next, 0, Long.MIN_VALUE, Long.MAX_VALUE);
					if (curVal < bestVal) {
						bestMove = move;
						bestVal = curVal;
					}
				}
			}
//		}
		System.out.println("best move found");
		return bestMove;
	}
	
	private double minPlayer(GameState  curState, long depth, double alpha, double beta) {
		if (verbose) {
			System.out.println("Reaching depth " + depth);
		}
		if (curState.isGameOver() || depth == MAX_DEPTH) {
			return staticEvaluator(curState);
		}
		expandedNodes += 1;

		ArrayList<Move> validMoves = curState.getLegalMoves();
		allNodesNoPruning += validMoves.size();
		for (Move move: validMoves) {
			GameState next = curState.makeMove(move);
			generatedNodes += 1;
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
		if (curState.isGameOver() || depth == MAX_DEPTH) {
			return staticEvaluator(curState);
		}
		expandedNodes += 1;
		
		ArrayList<Move> validMoves = curState.getLegalMoves();
		allNodesNoPruning += validMoves.size();
		for (Move move: validMoves) {
			GameState next = curState.makeMove(move);
			generatedNodes += 1;
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
		return state.getPlayerScore(true);
	}
	@Override
	public int getNodesGenerated() {
		// TODO Auto-generated method stub
		return generatedNodes;
	}
	@Override
	public int getStaticEvaluations() {
		// TODO Auto-generated method stub
		return expandedNodes;
	}
	@Override
	public double getAveBranchingFactor() {
		// TODO Auto-generated method stub
		return (double) allNodesNoPruning / (double) expandedNodes;
	}
	@Override
	public double getEffectiveBranchingFactor() {
		// TODO Auto-generated method stub
		// NOT SURE - does no of generated nodes include nodes
		// that are pruned during the search? If so, do we need
		// to create a separate variable for total number of generated
		// AND visited nodes?
		return (double) generatedNodes / (double) expandedNodes;
	}

}
