package ttt;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings(value = { "serial" })
class BoardCanvas extends Canvas implements MouseListener{

	public static final int BORDER = 40;
	
	// Instance Variables
	TicTacToeApplet applet;
	TTTGame game;
	
	/**
	 * Constructor for a boardCanvas
	 * @param a The applet
	 * @param g The game
	 */
	public BoardCanvas(TicTacToeApplet a, TTTGame g) {
		applet = a;
		game = g;
	}
	
	/**
	 * Paints the board
	 */
	public void paint(Graphics g) {
		Dimension d = getSize();
		int w = Math.min(d.width, d.height) - BORDER;
		int x0 = (d.width-w)/2;
		int y0 = (d.height-w)/2;
		int x1 = x0+w;
		int y1 = y0+w;
		drawBoard(g, game, x0, y0, x1, y1, w);
		centerString(g, game.getStatus(), d.width/2, d.height-10);
	}
	
	/**
	 * Helper method to draw a string centered at x, y
	 * From exam 2 code
	 * @param g		the graphics context
	 * @param s		the string
	 * @param x		x coordinate
	 * @param y		y coordinate
	 */
    public static void centerString(Graphics g, String s, int x, int y) {
    	g.setFont(new Font("Tahoma", Font.BOLD, 18));
    	FontMetrics fm = g.getFontMetrics(g.getFont());
    	int xs = x - fm.stringWidth(s)/2 + 1;
    	int ys = y + fm.getAscent()/3 + 1;
    	g.drawString(s, xs, ys);
    }
	
	/**
	 * Draws the board
	 * @param g     the graphics context
	 * @param game  the instance of TTTGame
	 * @param x0    top left x coordinate of board
	 * @param y0    top left y coordinate of board
	 * @param x1    bottom right x coordinate of board
	 * @param y1    bottom right y coordinate of board
	 * @param w     the width of the board
	 */
	public void drawBoard(Graphics g, TTTGame game, int x0, int y0, int x1, int y1, int w) {
		g.fillRect(x0, y0+w/3, w, 4);
		g.fillRect(x0, y0+(2*w)/3, w, 4);
		g.fillRect(x0+w/3, y0, 4, w);
		g.fillRect(x0+(2*w)/3, y0, 4, w);
		drawGame(g, game, x0, y0, w);
	}
	
	/**
	 * Draws the pieces on the board
	 * @param g		the graphics context
	 * @param game	the instance of the game
	 * @param x0	top left x coordinate
	 * @param y0	top left y coordinate
	 * @param w		width of the board
	 */
	public void drawGame(Graphics g, TTTGame game, int x0, int y0, int w) {
		int x = x0;
		int y = y0;
		int x1 = x0 + w/3;
		int y1 = y0 + w/3;
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				if ((game.board[i][j] == 0) || (game.board[i][j] == 1)) {
					drawPiece(g, game, x, y, x1, y1, game.board[i][j]);
				}
				y += w/3;
				y1 += w/3;
			}
			y = y0;
			y1 = y0 + w/3;
			x += w/3;
			x1 += w/3;
		}
	}

	/**
	 * On the mouse press, determine the x and y coordinates (normalized for the size of the board)
	 * and calls a markSpace function according to the opponent
	 */
	public void mousePressed(MouseEvent event) {
		Point p = event.getPoint();
		Dimension d = getSize();
		int w = Math.min(d.width, d.height) - BORDER;
		int x0 = (d.width-w)/2;
		int y0 = (d.height-w)/2;
		// if the game is against a human
		if (game.against == 2) {
			markSpace(getRow(p, x0, y0, w), getCol(p, x0, y0, w), game.getCurrentPlayer());
		}
		// if the game is against a computer
		if (game.against == 1) {
			markSpaceComputer(getRow(p, x0, y0, w), getCol(p, x0, y0, w), game.getCurrentPlayer());
		}
	}
	
	/**
	 * Gets the row the click is in.
	 * @param p		point that was clicked
	 * @param x0	top left x coordinate of the board
	 * @param y0	top left y coordinate of the board
	 * @param w		width of the board
	 * @return		the row of the click
	 */
	public int getRow(Point p, int x0, int y0, int w) {
		int row = 0;
		if (p.x > x0 + w/3) {
			row++;
		}
		if (p.x > x0 + (2*w)/3) {
			row++;
		}
		return row;
	}
	
	/**
	 * Gets the column that the click is in
	 * @param p		point that was clicked
	 * @param x0	top left x coordinate of the board
	 * @param y0	top left y coordinate of the board
	 * @param w		width of the board
	 * @return		the column of the click
	 */
	public int getCol(Point p, int x0, int y0, int w) {
		int col = 0;
		if (p.y > y0 + w/3) {
			col++;
		}
		if (p.y > y0 + (2*w)/3) {
			col++;
		}
		return col;
	}
	
	/**
	 * If there is no winner on the board and the clicked space is empty,
	 * mark the with the correct player's piece and move to the next player.
	 * @param row		the row clicked
	 * @param col		the column clicked
	 * @param player	the player who clicked
	 */
	public void markSpace(int row, int col, int player) {
		if ((game.board[row][col] == -1) && (!game.checkWin(0)) && (!game.checkWin(1))) {
			// if the clicked space is empty and there is no win on the board
			// mark the array
			game.markArray(row, col, player);
			// increment the move counter
			game.move ++;
			repaint();
			// after the move, check for a win or a tie
			if (game.checkWin(player)) {
				int reportedPlayer = player + 1;
				game.updateStatus("Player " + reportedPlayer + " Wins! Click reset for a new game.");
			}
			else if (game.checkTie()) {
				game.updateStatus("Tie Game! Click reset for a new game.");
			}
			// advance to the next player
			game.nextPlayer();
		}
	}
	
	/**
	 * Marks the space for a player against a computer and makes the computer's move.
	 * @param row		the row of the click
	 * @param col		the column of the click
	 * @param player	the current player (for the click)
	 */
	public void markSpaceComputer(int row, int col, int player) {
		if ((game.board[row][col] == -1) && (!game.checkWin(0)) && (!game.checkWin(1))) {
			// if the clicked space is empty and there is no winner on the board
			// mark the array
			game.markArray(row, col, player);
			// increment the move counter
			game.move++;
			repaint();
			// check for wins and ties after the human move
			if (game.checkWin(player)) {
				int reportedPlayer = player + 1;
				game.updateStatus("Player " + reportedPlayer + " Wins! Click reset for a new game.");
			}
			else if (game.checkTie()) {
				game.updateStatus("Tie Game! Click reset for a new game.");
			}
			// advance to the next player
			game.nextPlayer();
			
			if ((!game.checkWin(0)) && (!game.checkWin(1))) {
				// if there is no win following the human move
				
				// create a new Computer
				Computer c = new Computer(game);
				int[] m = new int[2];
				// get the computer's move
				m = c.getCompMove();
				// mark the computer's move
				game.markArray(m[0], m[1], game.currentPlayer);
				repaint();
				// check for a win or a tie
				if (game.checkWin(1)) {
					game.updateStatus("Computer Wins! Click reset for a new game.");
				}
				else if (game.checkWin(0)) {
					game.updateStatus("Player 1 Wins! Click for a new game.");
				}
				else if (game.checkTie()) {
					game.updateStatus("Tie Game! Click reset for a new game.");
				}
			}
			// increment the move counter and advance to the next player
			game.move++;
			game.nextPlayer();
		}
	}
	
	// Draw the pieces ----------------------------------------------//
	/**
	 * Determines whether to draw an X or an O
	 * based on the player
	 * @param g			graphics context
	 * @param game		the instance of TTTGame
	 * @param x0		top left x coordinate
	 * @param y0		top left y coordinate
	 * @param x1		bottom right x coordinate
	 * @param y1		bottom right y coordinate
	 * @param player	the current player
	 */
	public void drawPiece(Graphics g, TTTGame game, int x0, int y0, int x1, int y1, int player) {
		if (game.getPiece(player) == 0) {
			drawX(g, game, x0, y0, x1, y1);
		}
		else {
			drawO(g, game, x0, y0, x1, y1);
		}
	}
	
	/**
	 * Draws an X in the appropriate spot
	 * @param g		the graphics context
	 * @param game	the instance of TTTGame
	 * @param x0	top left x coordinate
	 * @param y0	top left y coordinate
	 * @param x1	bottom right x coordinate
	 * @param y1	bottom right y coordinate
	 */
	public void drawX(Graphics g, TTTGame game, int x0, int y0, int x1, int y1) {
		g.drawLine(x0+20, y0+20, x1-20, y1-20);
		g.drawLine(x0+20, y1-20, x1-20, y0+20);
	}
	
	/**
	 * Draws an O in the appropriate spot
	 * @param g		the graphics context
	 * @param game	the instance of TTTGame
	 * @param x0	top left x coordinate
	 * @param y0	top left y coordinate
	 * @param x1	bottom right x coordinate
	 * @param y1	bottom right y coordinate
	 */
	public void drawO(Graphics g, TTTGame game, int x0, int y0, int x1, int y1) {
		g.drawOval(x0+20, y0+20, (x1-x0)-40, (x1-x0)-40);
	}
	//---------------------------------------------------------------//
	
	// methods required to implement MouseListener
	public void mouseReleased(MouseEvent event) { }
	public void mouseClicked(MouseEvent event) { }
	public void mouseEntered(MouseEvent event) { }
	public void mouseExited(MouseEvent event) { }
	public void mouseMoved(MouseEvent event) { }
}