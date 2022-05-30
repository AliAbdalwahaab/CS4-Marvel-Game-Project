package views;

import controller.GameController;
import engine.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.colorchooser.ColorChooserComponentFactory;

import engine.Player;

public class GameVIEW extends JFrame{
    private JPanel gameBoard;
//    private JPanel Abilities;
//    private JButton CCAbility;
//    private JButton DAbility;
//    private JButton HAbility;
//    private JButton LAbility;

    private JPanel CurrentChampInfo;
    private JLabel CurrentPlayerName;
    private JLabel ChampName;
    private JLabel ChampType;
    private JLabel ChampHP;
    private JLabel ChampMana;
    private JLabel ChampActionPoints;
    private JLabel ChampAttackDmg;
    private JComboBox ChampAbilities;
    private JComboBox ChampAppliedEffects;
    private JButton endTurn;

    private JPanel HoverChampInfo;
    private JLabel HoverChampName;
    private JLabel HoverChampType;
    private JLabel HoverChampHP;
    private JLabel HoverChampMana;
    private JLabel HoverChampActionPoints;
    private JLabel HoverChampAttackDmg;
    private JComboBox HoverChampAbilities;
    private JComboBox HoverChampAppliedEffects;

    private JPanel abilitiesPanel;
    private JPanel rightPanel;

    public GameVIEW(GameController controller){
        setTitle("GAME STARTED!");
        addMouseListener(controller);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0, 0, 1920, 1080);

        //Board (CENTER)
        gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(5, 5));
        add(gameBoard, BorderLayout.CENTER);
//        for (int i = 0; i < 25; i++) {
//            JButton b = new JButton();
//            gameBoard.add(b);
//        }

        Object[][] Board = controller.getBoard();
        for (int row = 4; row >= 0; row--) {
            for (int col = 0; col < 5; col++) {
                gameBoard.add(new JButton((Board[row][col] == null) ? "Empty" : Board[row][col].toString()));
            }
        }
        this.revalidate();
        this.repaint();
        //for (Object o: )

        // Abilities left (2 grids)
        abilitiesPanel = new JPanel(new GridLayout(2,1));
        JLabel empty = new JLabel();
        empty.setPreferredSize(new Dimension(200,this.getHeight()));
        abilitiesPanel.add(empty);
        this.add(abilitiesPanel, BorderLayout.WEST);
        this.revalidate();
        this.repaint();


        // TurnOrder + Movement &  Attack right (2 grids)
        rightPanel = new JPanel(new GridLayout(2,1));
        JLabel empty2 = new JLabel();
        empty2.setPreferredSize(new Dimension(200,this.getHeight()));
        rightPanel.add(empty2);
        this.add(rightPanel, BorderLayout.EAST);

        // Hover Info (Page Start)
        HoverChampInfo =  new JPanel(new GridLayout(2, 4));
        HoverChampInfo.setBackground(Color.decode("#e8f743") );
        HoverChampName = new JLabel("Karinge");
        HoverChampType = new JLabel("Karinge");
        HoverChampHP = new JLabel("Karinge");
        HoverChampMana = new JLabel("Karinge");
        HoverChampActionPoints = new JLabel("Karinge");
        HoverChampAttackDmg = new JLabel("Karinge");
        HoverChampAbilities = new JComboBox();
        HoverChampAppliedEffects = new JComboBox();
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
        CurrentChampInfo =  new JPanel(new GridLayout(2, 5));
        CurrentChampInfo.setBackground(Color.decode("#4d8de8") );
        CurrentPlayerName = new JLabel("Karinge");
        ChampName = new JLabel("Karinge");
        ChampType = new JLabel("Karinge");
        ChampHP = new JLabel("Karinge");
        ChampMana = new JLabel("Karinge");
        ChampActionPoints = new JLabel("Karinge");
        ChampAttackDmg = new JLabel("Karinge");
        ChampAbilities = new JComboBox();
        ChampAppliedEffects = new JComboBox();
        endTurn = new JButton("END THIS SHIT");
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





//        Abilities = new JPanel();
//        Abilities.setLayout(new GridLayout(4,0));
//        CCAbility = new JButton("Crowd control");
//        DAbility = new JButton("Damage");
//        HAbility = new JButton("Healing");
//        LAbility = new JButton("Leader");
//        Abilities.add(CCAbility);
//        Abilities.add(DAbility);
//        Abilities.add(HAbility);
//        Abilities.add(LAbility);
//        add(Abilities,BorderLayout.EAST);

        this.revalidate();
        this.repaint();
    }

}