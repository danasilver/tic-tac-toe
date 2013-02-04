package ttt;

import java.util.*;

@SuppressWarnings(value = { "serial" })
public class Computer extends Stack<Integer>{
	
	int[] onedboard = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
	int[][] wins = {{0,1,2},
					{3,4,5},
					{6,7,8},
					{0,3,6},
					{1,4,7},
					{2,5,8},
					{0,4,8},
					{2,4,6}};
	
	/**
	 * Constructs a board
	 * @param board		the instance of TTTGame
	 */
	public Computer(TTTGame board) {
		onedboard[0] = board.getPlayer(0, 0);
		onedboard[1] = board.getPlayer(1, 0);
		onedboard[2] = board.getPlayer(2, 0);
		onedboard[3] = board.getPlayer(0, 1);
		onedboard[4] = board.getPlayer(1, 1);
		onedboard[5] = board.getPlayer(2, 1);
		onedboard[6] = board.getPlayer(0, 2);
		onedboard[7] = board.getPlayer(1, 2);
		onedboard[8] = board.getPlayer(2, 2);
		
	}
	
	/**
	 * Checks for a tie on the board
	 * @param b		a board
	 * @return		true for a tie, false if not
	 */
	public boolean checkTie(int[] b) {
		for (int i=0; i<9; i++) {
			if (b[i] == -1) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if the game is over
	 * @param b		a board
	 * @return		true if the game is over, false if not
	 */
	public boolean checkGameOver(int[] b) {
		// check if a win has occurred
		for (int i=0; i<8; i++) {
			if ((b[wins[i][0]] == b[wins[i][1]]) && (b[wins[i][1]] == b[wins[i][2]]) && (b[wins[i][0]] != -1)) {
				return true;	
			}
		}
		// check if a tie has occurred
		if (checkTie(b)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns an array with the open positions on a given board marked as -1
	 * Taken positions are marked as 0
	 * @param b		a board
	 * @return		an array of length 9 marked with -1 for an open spot, 0 for a taken spot
	 */
	public int[] openPositions(int[] b) {
		int[] openPositions = new int[9];
		for (int i=0; i<9; i++) {
			if (b[i] == -1) {
				openPositions[i] = -1;
			}
			else {
				openPositions[i] = 0;
			}
		}
		return openPositions;
	}
	
	/**
	 * Marks a given board at index i with value v
	 * and pushes the index to the Stack "lastMoves"
	 * @param b		a board
	 * @param i		index
	 * @param v		value
	 */
	public void mark(int[] b, int i, int v) {
		b[i] = v;
	}
	
	/**
	 * Converts a mark on space p to a mark on a 2d array
	 * @param p		a space p on a 1d array
	 * @return		an int array [i,j] that corresponds to the 2d array
	 */
	public int[] convertMark(int p) {
		int i;
		int j;
		// find i, j
		if (p == 0) {
			i = 0;
			j = 0;
		}
		else if (p == 1) {
			i = 0;
			j = 1;
		}
		else if (p == 2) {
			i = 0;
			j = 2;
		}
		else if (p == 3) {
			i = 1;
			j = 0;
		}
		else if (p == 4) {
			i = 1;
			j = 1;
		}
		else if (p == 5) {
			i = 1;
			j = 2;
		}
		else if (p == 6) {
			i = 2;
			j = 0;
		}
		else if (p == 7) {
			i = 2;
			j = 1;
		}
		else {
			i = 2;
			j = 2;
		}
		// m[i,j] = 2d position
		int[] m = {j,i};
		return m;
	}
	
	/**
	 * Scores the game. -1 for a loss, 0 for a tie, 1 for a win.
	 * ASSERT: game is over
	 * @param b		a board
	 * @return		a score (-1, 0, 1)
	 */
	public int getScore(int[] b) {
		// check if it's a win
		for (int i=0; i<8; i++) {
			if ((b[wins[i][0]] == b[wins[i][1]]) && (b[wins[i][1]] == b[wins[i][2]])) {
				// check who the winner is
				if (b[wins[i][0]] == 0) {
					// opponent (P1) gets score -1
					return -1;
					
				}
				else if (b[wins[i][0]] == 1){
					// computer (P2) gets score 1
					return 1;
				}
			}
		}
		// assert: game is a tie
		// tie game is score 0
		return 0;
	}
	
	/**
	 * Get's the 1d array position of the computer's move
	 * @param gameInstance 	a board
	 * @return				the 1d position of the computer's move
	 */
	public int move(int[] gameInstance) {
		//int movePosition;
		//int score;
		//score = maxMove(gameInstance)[0];
		
		// calls maxMove on the board
		// gets the second value of the returned array
		int[] m = maxMove(gameInstance);
		System.out.println(m[0]);
		return m[1];
		//return maxMove(gameInstance)[1];
	}
	
	/**
	 * Gets the computers move as a space on a 2d array
	 * @return	an int array with the space as [i,j]
	 */
	public int[] getCompMove() {
		int m = move(onedboard);
		int[] ij = new int[2];
		ij[0] = convertMark(m)[0];
		ij[1] = convertMark(m)[1];
		return ij;
	}
	
	/**
	 * Recursively (with minMove) gets the space where the computer should move
	 * @param gameInstance	a board to perform minimax on
	 * @return				an int array containing the best max score and the best max move
	 */
	public int[] maxMove(int[] gameInstance) {
		// initialize score and movePosition
		int score;
		int movePosition;
		int bestScoreMax = -2;
		int bestMoveMax = -1;
		// for each position in gameInstance check for an open position
		for (int i=0; i<9; i++) {
			if (openPositions(gameInstance)[i] == -1) {
				// at the open position, mark gameInstance at i with 1
				// (the computer is player 1)
				mark(gameInstance, i, 1);
			
				// if the game is over after that move
				if (checkGameOver(gameInstance)) {
					// score the game
					// update the movePosition
					score = getScore(gameInstance);
					movePosition = i;
				}
				else {
					// call minMove to get the score
					// update the position using minMove
					// this simulates the next player making an optimal move
					int[] m = minMove(gameInstance);
					score = m[0];
					movePosition = i;
				}
				// reset gameInstance to the state before adding a 1 at index i
				// allows simulation of another game at another spot
				
//				System.out.println(i + " " + score);
//				for (int j=0; j<9; j++) {
//					System.out.print(gameInstance[j] + " ");
//				}
//				System.out.println("");
				
				gameInstance[i] = -1;
				
				// if score > current bestScoreMax
				// then update bestScoreMax and bestMoveMax accordingly
				if (score > bestScoreMax) {
					bestScoreMax = score;
					bestMoveMax = movePosition;
				}
			}
		}
		// return the bestScoreMax and bestMoveMax
		int[] r = {bestScoreMax, bestMoveMax};
		return r;
	}
	
	/**
	 * Recursively (with maxMove) gets the space where the human should optimally move
	 * @param gameInstance	a board to perform minimax on
	 * @return				an int array containing the best min score and the best min move
	 */
	public int[] minMove(int[] gameInstance) {
		// initialize score and movePosition
		int score;
		int movePosition;
		int bestScoreMin = 2;
		int bestMoveMin = -1;
		// for each position in gameInstance check for an open position
		for (int i=0; i<9; i++) {
			if (openPositions(gameInstance)[i] == -1) {
				// at the open position, mark gameInstance at i with 0
				// (the human is player 0)
				mark(gameInstance, i, 0);
				
				// if the game is over after that move
				if (checkGameOver(gameInstance)) {
					// score the game
					// update the movePosition
					score = getScore(gameInstance);
					movePosition = i;
				}
				else {
					// call maxMove to get the score
					// update the position using maxMove
					// this simulates the next player making an optimal move
					int[] m = maxMove(gameInstance);
					score = m[0];
					movePosition = i;
				}
				
				// reset gameInstance to the state before adding a 1 at index i
				// allows simulation of another game at another spot
				gameInstance[i] = -1;
			
				// if score < current bestScoreMin
				// then update bestScoreMin and bestMoveMin accordingly
				if (score < bestScoreMin) {
					bestScoreMin = score;
					bestMoveMin = movePosition;
				}
			}
		}
		// return the bestScoreMin and bestMoveMin
		int[] r = {bestScoreMin, bestMoveMin};
		return r;
	}
		
}
