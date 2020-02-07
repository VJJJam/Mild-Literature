import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Visual implements ActionListener {
	
	private final int WIDTH = 1750;
	private final int HEIGHT = 1000;
	private final Color BACKCOLOR = new Color(0,105,105);
	private final Color TEAM_1_COLOR = Color.ORANGE;
	private final Color TEAM_2_COLOR = Color.GRAY;
	private final Color BROWN = new Color(110, 42, 42);
	private final Font FONT = new Font("Dialog", Font.PLAIN, 18);
	
	/* Panels */
	private JPanel panel1, panel2, panel3, panel4, panel5, panel6;
	private JPanel choosePanel1, choosePanel2, choosePanel3, choosePanel4,
				   choosePanel5, choosePanel6;
	private JPanel scorePanel;
	/* JTextArea */
	private JTextArea score1, score2;
	/* JComboBoxes */
	private JComboBox<String> box1, box2, box3, box4, box5, box6;
	/* JTextFields */
	private JTextField field1, field2, field3, field4, field5, field6;
	/* Buttons */
	private JButton askButton1, askButton2, askButton3,
					askButton4, askButton5, askButton6;
	private JButton upperButton1, upperButton2, upperButton3,
					upperButton4, upperButton5, upperButton6;
	private JButton lowerButton1, lowerButton2, lowerButton3,
					lowerButton4, lowerButton5, lowerButton6;
	private JButton flipButton;
	/* JLabels */
	private JLabel errorText1, errorText2, errorText3, errorText4,
				   errorText5, errorText6;
	
	/* Game window */
	private JFrame gameWindow;
	/* Teams */
	public Player[] team1, team2;
	public int points1, points2;
	public Player currentPlayer;
	/* Misc.*/
	int flip = 0; // flip == 0  --> Default: hidden
				  // + 1  --> Reveal cards
				  // flip >> 1  --> Default: hidden, -1
	
	public Visual(Player[] team1, Player[] team2) {
		this.team1 = team1;
		this.team2 = team2;
		points1 = 0;
		points2 = 0;
		
		makeGame();
	}
	
	public void makeGame() {
		gameWindow = new JFrame("Literature");
		// Users cannot resize the window
		gameWindow.setResizable(false);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.getContentPane().setBackground(BACKCOLOR);
		gameWindow.getContentPane().setLayout(null);
		gameWindow.setBounds(0, 0, WIDTH, HEIGHT);
		
		/*********************************/
		panel1 = new JPanel(new GridBagLayout());
		panel1.setBounds(0, 0, 700, 150);
		panel1.setBackground(TEAM_1_COLOR);
		
		int count = 1;
//		System.out.println("Panel"+(count)+"	x:"+panel1.getX()+"	y:" +panel1.getY()+ "	width:"+panel1.getWidth()+"	height:"+panel1.getHeight());
		gameWindow.add(panel1);
		// Illustrate player's hand
		Player p = team1[count - 1];
		flip = 1;
		updateVisualHand(p);
		count++;
		flip = 0;
		
		// Set up upper-suit check button
		upperButton1 = new JButton("UPPER SUIT");
		upperButton1.setLayout(null);
		upperButton1.setFont(new Font("Dialog", Font.BOLD, 14));
		upperButton1.setBounds(15, panel1.getHeight(), 125, 50);
		upperButton1.addActionListener(this);
		// Set up lower-suit check button
		lowerButton1 = new JButton("LOWER SUIT");
		lowerButton1.setLayout(null);
		lowerButton1.setFont(new Font("Dialog", Font.BOLD, 14));
		lowerButton1.setBounds(15, upperButton1.getY() + upperButton1.getHeight() + 20, 125, 50);
		lowerButton1.addActionListener(this);
		// Add to game window
		gameWindow.add(upperButton1);
		gameWindow.add(lowerButton1);
		
		// Now for the ask menu
		choosePanel1 = new JPanel(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.insets = new Insets(0,0,0,0);
		choosePanel1.setBounds(panel1.getWidth() / 5, panel1.getHeight(), 400, 60);
		choosePanel1.setBackground(TEAM_2_COLOR);
		// Onto the drop menu for the suits
		String[] options = {"Diamonds", "Clubs", "Hearts", "Spades"};
		box1 = new JComboBox<>(options);
		box1.setSelectedIndex(0);
		box1.setFont(FONT);
		// Onto the text field for which card number
		field1 = new JTextField();
		field1.setPreferredSize(new Dimension(50, 30));
		field1.setFont(FONT);
		// Onto the button to ask for the card
		askButton1 = new JButton("Ask");
		askButton1.setPreferredSize(new Dimension(100, 30));
		askButton1.setFont(FONT);
		// Onto the error label; in case their input is invalid
		errorText1 = new JLabel("[3,..9, A, K, Q, J]");
		errorText1.setFont(new Font("Dialog", Font.ITALIC, 15));
		errorText1.setForeground(choosePanel1.getBackground());
		
		// Add ALL to choosePanel1
		con.gridx = 0;
		choosePanel1.add(box1, con);
		con.gridx = 1;
		choosePanel1.add(field1, con);
		con.gridx = 2;
		choosePanel1.add(askButton1, con);
		askButton1.addActionListener(this);
		con.gridx = 1;
		con.gridy = 2;
		choosePanel1.add(errorText1, con);
		// add to window
		gameWindow.add(choosePanel1);
		// Hide visibility until error occurs
		choosePanel1.setVisible(false);
		
		// ONTO LABELING PLAYER
		JTextArea p1 = new JTextArea("P1");
		p1.setFont(new Font("Dialog", Font.BOLD, 75));
		p1.setEditable(false);
		p1.setBackground(BACKCOLOR);
		p1.setForeground(TEAM_1_COLOR);
		p1.setBounds(panel1.getWidth() - 100, panel1.getHeight(), 100, 100);
		// Add to game window
		gameWindow.add(p1);
		
//		// ONTO FLIP BUTTON
//		flip1 = new JButton("FLIP");
//		flip1.setBounds(p1.getX(), p1.getY() + p1.getHeight(), p1.getWidth() - 20, 25);
//		flip1.setFont(new Font("Dialog", Font.BOLD, 12));
//		flip1.setBackground(TEAM_1_COLOR);
//		flip1.addActionListener(this);
//		// Add to game window
//		gameWindow.add(flip1);
		/*******************************/
		
		/*********************************/
		panel2 = new JPanel(new GridBagLayout());
		panel2.setBounds(0, HEIGHT / 3, 700, 150);
		panel2.setBackground(BACKCOLOR);
//		System.out.println("Panel"+(count)+"	x:"+panel2.getX()+"	y:" +panel2.getY()+ "	width:"+panel2.getWidth()+"	height:"+panel2.getHeight());
		gameWindow.add(panel2);

		// Illustrate player's hand
		p = team1[count - 1];
		updateVisualHand(p);
		count++;
		
		// Set up upper-suit check button
		upperButton2 = new JButton("UPPER SUIT");
		upperButton2.setLayout(null);
		upperButton2.setFont(new Font("Dialog", Font.BOLD, 14));
		upperButton2.setBounds(15, panel2.getY() + panel2.getHeight(), 125, 50);
		upperButton2.addActionListener(this);
		// Set up lower-suit check button
		lowerButton2 = new JButton("LOWER SUIT");
		lowerButton2.setLayout(null);
		lowerButton2.setFont(new Font("Dialog", Font.BOLD, 14));
		lowerButton2.setBounds(15, upperButton2.getY() + upperButton2.getHeight() + 20, 125, 50);
		lowerButton2.addActionListener(this);
		// Add to game window
		gameWindow.add(upperButton2);
		gameWindow.add(lowerButton2);
		// Hide until later
		upperButton2.setVisible(false);
		lowerButton2.setVisible(false);
		
		// Now for the ask menu
		choosePanel2 = new JPanel(new GridBagLayout());
		con = new GridBagConstraints();
		con.insets = new Insets(0,0,0,0);
		choosePanel2.setBounds(panel2.getWidth() / 5, panel2.getY() + panel2.getHeight(), 400, 60);
		choosePanel2.setBackground(TEAM_2_COLOR);
		// Onto the drop menu for the suits
		box2 = new JComboBox<>(options);
		box2.setSelectedIndex(0);
		box2.setFont(FONT);
		// Onto the text field for which card number
		field2 = new JTextField();
		field2.setPreferredSize(new Dimension(50, 30));
		field2.setFont(FONT);
		// Onto the button to ask for the card
		askButton2 = new JButton("Ask");
		askButton2.setPreferredSize(new Dimension(100, 30));
		askButton2.setFont(FONT);
		// Onto the error label; in case their input is invalid
		errorText2 = new JLabel("[3,..9, A, K, Q, J]");
		errorText2.setFont(new Font("Dialog", Font.ITALIC, 15));
		errorText2.setForeground(choosePanel2.getBackground());
		
		// Add ALL to choosePanel1
		con.gridx = 0;
		choosePanel2.add(box2, con);
		con.gridx = 1;
		choosePanel2.add(field2, con);
		con.gridx = 2;
		choosePanel2.add(askButton2, con);
		askButton2.addActionListener(this);
		con.gridx = 1;
		con.gridy = 2;
		choosePanel2.add(errorText2, con);
		// add to window
		gameWindow.add(choosePanel2);
		// Hide visibility
		choosePanel2.setVisible(false);
		
		// ONTO LABELING PLAYER
		JTextArea p2 = new JTextArea("P2");
		p2.setFont(new Font("Dialog", Font.BOLD, 75));
		p2.setEditable(false);
		p2.setBackground(BACKCOLOR);
		p2.setForeground(TEAM_1_COLOR);
		p2.setBounds(panel2.getWidth() - 100, panel2.getY() + panel2.getHeight(), 100, 100);
		// Add to game window
		gameWindow.add(p2);
		/*******************************/
		
		/*********************************/
		panel3 = new JPanel(new GridBagLayout());
		panel3.setBounds(0, HEIGHT / 3  * 2, 700, 150);
		panel3.setBackground(BACKCOLOR);
//		System.out.println("Panel"+(count)+"	x:"+panel3.getX()+"	y:" +panel3.getY()+ "	width:"+panel3.getWidth()+"	height:"+panel3.getHeight());
		gameWindow.add(panel3);
		
		// Illustrate player's hand
		p = team1[count - 1];
		updateVisualHand(p);
		// Renew count to account for players on team 2
		count = 1;
		
		// Set up upper-suit check button
		upperButton3 = new JButton("UPPER SUIT");
		upperButton3.setLayout(null);
		upperButton3.setFont(new Font("Dialog", Font.BOLD, 14));
		upperButton3.setBounds(15, panel3.getY() + panel3.getHeight(), 125, 50);
		upperButton3.addActionListener(this);
		// Set up lower-suit check button
		lowerButton3 = new JButton("LOWER SUIT");
		lowerButton3.setLayout(null);
		lowerButton3.setFont(new Font("Dialog", Font.BOLD, 14));
		lowerButton3.setBounds(15, upperButton3.getY() + upperButton3.getHeight() + 20, 125, 50);
		lowerButton3.addActionListener(this);
		// Add to game window
		gameWindow.add(upperButton3);
		gameWindow.add(lowerButton3);
		// Hide until later
		upperButton3.setVisible(false);
		lowerButton3.setVisible(false);
		
		// Now for the ask menu
		choosePanel3 = new JPanel(new GridBagLayout());
		con = new GridBagConstraints();
		con.insets = new Insets(0,0,0,0);
		choosePanel3.setBounds(panel3.getWidth() / 5, panel3.getY() + panel3.getHeight(), 400, 60);
		choosePanel3.setBackground(TEAM_2_COLOR);
		// Onto the drop menu for the suits
		box3 = new JComboBox<>(options);
		box3.setSelectedIndex(0);
		box3.setFont(FONT);
		// Onto the text field for which card number
		field3 = new JTextField();
		field3.setPreferredSize(new Dimension(50, 30));
		field3.setFont(FONT);
		// Onto the button to ask for the card
		askButton3 = new JButton("Ask");
		askButton3.setPreferredSize(new Dimension(100, 30));
		askButton3.setFont(FONT);
		// Onto the error label; in case their input is invalid
		errorText3 = new JLabel("[3,..9, A, K, Q, J]");
		errorText3.setFont(new Font("Dialog", Font.ITALIC, 15));
		errorText3.setForeground(choosePanel3.getBackground());
		
		// Add ALL to choosePanel1
		con.gridx = 0;
		choosePanel3.add(box3, con);
		con.gridx = 1;
		choosePanel3.add(field3, con);
		con.gridx = 2;
		choosePanel3.add(askButton3, con);
		askButton3.addActionListener(this);
		con.gridx = 1;
		con.gridy = 2;
		choosePanel3.add(errorText3, con);
		// add to window
		gameWindow.add(choosePanel3);
		// Hide visibility
		choosePanel3.setVisible(false);
		
		// ONTO LABELING PLAYER
		JTextArea p3 = new JTextArea("P3");
		p3.setFont(new Font("Dialog", Font.BOLD, 75));
		p3.setEditable(false);
		p3.setBackground(BACKCOLOR);
		p3.setForeground(TEAM_1_COLOR);
		p3.setBounds(panel3.getWidth() - 100, panel3.getY() + panel3.getHeight(), 100, 100);
		// Add to game window
		gameWindow.add(p3);
		/*********************************/
		
		/*********************************/
		panel4 = new JPanel(new GridBagLayout());
		panel4.setBounds(WIDTH - 705, 0, 700, 150);
		panel4.setBackground(BACKCOLOR);
//		System.out.println("Panel"+(count)+"	x:"+panel4.getX()+"	y:" +panel4.getY()+ "	width:"+panel4.getWidth()+"	height:"+panel4.getHeight());		gameWindow.add(panel3);
		gameWindow.add(panel4);
		
		// Illustrate player's hand
		p = team2[count - 1];
		updateVisualHand(p);
		count++;
		
		// Set up upper-suit check button
		upperButton4 = new JButton("UPPER SUIT");
		upperButton4.setLayout(null);
		upperButton4.setFont(new Font("Dialog", Font.BOLD, 14));
		upperButton4.setBounds(panel4.getX() + 15, panel4.getHeight(), 125, 50);
		upperButton4.addActionListener(this);
		// Set up lower-suit check button
		lowerButton4 = new JButton("LOWER SUIT");
		lowerButton4.setLayout(null);
		lowerButton4.setFont(new Font("Dialog", Font.BOLD, 14));
		lowerButton4.setBounds(panel4.getX() + 15, upperButton4.getY() + upperButton4.getHeight() + 20, 125, 50);
		lowerButton4.addActionListener(this);
		// Add to game window
		gameWindow.add(upperButton4);
		gameWindow.add(lowerButton4);
		// Hide until later
		upperButton4.setVisible(false);
		lowerButton4.setVisible(false);
		
		// Now for the ask menu
		choosePanel4 = new JPanel(new GridBagLayout());
		con = new GridBagConstraints();
		choosePanel4.setBounds(panel4.getWidth() / 5 + panel4.getX(), panel4.getHeight(), 400, 60);
		choosePanel4.setBackground(TEAM_1_COLOR);
		// Onto the drop menu for the suits
		box4 = new JComboBox<>(options);
		box4.setSelectedIndex(0);
		box4.setFont(FONT);
		// Onto the text field for which card number
		field4 = new JTextField();
		field4.setPreferredSize(new Dimension(50, 30));
		field4.setFont(FONT);
		// Onto the button to ask for the card
		askButton4 = new JButton("Ask");
		askButton4.setPreferredSize(new Dimension(100, 30)); 
		askButton4.setFont(FONT);
		// Onto the error label; in case their input is invalid
		errorText4 = new JLabel("[3,..9, A, K, Q, J]");
		errorText4.setFont(new Font("Dialog", Font.ITALIC, 15));
		errorText4.setForeground(choosePanel4.getBackground());
		
		// Add ALL to choosePanel1
		con.gridx = 0;
		choosePanel4.add(box4, con);
		con.gridx = 1;
		choosePanel4.add(field4, con);
		con.gridx = 2;
		choosePanel4.add(askButton4, con);
		askButton4.addActionListener(this);
		con.gridx = 1;
		con.gridy = 2;
		choosePanel4.add(errorText4, con);
		// add to window
		gameWindow.add(choosePanel4);
		// Hide visibility
		choosePanel4.setVisible(false);
		
		// ONTO LABELING PLAYER
		JTextArea p4 = new JTextArea("P1");
		p4.setFont(new Font("Dialog", Font.BOLD, 75));
		p4.setEditable(false);
		p4.setBackground(BACKCOLOR);
		p4.setForeground(TEAM_2_COLOR);
		p4.setBounds(WIDTH - 100, panel4.getHeight(), 100, 100);
		// Add to game window
		gameWindow.add(p4);
		/*******************************/
		
		/*********************************/
		panel5 = new JPanel(new GridBagLayout());
		panel5.setBounds(WIDTH - 705, HEIGHT / 3, 700, 150);
		panel5.setBackground(BACKCOLOR);
//		System.out.println("Panel"+(count)+"	x:"+panel5.getX()+"	y:" +panel5.getY()+ "	width:"+panel5.getWidth()+"	height:"+panel5.getHeight());
		gameWindow.add(panel5);
		
		// Illustrate player's hand
		p = team2[count - 1];
		updateVisualHand(p);
		count++;

		// Set up upper-suit check button
		upperButton5 = new JButton("UPPER SUIT");
		upperButton5.setLayout(null);
		upperButton5.setFont(new Font("Dialog", Font.BOLD, 14));
		upperButton5.setBounds(panel5.getX() + 15, panel5.getY() + panel5.getHeight(), 125, 50);
		upperButton5.addActionListener(this);
		// Set up lower-suit check button
		lowerButton5 = new JButton("LOWER SUIT");
		lowerButton5.setLayout(null);
		lowerButton5.setFont(new Font("Dialog", Font.BOLD, 14));
		lowerButton5.setBounds(panel5.getX() + 15, upperButton5.getY() + upperButton5.getHeight() + 20, 125, 50);
		lowerButton5.addActionListener(this);
		// Add to game window
		gameWindow.add(upperButton5);
		gameWindow.add(lowerButton5);
		// Hide until later
		upperButton5.setVisible(false);
		lowerButton5.setVisible(false);
		
		// Now for the ask menu
		choosePanel5 = new JPanel(new GridBagLayout());
		con = new GridBagConstraints();
		choosePanel5.setBounds(panel5.getWidth() / 5 + panel5.getX(), panel5.getHeight() + panel5.getY(), 400, 60);
		choosePanel5.setBackground(TEAM_1_COLOR);
		// Onto the drop menu for the suits
		box5 = new JComboBox<>(options);
		box5.setSelectedIndex(0);
		box5.setFont(FONT);
		// Onto the text field for which card number
		field5 = new JTextField();
		field5.setPreferredSize(new Dimension(50, 30));
		field5.setFont(FONT);
		// Onto the button to ask for the card
		askButton5 = new JButton("Ask");
		askButton5.setPreferredSize(new Dimension(100, 30)); 
		askButton5.setFont(FONT);
		// Onto the error label; in case their input is invalid
		errorText5 = new JLabel("[3,..9, A, K, Q, J]");
		errorText5.setFont(new Font("Dialog", Font.ITALIC, 15));
		errorText5.setForeground(choosePanel5.getBackground());
		
		// Add ALL to choosePanel1
		con.gridx = 0;
		choosePanel5.add(box5, con);
		con.gridx = 1;
		choosePanel5.add(field5, con);
		con.gridx = 2;
		choosePanel5.add(askButton5, con);
		askButton5.addActionListener(this);
		con.gridx = 1;
		con.gridy = 2;
		choosePanel5.add(errorText5, con);
		// add to window
		gameWindow.add(choosePanel5);
		// Hide visibility
		choosePanel5.setVisible(false);
		
		// ONTO LABELING PLAYER
		JTextArea p5 = new JTextArea("P2");
		p5.setFont(new Font("Dialog", Font.BOLD, 75));
		p5.setEditable(false);
		p5.setBackground(BACKCOLOR);
		p5.setForeground(TEAM_2_COLOR);
		p5.setBounds(WIDTH - 100, panel5.getY() + panel5.getHeight(), 100, 100);
		// Add to game window
		gameWindow.add(p5);
		/*******************************/
		
		/*******************************/
		panel6 = new JPanel(new GridBagLayout());
		panel6.setBounds(WIDTH - 705, HEIGHT / 3  * 2, 700, 150);
		panel6.setBackground(BACKCOLOR);
//		System.out.println("Panel"+(count)+"	x:"+panel6.getX()+"	y:" +panel6.getY()+ "	width:"+panel6.getWidth()+"	height:"+panel6.getHeight());
		gameWindow.add(panel6);
		// Illustrate player's hand
		p = team2[count - 1];
		updateVisualHand(p);
		
		// Set up upper-suit check button
		upperButton6 = new JButton("UPPER SUIT");
		upperButton6.setLayout(null);
		upperButton6.setFont(new Font("Dialog", Font.BOLD, 14));
		upperButton6.setBounds(panel6.getX() + 15, panel6.getY() + panel6.getHeight(), 125, 50);
		upperButton6.addActionListener(this);
		// Set up lower-suit check button
		lowerButton6 = new JButton("LOWER SUIT");
		lowerButton6.setLayout(null);
		lowerButton6.setFont(new Font("Dialog", Font.BOLD, 14));
		lowerButton6.setBounds(panel6.getX() + 15, upperButton6.getY() + upperButton6.getHeight() + 20, 125, 50);
		lowerButton6.addActionListener(this);
		// Add to game window
		gameWindow.add(upperButton6);
		gameWindow.add(lowerButton6);
		// Hide until later
		upperButton6.setVisible(false);
		lowerButton6.setVisible(false);
		
		// Now for the ask menu
		choosePanel6 = new JPanel(new GridBagLayout());
		con = new GridBagConstraints();
		choosePanel6.setBounds(panel6.getWidth() / 5 + panel6.getX(), panel6.getHeight() + panel6.getY(), 400, 60);
		choosePanel6.setBackground(TEAM_1_COLOR);
		// Onto the drop menu for the suits
		box6 = new JComboBox<>(options);
		box6.setSelectedIndex(0);
		box6.setFont(FONT);
		// Onto the text field for which card number
		field6 = new JTextField();
		field6.setPreferredSize(new Dimension(50, 30));
		field6.setFont(FONT);
		// Onto the button to ask for the card
		askButton6 = new JButton("Ask");
		askButton6.setPreferredSize(new Dimension(100, 30)); 
		askButton6.setFont(FONT);
		// Onto the error label; in case their input is invalid
		errorText6 = new JLabel("[3,..9, A, K, Q, J]");
		errorText6.setFont(new Font("Dialog", Font.ITALIC, 15));
		errorText6.setForeground(choosePanel6.getBackground());
		//--- Add ALL to choosePanel1 ---//
		con.gridx = 0;
		choosePanel6.add(box6, con);
		con.gridx = 1;
		choosePanel6.add(field6, con);
		con.gridx = 2;
		choosePanel6.add(askButton6, con);
		askButton6.addActionListener(this);
		con.gridx = 1;
		con.gridy = 2;
		choosePanel6.add(errorText6, con);
		// add to window
		gameWindow.add(choosePanel6);
		// Hide visibility
		choosePanel6.setVisible(false);
		
		// ONTO LABELING PLAYER
		JTextArea p6 = new JTextArea("P3");
		p6.setFont(new Font("Dialog", Font.BOLD, 75));
		p6.setEditable(false);
		p6.setBackground(BACKCOLOR);
		p6.setForeground(TEAM_2_COLOR);
		p6.setBounds(WIDTH - 100, panel6.getY() + panel6.getHeight(), 100, 100);
		// Add to game window
		gameWindow.add(p6);
		/*******************************/
		
		/*~~~~~KEEP TRACK OF SCORES~~~~*/
		scorePanel = new JPanel();
		scorePanel.setLayout(new GridBagLayout());
		//scorePanel.setBounds(panel1.getWidth(), panel1.getHeight(), panel4.getX() - panel1.getWidth(), panel3.getY() - panel3.getHeight() );
		scorePanel.setBounds(panel1.getWidth(), 0, panel4.getX() - panel1.getWidth(), HEIGHT - 30);
		scorePanel.setBorder(BorderFactory.createLineBorder(new Color(110, 42, 42), 13));
		scorePanel.setBackground(BACKCOLOR);
//		System.out.println("\nScore Panel	x:"+scorePanel.getX()+"	y:" +scorePanel.getY()+ "	width:"+scorePanel.getWidth()+"	height:"+scorePanel.getHeight());
		GridBagConstraints grid = new GridBagConstraints();
		// Set up the scoring
		score1 = new JTextArea();
		score1.setText("00");
		score1.setFont(new Font("Dialog", Font.BOLD, 100));
		score1.setEditable(false);
		score1.setBackground(TEAM_1_COLOR);
		score1.setForeground(Color.white);
		score2 = new JTextArea();
		score2.setText("00");
		score2.setFont(new Font("Dialog", Font.BOLD, 100));
		score2.setEditable(false);
		score2.setBackground(TEAM_2_COLOR);
		score2.setForeground(Color.white);
		// Add to score panel
		//grid.anchor = GridBagConstraints.SOUTHEAST;
		grid.gridx = 0;
		grid.gridy = 1;
		scorePanel.add(score1, grid);
		//grid.anchor = GridBagConstraints.SOUTHWEST;
		grid.gridx = 1;
		grid.gridy = 1;
		scorePanel.add(score2, grid);
		// Add to game window
		gameWindow.add(scorePanel);
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
		
		flipButton = new JButton("FLIP");
		flipButton.setBounds(scorePanel.getX() + score1.getX(), scorePanel.getY() - scorePanel.getHeight(), 100, 25);
		flipButton.setFont(new Font("Dialog", Font.BOLD, 20));
		flipButton.setForeground(Color.WHITE);
		flipButton.setBackground(BROWN);
		flipButton.addActionListener(this);
		// Add to score panel
		grid.gridx = 0;
		grid.gridy = 0;
		grid.ipady = 40;
		grid.gridwidth = 2;
		scorePanel.add(flipButton, grid);
		
		gameWindow.repaint();
		gameWindow.setVisible(true);
	}
	
	/**
	 * Updates visual representation of player's hand
	 * 
	 * Parameters:
	 *   p - the player
	 */
	public void updateVisualHand(Player p) {
		int team = p.team;
		int id = p.id;
		GridBagConstraints c = new GridBagConstraints();
		// Check length of player's hand
		if(p.hand.size() > 8 && p.hand.size() < 13) {
			c.insets = new Insets(0, -50, 0, 0);
		} else if (p.hand.size() >= 13 && p.hand.size() < 20) {
			c.insets = new Insets(0, -65, 0, 0);
		} else if(p.hand.size() >= 20) {
			c.insets = new Insets(0, -70, 0 , 0);
		}
		
		c.gridheight = 1;
		c.gridwidth  = 4;
		
		if(team == 1) {
			if(id == 1) {
				panel1.removeAll();
				for(int card = 0; card < team1[id-1].hand.size(); card++) {
					ImageIcon temp;
					if(this.flip == 1) {
						temp = new ImageIcon("./Cards/"+p.hand.get(card)+".png");
					} else {
						this.flip = 0;
						temp = new ImageIcon("./Cards/Cover.png");
					}
					// Re-size temp
					temp = new ImageIcon(temp.getImage().getScaledInstance(temp.getIconWidth() + 12, temp.getIconHeight() + 16, Image.SCALE_SMOOTH));
					panel1.add(new JLabel(temp), c);
				}
				panel1.repaint();
				panel1.setVisible(true);
				panel1.validate();
			} else if (id == 2) {
				panel2.removeAll();
				for(int card = 0; card < team1[id-1].hand.size(); card++) {
					ImageIcon temp;
					if(this.flip == 1) {
						temp = new ImageIcon("./Cards/"+p.hand.get(card)+".png");
					} else {
						this.flip = 0;
						temp = new ImageIcon("./Cards/Cover.png");
					}
					// Re-size temp
					temp = new ImageIcon(temp.getImage().getScaledInstance(temp.getIconWidth() + 12, temp.getIconHeight() + 16, Image.SCALE_SMOOTH));
					panel2.add(new JLabel(temp), c);
				}
				panel2.repaint();
				panel2.setVisible(true);
				panel2.validate();
			} else if(id == 3) {
				panel3.removeAll();
				for(int card = 0; card < team1[id-1].hand.size(); card++) {
					ImageIcon temp;
					if(this.flip == 1) {
						temp = new ImageIcon("./Cards/"+p.hand.get(card)+".png");
					} else {
						this.flip = 0;
						temp = new ImageIcon("./Cards/Cover.png");
					}
					// Re-size temp
					temp = new ImageIcon(temp.getImage().getScaledInstance(temp.getIconWidth() + 12, temp.getIconHeight() + 16, Image.SCALE_SMOOTH));
					panel3.add(new JLabel(temp), c);
				}
				panel3.repaint();
				panel3.setVisible(true);
				panel3.validate();
			}
		} else if (team == 2) {
			if(id == 1) {
				panel4.removeAll();
				for(int card = 0; card < team2[id-1].hand.size(); card++) {
					ImageIcon temp;
					if(this.flip == 1) {
						temp = new ImageIcon("./Cards/"+p.hand.get(card)+".png");
					} else {
						this.flip = 0;
						temp = new ImageIcon("./Cards/Cover.png");
					}
					// Re-size temp
					temp = new ImageIcon(temp.getImage().getScaledInstance(temp.getIconWidth() + 12, temp.getIconHeight() + 16, Image.SCALE_SMOOTH));
					panel4.add(new JLabel(temp), c);
				}
				panel4.repaint();
				panel4.setVisible(true);
				panel4.validate();
			} else if (id == 2) {
				panel5.removeAll();
				for(int card = 0; card < team2[id-1].hand.size(); card++) {
					ImageIcon temp;
					if(this.flip == 1) {
						temp = new ImageIcon("./Cards/"+p.hand.get(card)+".png");
					} else {
						this.flip = 0;
						temp = new ImageIcon("./Cards/Cover.png");
					}
					// Re-size temp
					temp = new ImageIcon(temp.getImage().getScaledInstance(temp.getIconWidth() + 12, temp.getIconHeight() + 16, Image.SCALE_SMOOTH));
					panel5.add(new JLabel(temp), c);
				}
				panel5.repaint();
				panel5.setVisible(true);
				panel5.validate();
			} else if(id == 3) {
				panel6.removeAll();
				for(int card = 0; card < team2[id-1].hand.size(); card++) {
					ImageIcon temp;
					if(this.flip == 1) {
						temp = new ImageIcon("./Cards/"+p.hand.get(card)+".png");
					} else {
						this.flip = 0;
						temp = new ImageIcon("./Cards/Cover.png");
					}
					// Re-size temp
					temp = new ImageIcon(temp.getImage().getScaledInstance(temp.getIconWidth() + 12, temp.getIconHeight() + 16, Image.SCALE_SMOOTH));
					panel6.add(new JLabel(temp), c);
				}
				panel6.repaint();
				panel6.setVisible(true);
				panel6.validate();
			}
		}
		gameWindow.repaint();
	}
	
	/**
	 * Updates visual representation of a team's points
	 * 
	 * Parameters:
	 *   teamNum - the team number
	 *   points - points to be added to current points
	 */
	public void updateVisualPoints(int teamNum) {
		if(teamNum == 1) {
			if(points1 >= 10) {
				score1.setText(Integer.toString(points1));
			} else {
				score1.setText("0" + Integer.toString(points1));
			}
		} else if (teamNum == 2) { //Just in case...
			if(points2 >= 10) {
				score2.setText(Integer.toString(points2));
			} else {
				score2.setText("0" + Integer.toString(points2));
			}
		}

		gameWindow.repaint();
	}
	
	/**
	 * Helper method to update visuals...HIGHER-COMPLETION CHECK
	 */
	public void upperHelper(int team, int index) {
		if(team == 1) {
			char suit = team1[index].checkHigherSuit();
			if(suit != '\0') {
				// Has already deleted... update visual
				updateVisualHand(team1[index]);
				// Award +2 point to team
				points1 += 2;
				updateVisualPoints(1);
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Team "+team+": Player " + team1[index].id + " does not have any high-completion of any suit.");
			}
		} else if(team == 2) {
			char suit = team2[index].checkHigherSuit();
			if(suit != '\0') {
				// Has already deleted... update visual
				updateVisualHand(team2[index]);
				// Award +2 point to team
				points2 += 2;
				updateVisualPoints(2);
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Team "+team+": Player " + team1[index].id + " does not have any high-completion of any suit.");
			}
		}
		endGame();
	}
	
	/**
	 * Helper method to update visuals...LOWER-COMPLETION CHECK
	 */
	public void lowerHelper(int team, int index) {
		if(team == 1) {
			char suit = team1[index].checkLowerSuit();
			if(suit != '\0') {
				// Has already deleted... update visual
				updateVisualHand(team1[index]);
				// Award +1 point to team
				points1++;
				updateVisualPoints(1);
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Team "+team+": Player " + team1[index].id + " does not have any low-completion of any suit.");
			}
		} else if(team == 2) {
			char suit = team2[index].checkLowerSuit();
			if(suit != '\0') {
				// Has already deleted... update visual
				updateVisualHand(team2[index]);
				// Award +1 point to team
				points2++;
				updateVisualPoints(2);
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Team "+team+": Player " + team1[index].id + " does not have any low-completion of any suit.");
			}
		}
		endGame();
	}
	
	public void endGame() {
		for(Player p : team1) {
			if(!p.hand.isEmpty()) {
				return;
			}
		}
		for(Player p : team2) {
			if(!p.hand.isEmpty()) {
				return;
			}
		}
		// Empty hands at this point. Terminate.
		// Which team was the winner?
		if(points1 < points2) {
			JOptionPane.showMessageDialog(new JFrame(), "Team 2 WINS!");
		} else if(points1 > points2) {
			JOptionPane.showMessageDialog(new JFrame(), "Team 1 WINS!");
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "DRAW!");
		}
		// Prompt for new game.
		JFrame frame = new JFrame();
		Object[] options = {"OK", "CANCEL"};
		int o = JOptionPane.showOptionDialog(frame, "New Game?",
				"", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE,null,
				 options, options[0]);
		System.out.println(o);
		if(o == 0) { // OK option
			gameWindow.dispose();
			new Game();
		} else {
			frame.dispose();
		}
	}
	
	/**
	 * Given an alphabetical form of a number, gives back its number form
	 * Example: A -> 14, K -> 13, etc.
	 */
	private String alphToNum(String num) {
		switch(num.toUpperCase()) {
		case "J": return "11";
		case "Q": return "12";
		case "K": return "13";
		case "A": return "14";
		}
		// Check to see if num is beyond 3,..10
		if(Integer.parseInt(num) < 3 || Integer.parseInt(num) > 10) {
			return null;
		}
		return num;
	}
	
	private char suitToChar(String suit) {
		switch(suit) {
		case "Diamonds": return 'D';
		case "Clubs": return 'C';
		case "Hearts": return 'H';
		case "Spades": return 'S';
		}
		
		return '\0';
	}
	
	/**
	 * Manages buttons
	 * 
	 * Parameters:
	 *   e - An action event (button being pressed)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(upperButton1)) {
			upperHelper(1, 0);
		} else if(e.getSource().equals(upperButton2)) {
			upperHelper(1, 1);
		} else if(e.getSource().equals(upperButton3)) {
			upperHelper(1, 2);
		} else if(e.getSource().equals(upperButton4)) {
			upperHelper(2, 0);
		} else if(e.getSource().equals(upperButton5)) {
			upperHelper(2, 1);
		} else if(e.getSource().equals(upperButton6)) {
			upperHelper(2, 2);
		} else if(e.getSource().equals(lowerButton1)) {
			lowerHelper(1, 0);
		} else if(e.getSource().equals(lowerButton2)) {
			lowerHelper(1, 1);
		} else if(e.getSource().equals(lowerButton3)) {
			lowerHelper(1, 2);
		} else if(e.getSource().equals(lowerButton4)) {
			lowerHelper(2, 0);
		} else if(e.getSource().equals(lowerButton5)) {
			lowerHelper(2, 1);
		} else if(e.getSource().equals(lowerButton6)) {
			lowerHelper(2, 2);
		} else if(e.getSource().equals(askButton1)) {
			String num = alphToNum( field1.getText() );
			if( null == num ) {
				errorText1.setForeground(TEAM_1_COLOR);
				return;
			}
			// Reset error text
			errorText1.setForeground(TEAM_2_COLOR);
			String card = num + suitToChar( (String)box1.getSelectedItem() );
			if(currentPlayer.askPlayer(team1[0], card)) {
				// True...card will already have been removed -> Update visuals
				updateVisualHand(team1[0]);
				updateVisualHand(currentPlayer);
				gameWindow.repaint();
			} else {
				card = field1.getText().toUpperCase() + suitToChar( (String)box1.getSelectedItem() );
				JOptionPane.showMessageDialog(new JFrame(), "Opponent Player "+team1[0].id+" does not have the card ["+card+"].");
				// Opponent's turn
				turnOff(currentPlayer);
				gameWindow.repaint();
				currentPlayer = team1[0];
			}
			// Current player's turn
			whosTurn(currentPlayer);
		} else if(e.getSource().equals(askButton2)) {
			String num = alphToNum( field2.getText() );
			if(null == num) {
				errorText2.setForeground(TEAM_1_COLOR);
				return;
			}
			// Reset error text
			errorText2.setForeground(TEAM_2_COLOR);
			String card = num + suitToChar( (String)box2.getSelectedItem() );
			if(currentPlayer.askPlayer(team1[1], card)) {
				// True...card will already have been removed -> Update visuals
				updateVisualHand(team1[1]);
				updateVisualHand(currentPlayer);
				gameWindow.repaint();
			} else {
				card = field2.getText().toUpperCase() + suitToChar( (String)box2.getSelectedItem() );
				JOptionPane.showMessageDialog(new JFrame(), "Opponent Player "+team1[1].id+" does not have the card ["+card+"].");
				// Opponent's turn
				turnOff(currentPlayer);
				gameWindow.repaint();
				currentPlayer = team1[1];
			}
			// Current player's turn
			whosTurn(currentPlayer);
		} else if(e.getSource().equals(askButton3)) {
			String num = alphToNum( field3.getText() );
			if(null == num) {
				errorText3.setForeground(TEAM_1_COLOR);
				return;
			}
			// Reset error text
			errorText3.setForeground(TEAM_2_COLOR);
			String card = num + suitToChar( (String)box3.getSelectedItem() );
			if(currentPlayer.askPlayer(team1[2], card)) {
				// True...card will already have been removed -> Update visuals
				updateVisualHand(team1[2]);
				updateVisualHand(currentPlayer);
				gameWindow.repaint();
			} else {
				card = field3.getText().toUpperCase() + suitToChar( (String)box3.getSelectedItem() );
				JOptionPane.showMessageDialog(new JFrame(), "Opponent Player "+team1[2].id+" does not have the card ["+card+"].");
				// Opponent's turn
				turnOff(currentPlayer);
				gameWindow.repaint();
				currentPlayer = team1[2];
			}
			// Current player's turn
			whosTurn(currentPlayer);
		} else if(e.getSource().equals(askButton4)) {
			String num = alphToNum( field4.getText() );
			if(null == num) {
				errorText4.setForeground(TEAM_2_COLOR);
				return;
			}
			// Reset error text
			errorText4.setForeground(TEAM_1_COLOR);
			String card = num + suitToChar( (String)box4.getSelectedItem() );
			if(currentPlayer.askPlayer(team2[0], card)) {
				// True...card will already have been removed -> Update visuals
				updateVisualHand(team2[0]);
				updateVisualHand(currentPlayer);
				gameWindow.repaint();
			} else {
				card = field4.getText().toUpperCase() + suitToChar( (String)box4.getSelectedItem() );
				JOptionPane.showMessageDialog(new JFrame(), "Opponent Player "+team2[0].id+" does not have the card ["+card+"].");
				// Opponent's turn
				turnOff(currentPlayer);
				gameWindow.repaint();
				currentPlayer = team2[0];
			}
			// Current player's turn
			whosTurn(currentPlayer);
		} else if(e.getSource().equals(askButton5)) {
			String num = alphToNum( field5.getText() );
			if(null == num) {
				errorText5.setForeground(TEAM_2_COLOR);
				return;
			}
			// Reset error text
			errorText5.setForeground(TEAM_1_COLOR);
			String card = num + suitToChar( (String)box5.getSelectedItem() );
			if(currentPlayer.askPlayer(team2[1], card)) {
				// True...card will already have been removed -> Update visuals
				updateVisualHand(team2[1]);
				updateVisualHand(currentPlayer);
				gameWindow.repaint();
			} else {
				card = field5.getText().toUpperCase() + suitToChar( (String)box5.getSelectedItem() );
				JOptionPane.showMessageDialog(new JFrame(), "Opponent Player "+team2[1].id+" does not have the card ["+card+"].");
				// Opponent's turn
				turnOff(currentPlayer);
				gameWindow.repaint();
				currentPlayer = team2[1];
			}
			// Current player's turn
			whosTurn(currentPlayer);
		} else if(e.getSource().equals(askButton6)) {
			String num = alphToNum( field6.getText() );
			if(null == num) {
				errorText6.setForeground(TEAM_2_COLOR);
				return;
			}
			// Reset error text
			errorText6.setForeground(TEAM_1_COLOR);
			String card = num + suitToChar( (String)box6.getSelectedItem() );
			if(currentPlayer.askPlayer(team2[2], card)) {
				// True...card will already have been removed -> Update visuals
				updateVisualHand(team2[2]);
				updateVisualHand(currentPlayer);
				gameWindow.repaint();
			} else {
				card = field6.getText().toUpperCase() + suitToChar( (String)box6.getSelectedItem() );
				JOptionPane.showMessageDialog(new JFrame(), "Opponent Player "+team2[2].id+" does not have the card ["+card+"].");
				// Opponent's turn
				turnOff(currentPlayer);
				gameWindow.repaint();
				currentPlayer = team2[2];
			}
			// Current player's turn
			whosTurn(currentPlayer);
		} else if(e.getSource().equals(flipButton)) {
			this.flip++;
			if(this.flip == 1) {
				flipButton.setBackground(Color.WHITE);
				flipButton.setForeground(Color.BLACK);
			} else {
				//this.flip--;
				flipButton.setBackground(BROWN);
				flipButton.setForeground(Color.WHITE);
			}
			helpFlip();
		}
	}
	
	private void helpFlip() {
		if(currentPlayer.team == 1) {
			for(Player p : team1) {
				if(p.id != currentPlayer.id) {
					updateVisualHand(p);
				}
			}
			
			for(Player p : team2) {
				updateVisualHand(p);
			}
		} else {
			for(Player p : team1) {
				updateVisualHand(p);
			}
			
			for(Player p : team2) {
				if(p.id != currentPlayer.id) {
					updateVisualHand(p);
				}
			}
		}
	}
	
	private void resetBox(Player p) {
		int team = p.team;
		int id = p.id;
		
		if(team == 1) {
			switch(id) {
			case 1: box1.setSelectedIndex(0); field1.setText(""); break;
			case 2: box2.setSelectedIndex(0); field2.setText(""); break;
			case 3: box3.setSelectedIndex(0); field3.setText(""); break;
			}
		} else if(team == 2) {
			switch(id) {
			case 1: box4.setSelectedIndex(0); field4.setText(""); break;
			case 2: box5.setSelectedIndex(0); field5.setText(""); break;
			case 3: box6.setSelectedIndex(0); field6.setText(""); break;
			}
		}
	}
	
	/**
	 * Turns off background color of player (not their turn anymore)
	 */
	private void turnOff(Player p) {
		int team = p.team;
		int id = p.id;
		
		if(team == 1) {
			switch(id) {
			case 1: panel1.setBackground(BACKCOLOR); panel1.repaint(); upperButton1.setVisible(false); lowerButton1.setVisible(false); break;
			case 2: panel2.setBackground(BACKCOLOR); panel2.repaint(); upperButton2.setVisible(false); lowerButton2.setVisible(false); break;
			case 3: panel3.setBackground(BACKCOLOR); panel3.repaint(); upperButton3.setVisible(false); lowerButton3.setVisible(false); break;
			}
		} else if (team == 2) { // Just in case test case...
			switch(id) {
			case 1: panel4.setBackground(BACKCOLOR); panel4.repaint(); upperButton4.setVisible(false); lowerButton4.setVisible(false); break;
			case 2: panel5.setBackground(BACKCOLOR); panel5.repaint(); upperButton5.setVisible(false); lowerButton5.setVisible(false); break;
			case 3: panel6.setBackground(BACKCOLOR); panel6.repaint(); upperButton6.setVisible(false); lowerButton6.setVisible(false); break;
			}
		}
		// Reset boxes of opponents
		if(team == 1) {
			// Reset team 2 boxes
			for(int i = 0; i < team2.length; i++) {
				resetBox(team2[i]);
			}
		} else if(team == 2) {
			// Reset team 1 boxes
			for(int i = 0; i < team1.length; i++) {
				resetBox(team1[i]);
			}
		}
		
		// Repaint game window
		gameWindow.repaint();
	}
	
	public void whosTurn(Player p) {
		int team = p.team;
		int id = p.id;
		GridBagConstraints c = new GridBagConstraints();
		// Check length of player's hand
		if(p.hand.size() > 8 && p.hand.size() < 13) {
			c.insets = new Insets(0, -50, 0, 0);
		} else if (p.hand.size() >= 13 && p.hand.size() < 20) {
			c.insets = new Insets(0, -65, 0, 0);
		} else if(p.hand.size() >= 20) {
			c.insets = new Insets(0, -70, 0 , 0);
		}
		
		c.gridheight = 1;
		c.gridwidth  = 4;
		
		if(team == 1) {
			// Hide all current team choices
			choosePanel1.setVisible(false);
			choosePanel2.setVisible(false);
			choosePanel3.setVisible(false);
			switch(id) {
			case 1: 
				panel1.removeAll();
				for(int card = 0; card < team1[id-1].hand.size(); card++) {
					ImageIcon temp;
					temp = new ImageIcon("./Cards/"+p.hand.get(card)+".png");
					// Re-size temp
					temp = new ImageIcon(temp.getImage().getScaledInstance(temp.getIconWidth() + 12, temp.getIconHeight() + 16, Image.SCALE_SMOOTH));
					panel1.add(new JLabel(temp), c);
				}
				panel1.setBackground(TEAM_1_COLOR); panel1.repaint(); upperButton1.setVisible(true); lowerButton1.setVisible(true); 
				panel1.repaint();
				panel1.setVisible(true);
				panel1.validate();
				break;
			case 2: 
				panel2.removeAll();
				for(int card = 0; card < team1[id-1].hand.size(); card++) {
					ImageIcon temp;
					temp = new ImageIcon("./Cards/"+p.hand.get(card)+".png");
					// Re-size temp
					temp = new ImageIcon(temp.getImage().getScaledInstance(temp.getIconWidth() + 12, temp.getIconHeight() + 16, Image.SCALE_SMOOTH));
					panel2.add(new JLabel(temp), c);
				}
				panel2.setBackground(TEAM_1_COLOR); panel2.repaint(); upperButton2.setVisible(true); lowerButton2.setVisible(true);
				panel2.repaint();
				panel2.setVisible(true);
				panel2.validate();
				break;
			case 3: 
				panel3.removeAll();
				for(int card = 0; card < team1[id-1].hand.size(); card++) {
					ImageIcon temp;
					temp = new ImageIcon("./Cards/"+p.hand.get(card)+".png");
					// Re-size temp
					temp = new ImageIcon(temp.getImage().getScaledInstance(temp.getIconWidth() + 12, temp.getIconHeight() + 16, Image.SCALE_SMOOTH));
					panel3.add(new JLabel(temp), c);
				}
				panel3.setBackground(TEAM_1_COLOR); panel3.repaint(); upperButton3.setVisible(true); lowerButton3.setVisible(true);
				panel3.repaint();
				panel3.setVisible(true);
				panel3.validate();
				break;
			}
			// Set all opponent choices visible
			choosePanel4.setVisible(true);
			choosePanel5.setVisible(true);
			choosePanel6.setVisible(true);
		} else if (team == 2) { // Just in case test case...
			// Hide all current team choices
			choosePanel4.setVisible(false);
			choosePanel5.setVisible(false);
			choosePanel6.setVisible(false);
			switch(id) {
			case 1:
				panel4.removeAll();
				for(int card = 0; card < team2[id-1].hand.size(); card++) {
					ImageIcon temp;
					temp = new ImageIcon("./Cards/"+p.hand.get(card)+".png");
					// Re-size temp
					temp = new ImageIcon(temp.getImage().getScaledInstance(temp.getIconWidth() + 12, temp.getIconHeight() + 16, Image.SCALE_SMOOTH));
					panel4.add(new JLabel(temp), c);
				}
				panel4.setBackground(TEAM_2_COLOR); panel4.repaint(); upperButton4.setVisible(true); lowerButton4.setVisible(true);
				panel4.repaint();
				panel4.setVisible(true);
				panel4.validate();
				break;
			case 2: 
				panel5.removeAll();
				for(int card = 0; card < team2[id-1].hand.size(); card++) {
					ImageIcon temp;
					temp = new ImageIcon("./Cards/"+p.hand.get(card)+".png");
					// Re-size temp
					temp = new ImageIcon(temp.getImage().getScaledInstance(temp.getIconWidth() + 12, temp.getIconHeight() + 16, Image.SCALE_SMOOTH));
					panel5.add(new JLabel(temp), c);
				}
				panel5.setBackground(TEAM_2_COLOR); panel5.repaint(); upperButton5.setVisible(true); lowerButton5.setVisible(true);
				panel5.repaint();
				panel5.setVisible(true);
				panel5.validate();
				break;
			case 3:
				panel6.removeAll();
				for(int card = 0; card < team2[id-1].hand.size(); card++) {
					ImageIcon temp;
					temp = new ImageIcon("./Cards/"+p.hand.get(card)+".png");
					// Re-size temp
					temp = new ImageIcon(temp.getImage().getScaledInstance(temp.getIconWidth() + 12, temp.getIconHeight() + 16, Image.SCALE_SMOOTH));
					panel6.add(new JLabel(temp), c);
				}
				panel6.setBackground(TEAM_2_COLOR); panel6.repaint(); upperButton6.setVisible(true); lowerButton6.setVisible(true);
				panel6.repaint();
				panel6.setVisible(true);
				panel6.validate();
				break;
			}
			// Set all opponent choices visible
			choosePanel1.setVisible(true);
			choosePanel2.setVisible(true);
			choosePanel3.setVisible(true);
		}
		
		gameWindow.repaint();
	}
	
	public void startGame() {
		currentPlayer = team1[0];
		// Play
		whosTurn(currentPlayer);
	}
}