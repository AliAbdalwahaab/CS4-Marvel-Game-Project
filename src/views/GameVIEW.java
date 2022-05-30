package views;

import engine.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import engine.Player;

public class GameVIEW extends JFrame {
    private JPanel GameBoard;
    private JPanel Abilities;
    private JButton CCAbility;
    private JButton DAbility;
    private JButton HAbility;
    private JButton LAbility;

    public GameVIEW(){
        setTitle("GAME STARTED!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(0, 0, 1920, 1080);
        GameBoard = new JPanel();
        GameBoard.setLayout(new GridLayout(5, 5));
        add(GameBoard, BorderLayout.CENTER);
        Abilities = new JPanel();
        Abilities.setLayout(new GridLayout(4,0));
        CCAbility = new JButton("Crowd control");
        DAbility = new JButton("Damage");
        HAbility = new JButton("Healing");
        LAbility = new JButton("Leader");
        Abilities.add(CCAbility);
        Abilities.add(DAbility);
        Abilities.add(HAbility);
        Abilities.add(LAbility);
        add(Abilities,BorderLayout.EAST);
    }

}