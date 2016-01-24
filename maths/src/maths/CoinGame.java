package maths;

import java.util.Random;

/**
 * 4 / 7 0.57142857142857142857142857142857
 * 
 * @author Ivan
 * 
 *         1 in 100 when I won 73% of games over 50 games.
 */
public class CoinGame {

	public static void main(String args[]) {

		for (int y = 0; y < 1000; y++) {
			int games = 50;
			int player1wins = 0;
			for (int i = 0; i < games; i++) {
				if (whichPlayerWins() == 1) {
					player1wins++;
				}
			}
			double outcome = (player1wins * 100.0 / games);
			if (outcome > 73)
				System.out.println("Player 1 wins %" + outcome);
		}
	}

	private static int whichPlayerWins() {
		int player = 1;

		while (true) {
			if (isTwoHeads()) {
				return player;
			}

			if (player == 1) {
				player = 2;
			} else {
				player = 1;
			}
		}

	}

	// Player 1 wins %57.0717 Secure Random
	// Player 1 wins %57.1939

	// Player 1 wins %57.1128 Random
	// Player 1 wins %57.1671
	// Player 1 wins %57.0999
	private static boolean isTwoHeads() {
		// SecureRandom random = new SecureRandom();
		Random random = new Random();
		int n = random.nextInt(4);
		return (n == 0);
	}
}
