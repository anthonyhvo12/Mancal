public class MancalaExperiment {
	
	MancalaPlayer p1, p2, randomPlayer;

	int size, startingStones,time;
	
	public static void main(String[] args) {

		MMMancaPlayer mm = new MMMancaPlayer("MinimaxPlayer");
		ABMancaPlayer ab = new ABMancaPlayer("ABMancaPlayer");
		MancalaExperiment m = new MancalaExperiment(mm, ab, 7, 4, 1500);
		m.playGame();
	}

	public MancalaExperiment(MancalaPlayer p1, MancalaPlayer p2, int size, int startingStones, int time) {
		this.p1 = p1;
		this.p2 = p2;
		this.size = size;
		this.startingStones = startingStones;
		this.time = time;
		randomPlayer = new RandomPlayer();
	}

	public void playGame() {
		playGame(true);
	}
	
	public void playGame(boolean p1IsBottom) {
		int moveCount = 0;
		MancalaPlayer bottom,top;
		if(p1IsBottom) {
			bottom=p1;
			top = p2;
		}else {
			bottom = p2;
			top = p1;
		}
		GameState g = new GameState(size, startingStones);
		long experimentDdl = System.currentTimeMillis() + time;
		while (!g.isGameOver() /*&& experimentDdl - System.currentTimeMillis() > 500 */) {
			moveCount++;
			GameThread t;
			if (g.isBottomTurn) {
				t = new GameThread(bottom,g,time);
				
			} else {

				t = new GameThread(top,g,time);
				
			}
		//	System.out.println("trying ");
			t.run();
			
			int segments =0;
			while(t.m==null && segments <8) {
				try {
					Thread.sleep(time/8);
					segments++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(t.m == null){
				System.out.println("Player took too long! " + t.p);
				System.out.println("Making random move...");
				t.m=randomPlayer.getMove(g,time);
			}
			g = g.makeMove(t.m);
			g.printBoard();
			System.out.println("Top is " + top);
			System.out.println("Bottom is " + bottom);
			
		}
//		long finalScore = g.getFinalNetScore();
//		System.out.println( "Game over!");
//		System.out.println("Final net score is " + finalScore );
//		
//		if(finalScore > 0) {
//			System.out.println(bottom + " wins!");
//		}else if(finalScore <0) {
//			System.out.println(top + " wins!");
//		}else {
//			System.out.println("It's a tie!");
//		}
//		return g.getFinalNetScore();
		// branching facvtor of mancala game - for a particular player??
		System.out.println(p1.name + " generated " + ((MiniMax) p1).getNodesGenerated());
		System.out.println(p2.name + " generated " + ((MiniMax) p2).getNodesGenerated());
		System.out.println(p1.name + " has an avg branching factor of " + ((MiniMax) p1).getAveBranchingFactor());
		System.out.println(p2.name + " has an avg branching factor of " + ((MiniMax) p2).getAveBranchingFactor());
		System.out.println(p1.name + " has an effective branching factor of " + ((MiniMax) p1).getEffectiveBranchingFactor());
		System.out.println(p2.name + " has an effective branching factor of " + ((MiniMax) p2).getEffectiveBranchingFactor());
	}

	private class GameThread implements Runnable {

		MancalaPlayer p;
		GameState g;
		int time;
		Move m=null;

		public GameThread(MancalaPlayer p, GameState g, int time) {
			this.p = p;
			this.g = g;
			this.time = time;
		}

		@Override
		public void run() {
			System.out.println("getting move");
			m = p.getMove(g, System.currentTimeMillis() + time);

		}

	}
}
