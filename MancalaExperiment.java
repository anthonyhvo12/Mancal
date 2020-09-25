public class MancalaExperiment {
	
	MancalaPlayer p1, p2, randomPlayer;

	int size, startingStones,time;
	
	static double sumOfDepth3NodesAB = 0;
	static double sumOfDepth3AvgBrachingFactor = 0;
	static double sumOfDepth3NodesMM = 0;
	static double sumOfDepth3EffectiveBrachingFactorAB = 0;
	static double sumOfDepth6NodesAB = 0;
	static double sumOfDepth6AvgBrachingFactor = 0;
	static double sumOfDepth6NodesMM = 0;
	static double sumOfDepth6EffectiveBrachingFactorAB = 0;
	static double sumOfDepth12NodesAB = 0;
	static double sumOfDepth12AvgBrachingFactor = 0;
	static double sumOfDepth12NodesMM = 0;
	static double sumOfDepth12EffectiveBrachingFactorAB = 0;
	
	public static void main(String[] args) {

//		MMMancaPlayer mm = new MMMancaPlayer("MinimaxPlayer", 10);
//		ABMancaPlayer ab = new ABMancaPlayer("ABMancaPlayer", 10);
//		MancalaExperiment m = new MancalaExperiment(mm, ab, 7, 4, 100000);
		//time
		
//		long startDepth12 =  System.currentTimeMillis();
//		MMMancaPlayer mm = new MMMancaPlayer("MinimaxPlayer", 12);
//		ABMancaPlayer ab = new ABMancaPlayer("ABMancaPlayer", 12);
//		MancalaExperiment m = new MancalaExperiment(mm, ab, 7, 4, 100000);
//		m.playGame();
//		long timeDepth12 = System.currentTimeMillis() - startDepth12;
//		System.out.println("Total time for 1 game of depth 12: " + timeDepth12);
//		System.out.println("NodesAB in 1sec " + sumOfDepth12NodesAB / (timeDepth12/ 1000));
//		System.out.println("NodesMM in 1sec " + sumOfDepth12NodesMM / (timeDepth12/ 1000));
//		System.out.println("Depth12AvgBrachingFactor " + sumOfDepth12AvgBrachingFactor);
//		System.out.println("Depth12EffBrachingFactor " + sumOfDepth12EffectiveBrachingFactorAB);
		
		long startDepth3 =  System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			MMMancaPlayer mm = new MMMancaPlayer("MinimaxPlayer", 6);
			ABMancaPlayer ab = new ABMancaPlayer("ABMancaPlayer", 6);
			MancalaExperiment m = new MancalaExperiment(mm, ab, 7, 4, 100000);
			m.playGame();
		}
		long timeDepth3 = System.currentTimeMillis() - startDepth3;
		System.out.println("Total time for 100 game of depth 6: " + timeDepth3);
		System.out.println("NodesAB in 1sec " + sumOfDepth3NodesAB / (timeDepth3/ 1000));
		System.out.println("NodesMM in 1sec " + sumOfDepth3NodesMM / (timeDepth3/ 1000));
		System.out.println("Depth6AvgBrachingFactor " + sumOfDepth3AvgBrachingFactor / 100);
		System.out.println("Depth6EffBrachingFactor " + sumOfDepth3EffectiveBrachingFactorAB / 100);
		
//		long startDepth3 =  System.currentTimeMillis();
//		for (int i = 0; i < 1000; i++) {
//			MMMancaPlayer mm = new MMMancaPlayer("MinimaxPlayer", 3);
//			ABMancaPlayer ab = new ABMancaPlayer("ABMancaPlayer", 3);
//			MancalaExperiment m = new MancalaExperiment(mm, ab, 7, 4, 100000);
//			m.playGame();
//		}
//		long timeDepth3 = System.currentTimeMillis() - startDepth3;
//		System.out.println("Total time for 1000 game of depth 3: " + timeDepth3);
//		System.out.println("NodesAB in 1sec " + sumOfDepth3NodesAB / (timeDepth3/ 1000));
//		System.out.println("NodesMM in 1sec " + sumOfDepth3NodesMM / (timeDepth3/ 1000));
//		System.out.println("Depth6AvgBrachingFactor " + sumOfDepth3AvgBrachingFactor / 1000);
//		System.out.println("Depth6EffBrachingFactor " + sumOfDepth3EffectiveBrachingFactorAB / 1000);
		
//		long startDepth3 =  System.currentTimeMillis();
//		for (int i = 0; i < 10; i++) {
//			MMMancaPlayer mm = new MMMancaPlayer("MinimaxPlayer", 9);
//			ABMancaPlayer ab = new ABMancaPlayer("ABMancaPlayer", 9);
//			MancalaExperiment m = new MancalaExperiment(mm, ab, 7, 4, 100000);
//			m.playGame();
//		}
//		long timeDepth3 = System.currentTimeMillis() - startDepth3;
//		System.out.println("Total time for 10 games of depth 9: " + timeDepth3);
//		System.out.println("NodesAB in 1sec " + sumOfDepth3NodesAB / (timeDepth3/ 1000));
//		System.out.println("NodesMM in 1sec " + sumOfDepth3NodesMM / (timeDepth3/ 1000));
//		System.out.println("Depth6AvgBrachingFactor " + sumOfDepth3AvgBrachingFactor / 10);
//		System.out.println("Depth6EffBrachingFactor " + sumOfDepth3EffectiveBrachingFactorAB / 10);
		
//		System.out.println("Depth6AvgBrachingFactor " + sumOfDepth6AvgBrachingFactor / 2 );
//		System.out.println("Depth6AvgBrachingFactor " + sumOfDepth6EffectiveBrachingFactorAB / 2);
		//time
		
		
		// time
		// 8 games depth = 2 - add up all generated nodes / time; sum of branching factors / games
		// 4 games depth = 6 - 
		// 2 game depth = 12
		
		//b^d = nodes => d = logb(nodes)
		// nodes / node per second = second to go to depth d
		
		//2. D(mm) = logb(mm)(nodes in T)
		// nodes per second * T = nodes
		// D(ab) = logb(ab)(nodes in T)
//		12 in 2s (mm)
		

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
		sumOfDepth12NodesAB += ((MiniMax) p2).getNodesGenerated();
		sumOfDepth12AvgBrachingFactor += ((MiniMax) p1).getAveBranchingFactor();
		sumOfDepth12NodesMM += ((MiniMax) p1).getNodesGenerated();
		sumOfDepth12EffectiveBrachingFactorAB += ((MiniMax) p2).getEffectiveBranchingFactor();
		System.out.println(p1.name + " generated " + ((MiniMax) p1).getNodesGenerated());
		System.out.println(p2.name + " generated " + ((MiniMax) p2).getNodesGenerated());
		System.out.println(p1.name + " has an avg branching factor of " + ((MiniMax) p1).getAveBranchingFactor());
//		System.out.println(p2.name + " has an avg branching factor of " + ((MiniMax) p2).getAveBranchingFactor());
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
