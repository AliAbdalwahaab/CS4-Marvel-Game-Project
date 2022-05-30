package views;

import controller.GameController;
import engine.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.colorchooser.ColorChooserComponentFactory;

import engine.Player;

public class GameVIEW extends JFrame{
    private JPanel GameBoard;
    private JPanel Abilities;
    private JButton CCAbility;
    private JButton DAbility;
    private JButton HAbility;
    private JButton LAbility;

    public GameVIEW(GameController controller){
        setTitle("GAME STARTED!");
        addMouseListener(controller);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0, 0, 1920, 1080);


        //Board (CENTER)

        // Abilities left (2 grids)

        // TurnOrder + Movement &  Attack right (2 grids)

        // Hover Info (Page Start)
        JPanel HoverChampInfo =  new JPanel(new GridLayout(2, 4));
        HoverChampInfo.setBackground(Color.decode("#e8f743") );
        JLabel HoverChampName = new JLabel("Karinge");
        JLabel HoverChampType = new JLabel("Karinge");
        JLabel HoverChampHP = new JLabel("Karinge");
        JLabel HoverChampMana = new JLabel("Karinge");
        JLabel HoverChampActionPoints = new JLabel("Karinge");
        JLabel HoverChampAttackDmg = new JLabel("Karinge");
        JComboBox HoverChampAbilities = new JComboBox();
        JComboBox HoverChampAppliedEffects = new JComboBox();
        HoverChampInfo.add(HoverChampName);
        HoverChampInfo.add(HoverChampType);
        HoverChampInfo.add(HoverChampHP);
        HoverChampInfo.add(HoverChampMana);
        HoverChampInfo.add(HoverChampActionPoints);
        HoverChampInfo.add(HoverChampAttackDmg);
        HoverChampInfo.add(HoverChampAbilities);
        HoverChampInfo.add(HoverChampAppliedEffects);
        HoverChampInfo.setVisible(true);
        this.add(HoverChampInfo, BorderLayout.NORTH);

        // Current Champ info (Page End)
        JPanel CurrentChampInfo =  new JPanel(new GridLayout(2, 5));
        CurrentChampInfo.setBackground(Color.decode("#4d8de8") );
        JLabel CurrentPlayerName = new JLabel("Karinge");
        JLabel ChampName = new JLabel("Karinge");
        JLabel ChampType = new JLabel("Karinge");
        JLabel ChampHP = new JLabel("Karinge");
        JLabel ChampMana = new JLabel("Karinge");
        JLabel ChampActionPoints = new JLabel("Karinge");
        JLabel ChampAttackDmg = new JLabel("Karinge");
        JComboBox ChampAbilities = new JComboBox();
        JComboBox ChampAppliedEffects = new JComboBox();
        JButton endTurn = new JButton("END THIS SHIT");
        CurrentChampInfo.add(CurrentPlayerName);
        CurrentChampInfo.add(ChampName);
        CurrentChampInfo.add(ChampType);
        CurrentChampInfo.add(ChampHP);
        CurrentChampInfo.add(ChampMana);
        CurrentChampInfo.add(ChampActionPoints);
        CurrentChampInfo.add(ChampAttackDmg);
        CurrentChampInfo.add(ChampAbilities);
        CurrentChampInfo.add(ChampAppliedEffects);
        CurrentChampInfo.add(endTurn);
        CurrentChampInfo.setVisible(true);
        this.add(CurrentChampInfo, BorderLayout.SOUTH);


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

        this.revalidate();
        this.repaint();
    }

}