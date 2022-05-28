package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import engine.Game;
import engine.Player;

public class CharacterSelectionGUI extends JFrame implements ActionListener {
	
	private Game newGame;
	
	
	private JButton submitName;
	private JTextField textField;
	
	private String firstPlayerName = "";
	private String secondPlayerName = "";
	
	public CharacterSelectionGUI() {
		this.setTitle("Gay Game");
		this.setBounds(0,0,1920,1080);
		this.setLayout(new FlowLayout());
		
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(100,50));
		
		JButton submitName = new JButton("Submit Name");
		submitName.setPreferredSize(new Dimension(300,20));
		submitName.addActionListener(this);
		
		this.add(textField);
		this.add(submitName);
		this.pack();
		
	
		this.setVisible(true);
		this.revalidate();
		this.repaint();
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
		if (e.getSource() == submitName) {
			System.out.println(textField.getText());
			
		}
		
	}

}
