package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import engine.Game;
import engine.Player;
import model.world.Champion;

import static java.awt.Font.BOLD;

public class CharacterSelectionView extends JFrame implements ActionListener {
	
	private Game currentGame;

	private JTextField textField1;
	private JTextField textField2;
	private JButton submit;
	private JLabel text1;
	private JLabel text2;

	private JPanel centerPanel;
	private JPanel topPanel;
	private ArrayList<JButton> btns;
	
	private String firstPlayerName = "";
	private String secondPlayerName = "";
	
	public CharacterSelectionView() {
		this.setTitle("Game");
		this.setBounds(0,0,1920,1080);
		this.setLayout(null);

		text1 = new JLabel();
		text1.setText("Player 1 Name");
		text1.setFont(new Font("Times New Roman",BOLD,25));
		text1.setBounds(590,170,200,200);

		textField1 = new JTextField();
		textField1.setBounds(530,300,300,50);

		text2 = new JLabel();
		text2.setText("Player 2 Name");
		text2.setFont(new Font("Times New Roman",BOLD,25));
		text2.setBounds(590,280,200,200);

		textField2 = new JTextField();
		textField2.setBounds(530,300+100,300,50);

		submit = new JButton("Start");
		submit.setBounds(530+90,300+100+100,100,30);
		submit.addActionListener(this);

		this.add(text1);
		this.add(text2);
		this.add(textField1);
		this.add(textField2);
		this.add(submit);

		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new CharacterSelectionView();
	}
	
	public Game getcurrentGame() {
		return this.currentGame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			firstPlayerName = textField1.getText();
			secondPlayerName = textField2.getText();
			submit.setEnabled(false);

			Player player1 = new Player(firstPlayerName);
			Player player2 = new Player(secondPlayerName);
			currentGame = new Game(player1,player2);

			this.getContentPane().removeAll();
			this.repaint();

			updateTeams();

		}



		
	}

	public void updateTeams() {

		this.setLayout(new BorderLayout());
		this.setVisible(true);

		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(0,5));

		btns = new ArrayList<JButton>();

		try {
			currentGame.loadChampions("Champions.csv");
			currentGame.loadAbilities("Abilities.csv");
		} catch (Exception exception) {
			System.out.println("Error");
		}

		for (Champion c: currentGame.getAvailableChampions()) {
			JButton b = new JButton();
			b.setText(c.getName());
			b.addActionListener(this);
			centerPanel.add(b);
			btns.add(b);
		}

		this.add(centerPanel,BorderLayout.CENTER);
		centerPanel.setVisible(true);
		this.revalidate();
		this.repaint();


		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(0,2));
		topPanel.setPreferredSize(new Dimension(200,200));
		topPanel.setVisible(true);

		JTextArea player1Name = new JTextArea();
		player1Name.setText("  "+currentGame.getFirstPlayer().getName());
		player1Name.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));

		player1Name.setVisible(true);
		player1Name.setEditable(false);

		JTextArea player1Leader = new JTextArea();
		player1Leader.setText("   lol");
		player1Leader.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));
		player1Leader.setVisible(true);
		player1Leader.setEditable(false);

		JTextArea player1Champ1 = new JTextArea();
		player1Champ1.setText("    currentGame.getFirstPlayer().getTeam().get(1).getName()");
		player1Champ1.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));
		player1Champ1.setVisible(true);
		player1Champ1.setEditable(false);

		JTextArea player1Champ2 = new JTextArea();
		player1Champ2.setText("    currentGame.getFirstPlayer().getTeam().get(2).getName()");
		player1Champ2.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));
		player1Champ2.setVisible(true);
		player1Champ2.setEditable(false);

		JPanel player1Panel = new JPanel();
		player1Panel.setLayout(new GridLayout(4,0));
		player1Panel.setVisible(true);
		player1Panel.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),topPanel.getHeight()));

		player1Panel.add(player1Name);
		player1Panel.add(player1Leader);
		player1Panel.add(player1Champ1);
		player1Panel.add(player1Champ2);

		topPanel.add(player1Panel);

		this.revalidate();
		this.repaint();




		JPanel player2Panel = new JPanel();
		player2Panel.setLayout(new GridLayout(4,0));
		player2Panel.setVisible(true);

		player2Panel.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),topPanel.getHeight()));

		JTextArea player2Name = new JTextArea();
		player2Name.setText(currentGame.getSecondPlayer().getName());
		player2Name.setEditable(false);
		player2Name.setVisible(true);
		player2Name.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));


		JTextArea player2Leader = new JTextArea();
		player2Leader.setText("currentGame.getSecondPlayer().getLeader().getName()");
		player2Leader.setEditable(false);
		player2Leader.setVisible(true);
		player2Leader.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));


		JTextArea player2Champ1 = new JTextArea();
		player2Champ1.setText("currentGame.getSecondPlayer().getTeam().get(1).getName()");
		player2Champ1.setEditable(false);
		player2Champ1.setVisible(true);
		player2Champ1.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));

		JTextArea player2Champ2 = new JTextArea();
		player2Champ2.setText("currentGame.getSecondPlayer().getTeam().get(2).getName()");
		player2Champ2.setEditable(false);
		player2Champ2.setVisible(true);
		player2Champ2.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));

		player2Panel.add(player2Name);
		player2Panel.add(player2Leader);
		player2Panel.add(player2Champ1);
		player2Panel.add(player2Champ2);

		topPanel.add(player2Panel);

		this.revalidate();
		this.repaint();


		this.add(topPanel,BorderLayout.NORTH);
		this.revalidate();
		this.repaint();
	}

}
