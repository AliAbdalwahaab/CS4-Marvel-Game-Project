package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import controller.GameController;
import engine.Game;
import engine.Player;
import model.world.Champion;

import static java.awt.Font.BOLD;

public class CharacterSelectionView extends JFrame implements ActionListener {
	//TODO: Display champion info, change chosen champion fonts

	private Game currentGame;

	private JTextField textField1;
	private JTextField textField2;
	private JButton submit;
	private JLabel text1;
	private JLabel text2;

	private JPanel centerPanel;
	private ArrayList<JButton> btns;
	private JPanel topPanel;
	private JPanel player1Panel;
	private JPanel player2Panel;

	private JTextArea player1Leader;
	private JTextArea player1Champ1;
	private JTextArea player1Champ2;

	private JTextArea player2Leader;
	private JTextArea player2Champ1;
	private JTextArea player2Champ2;


	
	private String firstPlayerName = "";
	private String secondPlayerName = "";
	
	public CharacterSelectionView() {
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setTitle("Game");
		this.setBounds(0,0,1920,1080);
		this.setLayout(new BorderLayout());

		text1 = new JLabel();
		text1.setText("Player 1 Name");
		text1.setFont(new Font("Chiller",BOLD,35));
		text1.setBounds(590,170,200,200);

		textField1 = new JTextField();
		textField1.setBounds(530,300,300,50);

		text2 = new JLabel();
		text2.setText("Player 2 Name");
		text2.setFont(new Font("Chiller",BOLD,35));
		text2.setBounds(590,280,200,200);

		textField2 = new JTextField();
		textField2.setBounds(530,300+100,300,50);

		submit = new JButton("Start");
		submit.setFont(new Font("Chiller",BOLD,80));
		submit.setBounds(530+90,300+100+100,100,5);
		submit.addActionListener(this);


		JPanel empty = new JPanel();
		empty.setVisible(true);
		empty.setPreferredSize(new Dimension(200,this.getHeight()));
		this.add(empty,BorderLayout.EAST);

		JPanel empty2 = new JPanel();
		empty2.setVisible(true);
		empty2.setPreferredSize(new Dimension(200,this.getHeight()));
		this.add(empty2,BorderLayout.WEST);

		JPanel empty3 = new JPanel();
		empty3.setVisible(true);
		empty3.setPreferredSize(new Dimension(this.getWidth(),110));
		this.add(empty3,BorderLayout.NORTH);

		JLabel empty4 = new JLabel();
		empty4.setVisible(true);
		empty4.setPreferredSize(new Dimension(this.getWidth(),110));


		JPanel centeralign = new JPanel();
		centeralign.setLayout(new GridLayout(8,0));
		centeralign.setPreferredSize(new Dimension(200,300));
		this.add(centeralign, BorderLayout.CENTER);
		centeralign.add(text1);
		centeralign.add(textField1);
		centeralign.add(text2);
		centeralign.add(textField2);
		centeralign.add(empty4);
		centeralign.add(submit);

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
			player1.setColor("#4d8de8");
			player2.setColor("#de4335");
			currentGame = new Game(player1, player2);

			this.getContentPane().removeAll();
			this.repaint();

			updateTeams();
		}

		if (btns.contains(e.getSource())) {
			for (JButton b: btns) {
				if (e.getSource() == b && player1Leader.getText().equals(" Not yet selected")) {
					player1Leader.setText(b.getText()+" (Leader)");
					for (Champion c: currentGame.getAvailableChampions()) {
						if (c.getName().equals(b.getText())) {
							currentGame.getFirstPlayer().setLeader(c);
							currentGame.getFirstPlayer().getTeam().add(c);
							break;
						}
					}
					b.setEnabled(false);
				} else if (e.getSource() == b && player1Champ1.getText().equals(" Not yet selected")) {
					player1Champ1.setText(b.getText());
					for (Champion c: currentGame.getAvailableChampions()) {
						if (c.getName().equals(b.getText())) {
							currentGame.getFirstPlayer().getTeam().add(c);
							break;
						}
					}
					b.setEnabled(false);
				} else if (e.getSource() == b && player1Champ2.getText().equals(" Not yet selected")) {
					player1Champ2.setText(b.getText());
					for (Champion c: currentGame.getAvailableChampions()) {
						if (c.getName().equals(b.getText())) {
							currentGame.getFirstPlayer().getTeam().add(c);
							break;
						}
					}
					b.setEnabled(false);

					JOptionPane.showMessageDialog(null,"First Team Selected. " +
							"Second Player should choose all their Champions. The first champion selected is the leader");

				} else if (e.getSource() == b && player2Leader.getText().equals("Not yet selected")) {
					player2Leader.setText(b.getText()+" (Leader)");
					for (Champion c: currentGame.getAvailableChampions()) {
						if (c.getName().equals(b.getText())) {
							currentGame.getSecondPlayer().setLeader(c);
							currentGame.getSecondPlayer().getTeam().add(c);
							break;
						}
					}
					b.setEnabled(false);
				} else if (e.getSource() == b && player2Champ1.getText().equals("Not yet selected")) {
					player2Champ1.setText(b.getText());
					for (Champion c: currentGame.getAvailableChampions()) {
						if (c.getName().equals(b.getText())) {
							currentGame.getSecondPlayer().getTeam().add(c);
							break;
						}
					}
					b.setEnabled(false);
				} else if (e.getSource() == b && player2Champ2.getText().equals("Not yet selected")) {
					player2Champ2.setText(b.getText());
					for (Champion c: currentGame.getAvailableChampions()) {
						if (c.getName().equals(b.getText())) {
							currentGame.getSecondPlayer().getTeam().add(c);
							break;
						}
					}
					b.setEnabled(false);

					JOptionPane.showMessageDialog(null,"Second Team Selected. Game is about to start");
					this.dispose();

					currentGame.placeChampions();
					currentGame.prepareChampionTurns();

					new GameVIEW(new GameController(currentGame));
				}
			}
			this.revalidate();
			this.repaint();
		}



		
	}

	public void updateTeams() {

		this.setLayout(new BorderLayout());
		this.setVisible(true);

		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(0,5));

		btns = new ArrayList<JButton>();

		try {
			currentGame.loadAbilities("Abilities.csv");
			currentGame.loadChampions("Champions.csv");
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
		player1Name.setFont(new Font("Chiller",BOLD,40));
		player1Name.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));

		player1Name.setVisible(true);
		player1Name.setEditable(false);

		player1Leader = new JTextArea();
		player1Leader.setText(" Not yet selected");
		player1Leader.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));
		player1Leader.setVisible(true);
		player1Leader.setEditable(false);

		player1Champ1 = new JTextArea();
		player1Champ1.setText(" Not yet selected");
		player1Champ1.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));
		player1Champ1.setVisible(true);
		player1Champ1.setEditable(false);

		player1Champ2 = new JTextArea();
		player1Champ2.setText(" Not yet selected");
		player1Champ2.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));
		player1Champ2.setVisible(true);
		player1Champ2.setEditable(false);

		player1Panel = new JPanel();
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

		player2Panel = new JPanel();
		player2Panel.setLayout(new GridLayout(4,0));
		player2Panel.setVisible(true);

		player2Panel.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),topPanel.getHeight()));

		JTextArea player2Name = new JTextArea();
		player2Name.setFont(new Font("Chiller",BOLD,40));
		player2Name.setText(currentGame.getSecondPlayer().getName());
		player2Name.setEditable(false);
		player2Name.setVisible(true);
		player2Name.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));


		player2Leader = new JTextArea();
		player2Leader.setText("Not yet selected");
		player2Leader.setEditable(false);
		player2Leader.setVisible(true);
		player2Leader.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));


		player2Champ1 = new JTextArea();
		player2Champ1.setText("Not yet selected");
		player2Champ1.setEditable(false);
		player2Champ1.setVisible(true);
		player2Champ1.setPreferredSize(new Dimension((int) 0.5*topPanel.getWidth(),20));

		player2Champ2 = new JTextArea();
		player2Champ2.setText("Not yet selected");
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
		JOptionPane.showMessageDialog(null,"First Player should choose all " +
				"their Champions first, the first champion selected is the leader");

	}

}
