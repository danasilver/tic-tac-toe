package ttt;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

@SuppressWarnings(value = { "serial" }) 
public class TicTacToeApplet extends Applet implements ActionListener, ItemListener{
	
	TTTGame game;
	BoardCanvas board;
	Label titleLabel, winLabel;
	Panel bottomPanel, buttonPanel;
	Choice againstChoice, pieceChoice;
	Button resetButton;
	
	/**
	 * Initializes the applet.
	 */
	public void init() {
		setFont(new Font("Tahoma", Font.BOLD, 14));
		setLayout(new BorderLayout());
		
		game = new TTTGame();
		game.setPlayerOnePiece(0);
		board = new BoardCanvas(this, game);
		board.addMouseListener(board);
		
		board.setBackground(Color.yellow);  // Delete later
		
		add("North", makeTitleLabel());
		add("Center", board);
		add("South", makeBottomPanel());
	}
	
	/**
	 * Makes the title label for the applet.
	 * @return The label "Tic Tac Toe" as the title.
	 */
	public Label makeTitleLabel() {
		titleLabel = new Label("Tic Tac Toe");
		titleLabel.setAlignment(Label.CENTER);
		titleLabel.setForeground(Color.green);
		titleLabel.setBackground(Color.black);
		return titleLabel;
	}
	
	/**
	 * Makes a panel with choice buttons "againstChoice" and "pieceChoice",
	 * and button "resetButton".
	 * @return The panel that contains the buttons to start the game.
	 */
	public Panel makeButtonPanel() {
		againstChoice = new Choice();
		againstChoice.addItemListener(this);
		againstChoice.add("--Play against--");
		againstChoice.add("Computer");
		againstChoice.add("Human");
		
		pieceChoice = new Choice();
		pieceChoice.addItemListener(this);
		pieceChoice.addItem("--Player 1's Piece--");
		pieceChoice.addItem("X");
		pieceChoice.addItem("O");
		
		resetButton = new Button("Reset");
		resetButton.addActionListener(this);
		
		buttonPanel = new Panel();
		buttonPanel.add(againstChoice);
		buttonPanel.add(pieceChoice);
		buttonPanel.add(resetButton);
		
		return buttonPanel;
	}
	
	/**
	 * Makes a panel with that contains "winLabel" and "buttonPanel".
	 * @return A panel that contains the winLabel and the buttonPanel.
	 */
	public Panel makeBottomPanel() {
		bottomPanel = new Panel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.setBackground(Color.green);
		bottomPanel.add("South", makeButtonPanel());
		return bottomPanel;
	}
	
	/**
	 * Updates the pieces and opponent when the selection state is changed.
	 */
	public void itemStateChanged(ItemEvent e) {
		// if pieceChoice is changed
		if (e.getSource() == pieceChoice) {
			// if pieceChoice is index 2
			if (pieceChoice.getSelectedIndex() == 2) {
				// player 1 piece is O
				game.setPlayerOnePiece(1);
			}
			else {
				// else, player 1 piece is X
				game.setPlayerOnePiece(0);
			}
		}
		// if againstChoice is changed
		if (e.getSource() == againstChoice) {
			// if againstChoice is at index 1
			if (againstChoice.getSelectedIndex() == 1) {
				// game is against the computer
				game.against = 1;
			}
			else {
				// else game is against a human
				game.against = 2;
			}
		}
	}
	
	/**
	 * When the reset button is pressed, reset the board.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == resetButton) {
			game.reset();
			board.repaint();
			
		}
	}
}