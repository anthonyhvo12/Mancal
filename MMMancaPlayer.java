import java.util.ArrayList;

// https://github.com/skalakm/dickinsonaifall2020/blob/master/ainotes.txt

/*
 * A class that implement a player for the Othello game that uses the MiniMax algorithm 
 * to select its move.
 */
public class MMMancaPlayer extends MancalaPlayer implements MiniMax {
	
	private final long MAX_DEPTH;
	private int generatedNodes = 0;
	private int expandedNodes = 0;
	private int staticEvaluations = 0;
	
	public MMMancaPlayer(String name) {
		super.verbose = false;
		super.name = name;
		MAX_DEPTH = Integer.MAX_VALUE;
	}
	public MMMancaPlayer(String name, int depthLimit) {
		super.name = name;
		MAX_DEPTH = depthLimit;
	}

	/*
	 * min - max: gamestate
	 * minplayer fn vs maxplayer fn
	 * know which depth
	 * sibling nodes - same depth
	 */

	@Override
	/**
	 * Return the player's move
	 * @g the GameState to make a move
	 * @deadline the system time when it must return by
	 */
	public Move getMove(GameState g, long deadline) {
		Move bestMove = null;
		expandedNodes += 1;
		System.out.println("calling get move");
//		while(deadline-System.currentTimeMillis() > 500) { //this doesnt affect recursive calls?
			if (g.isBottomTurn) {
				//call minPlayer
				double bestVal = Double.NEGATIVE_INFINITY;
				ArrayList<Move> validMoves =g.getLegalMoves();
				System.out.println(validMoves.size());
				for (Move move: validMoves) {
					System.out.println("checking move");
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
				// call maxPlayer
				double bestVal = Double.POSITIVE_INFINITY;
				ArrayList<Move> validMoves =g.getLegalMoves();
				System.out.println(validMoves.size());
				for (Move move: validMoves) {
					System.out.println("checking move");
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
//		}
		System.out.println("best move found");
		return bestMove;
	}
	
	private double minPlayer(GameState curState, long depth) {
		if (verbose) {
//			System.out.println("Reaching depth " + depth);
		}
		if (curState.isGameOver() || depth == MAX_DEPTH) {
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
	
	private double maxPlayer(GameState curState, long depth) {
		if (verbose) {
			System.out.println("Reaching depth " + depth);
		}
		if (curState.isGameOver() || depth == MAX_DEPTH) {
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
	
	@Override
	public double staticEvaluator(GameState state) { //is this correct?
		// TODO Auto-generated method stub
		// number of pieces to own
		staticEvaluations += 1;
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
		return staticEvaluations;
	}
	@Override
	public double getAveBranchingFactor() {
		// TODO Auto-generated method stub
		return (double) generatedNodes / (double) expandedNodes;
	}
	@Override
	public double getEffectiveBranchingFactor() {
		// TODO Auto-generated method stub
		return getAveBranchingFactor();
	}

}
