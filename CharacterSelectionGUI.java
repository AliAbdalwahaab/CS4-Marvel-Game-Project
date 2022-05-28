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

public class CharacterSelectionGUI extends JFrame implements ActionListener {
	
	private Game newGame;

	JTextField textField1;
	JTextField textField2;
	JButton submit;
	JLabel text1;
	JLabel text2;
	
	private String firstPlayerName = "";
	private String secondPlayerName = "";
	
	public CharacterSelectionGUI() {
		this.setTitle("Gay Game");
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
		new CharacterSelectionGUI();
	}
	
	public Game getNewGame() {
		return this.newGame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			firstPlayerName = textField1.getText();
			secondPlayerName = textField2.getText();
			submit.setEnabled(false);

			Player player1 = new Player(firstPlayerName);
			Player player2 = new Player(secondPlayerName);
			newGame = new Game(player1,player2);

			this.getContentPane().removeAll();
			this.repaint();

			//till here no problems

			this.setLayout(new BorderLayout());

			JPanel centerPanel = new JPanel();
			centerPanel.setLayout(new GridLayout(0,5));
			centerPanel.setVisible(true);

			ArrayList<JButton> btns = new ArrayList<JButton>();

			for (Champion c: newGame.getAvailableChampions()) {
				btns.add(new JButton(c.getName()));
			}

			for (JButton b: btns) {
				b.setPreferredSize(new Dimension(50,50));
				b.addActionListener(this);
				centerPanel.add(b);
			}
			
			this.add(centerPanel,BorderLayout.CENTER);
			this.revalidate();
			this.repaint();




		}

		
	}

}
