package ttt;

public class TTTGame {
	
	// Instance variables--------------
	
	protected int[][] board;
	protected int rowDim;
	protected int colDim;
	protected int currentPlayer;
	protected int playerOnePiece;
	protected int playerTwoPiece;
	protected String status;
	protected int against;
	protected int move;

	// Constructors ------------------------------------------------------------------
	
	/**
	 * Constructs 3x3 board
	 */
	public TTTGame(){
		this(3, 3);
	}
	
	/**
	 * Constructs generic board
	 * @param rowDim	row dimension
	 * @param colDim	column dimension
	 */
	public TTTGame(int rowDim, int colDim){
		board = new int[rowDim][colDim];
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				board[i][j] = -1;
			}
		}
		status = "No winner yet!";
	}
	
	// Instance methods ---------------------------------------------------------------
	/**
	 * Sets currentPlayer to next player!
	 * If currentPlayer = 0, currentPlayer is 1 or otherwise.
	 */ 
	public void nextPlayer(){
		currentPlayer = (currentPlayer + 1) % 2;
	}
	
	/**
	 * @return currentPlayer either 0 or 1.
	 */
	public int getCurrentPlayer(){
		return currentPlayer;
	}
	
	/**
	 * Adds current player's piece to each square of the board with 
	 * given row, col, and player's piece.
	 * @param row  (0 <= row <= 2) are the columns of the board
	 * @param col	(0 <= col <= 2) are the rows of the board
	 * @param currentPlayer (0 or 1) is the current player
	 */
	public void markArray(int row, int col, int currentPlayer){
		board[row][col] = currentPlayer;
	}
	
	/**
	 * Gets the player's piece from the 2-D array at a certain i and j on the board.
	 * @param i (0 <= i <= 2) indices for arrays of the board.
	 * @param j (0 <= j <= 2) indices for elements of arrays on the board.
	 * @return  the an element of board at i and j.
	 */
	public int getPlayer(int i, int j) {
		return board[i][j];
	}
	
	/**
	 * Resets to a new empty board. Current player is set 0 in default.
	 * move is set to 0.
	 */
	public void reset(){
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				board[i][j] = -1;
			}
		}
		move = 0;
		currentPlayer = 0;
		updateStatus("No winner yet!");
	}
	
	/**
	 * Checks to see if there is a tie.
	 * @return true if the move number is 9, else false.
	 */
	public boolean checkTie(){
		return move == 9;
	}
	
	/**
	 * Checks for winning for the currentPlayer either a rowWin, colWin, or both diagonal win.
	 * @param currentPlayer    ( 0 or 1 ) is the currentPlayer.
	 * @return   true is there is a rowWin, colWin or diagonal win for currentPlayer false otherwise.
	 */
	public boolean checkWin(int currentPlayer){
		return (colWin(currentPlayer)
			|| rowWin(currentPlayer)
			|| diagWin1(currentPlayer)
			|| diagWin2(currentPlayer));
	}
	
	/**
	 * Checks for every column if there is a win.
	 * @param 	currentPlayer ( 0 or 1) is current player index.
	 * @return  true if there is a colWin false otherwise.
	 */
	public boolean colWin(int currentPlayer){
		for (int i=0; i<3; i++) {
			if ((board[i][0] == currentPlayer) &&
					(board[i][1] == currentPlayer) &&
					(board[i][2] == currentPlayer)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks for every row if there is a win.
	 * @param currentPlayer (0 or 1) is current player index.
	 * @return  true if there is a rowWin false otherwise.
	 */
	public boolean rowWin(int currentPlayer){
		for (int i=0; i<3; i++) {
			if ((board[0][i] == currentPlayer) &&
					(board[1][i] == currentPlayer) &&
					(board[2][i] == currentPlayer)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks for descending (\) diagonal win
	 * @param currentPlayer  (0 or 1) is current player index.
	 * @return true if there is a (\) diagonal win false otherwise.
	 */
	public boolean diagWin1(int currentPlayer){
		if ((board[0][0] == currentPlayer) &&
				(board[1][1] == currentPlayer) &&
				(board[2][2] == currentPlayer)) {
			return true;
		}
		else {
			return false;
		}
	}	
	
	/**
	 * Checks for descending (/) diagonal win
	 * @param currentPlayer  (0 or 1) is current player index.
	 * @return  true if there is a (/) diagonal win false otherwise.
	 */
	public boolean diagWin2(int currentPlayer){
		if ((board[0][2] == currentPlayer) &&
				(board[1][1] == currentPlayer) &&
				(board[2][0] == currentPlayer)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Sets the Players piece that is either 0 or 1 based on chosen piece.
	 * @param p (0 or 1) player's piece of the board.
	 */
	public void setPlayerOnePiece(int p) {
		playerOnePiece = p;
		setPlayerTwoPiece((p+1)%2);
	}
	
	/**
	 * Sets player two piece depending on p.
	 * @param p (0 or 1) player's piece of the board.
	 */
	public void setPlayerTwoPiece(int p) {
		playerTwoPiece = p;
	}
	
	/**
	 * Gets the player's piece on board  based on chosen piece.
	 * @param player (0 or 1) the player's piece denoted as the player on the board.
	 * @return  the player's piece on the board.
	 */
	public int getPiece(int player) {
		if (player == 0) {
			return playerOnePiece;
		}
		else {
			return playerTwoPiece;
		}
	}
	
	/**
	 * Updates the status of game to either a win or tie.
	 * @param s the string that is the status of the game.
	 */
	public void updateStatus(String s) {
		status = s;
	}
	
	/**
	 * Gets the status of the game for win or tie.
	 * @return the status of the game.
	 */
	public String getStatus() {
		return status;
	}
}