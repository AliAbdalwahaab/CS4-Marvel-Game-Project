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
        setBounds(50, 50, 1920, 1080);
        GameBoard = new JPanel();
        GameBoard.setLayout(new GridLayout(5, 5));
        add(GameBoard, BorderLayout.CENTER);
        Abilities = new JPanel();
        Abilities.setLayout(new GridLayout(4,0));
        Abilities.add(CCAbility, 1);
        Abilities.add(DAbility, 2);
        Abilities.add(HAbility, 3);
        Abilities.add(LAbility, 4);
        add(Abilities,BorderLayout.EAST);


    }

}