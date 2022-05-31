package views;

import controller.GameController;
import engine.Game;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.colorchooser.ColorChooserComponentFactory;
import javax.swing.plaf.metal.MetalIconFactory;

import engine.Player;
import model.abilities.Ability;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Effect;
import model.abilities.*;
import model.world.Champion;
import model.world.Condition;
import model.world.Cover;

public class GameVIEW extends JFrame implements ActionListener, MouseListener {

    private GameController controller;
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

    private JPanel turnOrderPanel;

    private JPanel abilitiesPanel;
    private JPanel selectAbilities;
    private JComboBox selfTargetBox;
    private JComboBox teamTargetBox;
    private JComboBox singleTargetBox;
    private JComboBox directionalTargetBox;
    private JComboBox surroundTargetBox;
    private JPanel rightPanel;
    private JButton upDirection;
    private JButton leftDirection;
    private JButton rightDirection;
    private JButton downDirection;
    private JButton attack;
    private boolean castAbilityFlag = false;
    private boolean attackFlag = false;

    private Object[][] Board;
    private Object[][] buttonBoard;

    public GameVIEW(GameController controller) {

        this.controller = controller;
        setTitle("GAME STARTED!");
        addMouseListener(this);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0, 0, 1920, 1080);

        //Board (CENTER)
        gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(5, 5));
        add(gameBoard, BorderLayout.CENTER);
        Board = controller.getBoard();
        buttonBoard = new Object[5][5];
        for (int i = 0; i < 5; i++) { // Blind copy
            for (int j = 0; j < 5; j++) {
                JButton b = new JButton((Board[i][j] == null) ? "" : Board[i][j].toString());
                b.addActionListener(this);
                b.addMouseListener(this);
                buttonBoard[i][j] = b;
            }
        }

        for (int row = 4; row >= 0; row--) {
            for (int col = 0; col < 5; col++) {
                gameBoard.add((JButton) buttonBoard[row][col]);
            }
        }


        // Abilities left (2 grids)
        abilitiesPanel = new JPanel(new GridLayout(2,1));
        selectAbilities = new JPanel(new GridLayout(10,1));
        JLabel singleTarget = new JLabel("Single Target");
         singleTargetBox = new JComboBox<>();
        JLabel selfTarget = new JLabel("Self Target");
         selfTargetBox = new JComboBox<>();
        JLabel teamTarget = new JLabel("Team Target");
         teamTargetBox = new JComboBox<>();
        JLabel directionalTarget = new JLabel("Directional Target");
         directionalTargetBox = new JComboBox<>();
        JLabel surroundTarget = new JLabel("Surround Target");
         surroundTargetBox = new JComboBox<>();
        JLabel empty = new JLabel("Karinge");
        empty.setPreferredSize(new Dimension((int) (this.getWidth()*0.15),this.getHeight()));
        abilitiesPanel.add(empty);
        this.add(abilitiesPanel, BorderLayout.WEST);



        // TurnOrder + Movement &  Attack right (2 grids)
        rightPanel = new JPanel(new GridLayout(2,1));
        turnOrderPanel = new JPanel(new GridLayout(7,1));
        JTextArea Title = new JTextArea("TURN ORDER");
        Title.setEditable(false);
        Title.setFont(new Font("Chiller", Font.BOLD, 20));
        turnOrderPanel.add(Title);
        turnOrderPanel.setBackground(Color.decode("#f0b089"));
        for(int i = controller.getGame().getTurnOrder().getQueue().length-1; i>=0; i--){
            Champion c = (Champion) controller.getGame().getTurnOrder().getQueue()[i];
            JLabel championAndStat = new JLabel(""+ c.getName() +" ("+c.getCondition()+")");
            turnOrderPanel.add(championAndStat);
        }

        rightPanel.add(turnOrderPanel);

        JLabel empty2 = new JLabel("Karinge");
        //empty2.setPreferredSize(new Dimension((int) (this.getWidth()*0.15),this.getHeight()));
        //rightPanel.add(empty2);
        JPanel directionPad = new JPanel(new GridLayout(3,3));
        directionPad.setBackground(Color.decode("#34d942"));
        JLabel upperLeftEmpty = new JLabel("");
        JLabel upperRightEmpty = new JLabel("");
        JLabel lowerLeftEmpty = new JLabel("");
        JLabel lowerRightEmpty = new JLabel("");
        upDirection = new JButton("UP");
        leftDirection = new JButton("LEFT");
        rightDirection = new JButton("RIGHT");
        downDirection = new JButton("DOWN");
        attack = new JButton("ATTACK");
        upDirection.addActionListener(this);
        leftDirection.addActionListener(this);
        rightDirection.addActionListener(this);
        downDirection.addActionListener(this);
        attack.addActionListener(this);
        directionPad.add(upperLeftEmpty);
        directionPad.add(upDirection);
        directionPad.add(upperRightEmpty);
        directionPad.add(leftDirection);
        directionPad.add(attack);
        directionPad.add(rightDirection);
        directionPad.add(lowerLeftEmpty);
        directionPad.add(downDirection);
        directionPad.add(lowerRightEmpty);
        rightPanel.add(directionPad);
        this.add(rightPanel, BorderLayout.EAST);

        // Hover Info (Page Start)
        HoverChampInfo =  new JPanel(new GridLayout(2, 4));
        HoverChampInfo.setBackground(Color.decode("#e8f743") );
        HoverChampName = new JLabel("Empty");
        HoverChampType = new JLabel("-");
        HoverChampHP = new JLabel("-");
        HoverChampMana = new JLabel("-");
        HoverChampActionPoints = new JLabel("-");
        HoverChampAttackDmg = new JLabel("-");
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
        String currentColor = "";
        if (controller.getPlayer1().getTeam().contains(controller.getCurrentChampion())){
            currentColor = controller.getPlayer1().getColor();
        } else currentColor = controller.getPlayer2().getColor();
        CurrentChampInfo.setBackground(Color.decode(currentColor));
        CurrentPlayerName = new JLabel("Player Name: "+(controller.getPlayer1().getTeam().contains(controller.getCurrentChampion())? controller.getPlayer1().getName():controller.getPlayer2().getName()));
        ChampName = new JLabel("Champion: "+controller.getCurrentChampion().getName() + "  Status: " + controller.getCurrentChampion().getCondition());
        ChampType = new JLabel("Class: "+controller.getCurrentChampion().getHeroClass());
        ChampHP = new JLabel("HP: "+controller.getCurrentChampion().getCurrentHP()+"/"+controller.getCurrentChampion().getMaxHP());
        ChampMana = new JLabel("Mana: "+controller.getCurrentChampion().getMana());
        ChampActionPoints = new JLabel("Action Pts: "+controller.getCurrentChampion().getCurrentActionPoints()+"/"+controller.getCurrentChampion().getMaxActionPointsPerTurn());
        ChampAttackDmg = new JLabel("Attack Damage: "+controller.getCurrentChampion().getAttackDamage() + "  Range: " + controller.getCurrentChampion().getAttackRange());
        ChampAbilities = new JComboBox();
        ChampAppliedEffects = new JComboBox();

        for (Effect e: controller.getCurrentChampion().getAppliedEffects()) {
            ChampAppliedEffects.addItem(e.getName()+" - "+e.getDuration()+" turn(s)");
        }

        for (Ability a: controller.getCurrentChampion().getAbilities()) {
            if (a instanceof DamagingAbility) {
                ChampAbilities.addItem(a.getName()+" - Damage: "+((DamagingAbility) a).getDamageAmount()+" HP");
            } else if (a instanceof HealingAbility) {
                ChampAbilities.addItem(a.getName()+" - Heal: "+((HealingAbility) a).getHealAmount()+" HP");
            } else if (a instanceof CrowdControlAbility) {
                ChampAbilities.addItem(a.getName() + " - Effect: "+((CrowdControlAbility) a).getEffect().getName()+ " - Duration: "+((CrowdControlAbility) a).getEffect().getDuration());
            }
        }

        endTurn = new JButton("End Turn");
        endTurn.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean map = false;
        boolean alBreak = false;
        for (int i = 0; i < buttonBoard.length; i++) {
            for (int j = 0; j < buttonBoard[i].length; j++) {
                if (buttonBoard[i][j] == (Object) e.getSource()) {
                    map = true;
                    alBreak = true;
                    break;
                }
            }
            if (alBreak) break;
        }
        if (castAbilityFlag) {

            //cast directional ability
            if (e.getSource() == upDirection) {

            } else if (e.getSource() == downDirection) {

            } else if (e.getSource() == leftDirection) {

            } else if (e.getSource() == rightDirection) {

            }
            castAbilityFlag = false;
        } else if (attackFlag){

            //attack
            if (e.getSource() == upDirection) {

            } else if (e.getSource() == downDirection) {

            } else if (e.getSource() == leftDirection) {

            } else if (e.getSource() == rightDirection) {

            }
            attackFlag = false;
        } else if (e.getSource() instanceof JButton && map){

            if (((JButton) e.getSource()).getText().equals("")) {
                // Empty
                HoverChampName.setText("Empty");
                HoverChampType.setText("-");
                HoverChampHP.setText("-");
                HoverChampMana.setText("-");
                HoverChampActionPoints.setText("-");
                HoverChampAttackDmg.setText("-");
                HoverChampAbilities.removeAllItems();
                HoverChampAppliedEffects.removeAllItems();
               return;
            }

            else if (((JButton) e.getSource()).getText().equals("=============")) { // Cover
                Cover cvr = null;
                Boolean allBreak = false;
                for (int i = 0; i < buttonBoard.length; i++) {
                    for (int j = 0; j < buttonBoard[i].length; j++) {
                        if (buttonBoard[i][j] == (Object) e.getSource()) {
                            cvr = (Cover) Board[i][j];
                            allBreak = true;
                            break;
                        }
                    }
                    if (allBreak) break;
                }
                HoverChampName.setText("Cover");
                HoverChampType.setText("-");
                HoverChampHP.setText("HP: " + cvr.getCurrentHP());
                HoverChampMana.setText("-");
                HoverChampActionPoints.setText("-");
                HoverChampAttackDmg.setText("-");

                HoverChampAbilities.removeAllItems();
                HoverChampAppliedEffects.removeAllItems();
            } else { // Champ
                Boolean allBreak = false;
                Champion c = null;
                for (int i = 0; i < buttonBoard.length; i++) {
                    for (int j = 0; j < buttonBoard[i].length; j++) {
                        if (buttonBoard[i][j] == (Object) e.getSource()) {
                            //System.out.println("Karingeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                            c = controller.getChamp(((JButton) buttonBoard[i][j]).getText());
                            allBreak = true;
                            break;
                        }
                    }
                    if (allBreak) break;
                }

                HoverChampName.setText("Champion Name: " + c.getName() +"  Status: "+ c.getCondition());
                HoverChampType.setText("Class: " + c.getHeroClass());
                HoverChampHP.setText("HP: " + c.getCurrentHP() + "/" + c.getMaxHP());
                HoverChampMana.setText("Mana: " + c.getMana());
                HoverChampActionPoints.setText("Action Pts: " + c.getCurrentActionPoints() + "/" + c.getMaxActionPointsPerTurn());
                HoverChampAttackDmg.setText("Attack Damage: " + c.getAttackDamage() + "  Range: " + c.getAttackRange());

                HoverChampAbilities.removeAllItems();
                HoverChampAppliedEffects.removeAllItems();
                for (Effect ef : c.getAppliedEffects()) {
                    HoverChampAppliedEffects.addItem(ef.getName() + " - " + ef.getDuration() + " turn(s)");
                }

                for (Ability a : c.getAbilities()) {
                    if (a instanceof DamagingAbility) {
                        HoverChampAbilities.addItem(a.getName() + " - Damage: " + ((DamagingAbility) a).getDamageAmount() + " HP");
                    } else if (a instanceof HealingAbility) {
                        HoverChampAbilities.addItem(a.getName() + " - Heal: " + ((HealingAbility) a).getHealAmount() + " HP");
                    } else if (a instanceof CrowdControlAbility) {
                        HoverChampAbilities.addItem(a.getName() + " - Effect: " + ((CrowdControlAbility) a).getEffect().getName() + " - Duration: " + ((CrowdControlAbility) a).getEffect().getDuration());
                    }
                }

                this.revalidate();
                this.repaint();
            }
        } else {

            //move
            if (e.getSource() == upDirection) {
                controller.onMoveClicked(upDirection.getText());

            } else if (e.getSource() == downDirection) {
                controller.onMoveClicked(downDirection.getText());
            } else if (e.getSource() == leftDirection) {
                controller.onMoveClicked(leftDirection.getText());
            } else if (e.getSource() == rightDirection) {
                controller.onMoveClicked(rightDirection.getText());
            }
            updateCenter();
            updateSouth();

        }


    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
//        if (mouseEvent.getSource() instanceof JButton) {
//            if (((JButton) mouseEvent.getSource()).getText().equals("")) { // Empty
//                return;
//            } else if (((JButton) mouseEvent.getSource()).getText().equals("=============")) { // Cover
//                Cover cvr = null;
//                Boolean allBreak = false;
//                for (int i = 0; i < buttonBoard.length; i++) {
//                    for (int j = 0; j < buttonBoard[i].length; j++) {
//                        if (buttonBoard[i][j] == (Object) mouseEvent.getSource()) {
//                            cvr = (Cover) Board[i][j];
//                            allBreak = true;
//                            break;
//                        }
//                    }
//                    if (allBreak) break;
//                }
//                HoverChampName.setText("Cover");
//                HoverChampType.setText("-");
//                HoverChampHP.setText("HP: " + cvr.getCurrentHP());
//                HoverChampMana.setText("-");
//                HoverChampActionPoints.setText("-");
//                HoverChampAttackDmg.setText("-");
//
//                HoverChampAbilities.removeAllItems();
//                HoverChampAppliedEffects.removeAllItems();
//            } else { // Champ
//                Boolean allBreak = false;
//                Champion c = null;
//                for (int i = 0; i < buttonBoard.length; i++) {
//                    for (int j = 0; j < buttonBoard[i].length; j++) {
//                        if (buttonBoard[i][j] == (Object) mouseEvent.getSource()) {
//                            //System.out.println("Karingeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//                            c = controller.getChamp(((JButton) buttonBoard[i][j]).getText());
//                            allBreak = true;
//                            break;
//                        }
//                    }
//                    if (allBreak) break;
//                }
//
//                HoverChampName.setText("Champion Name: " + c.getName());
//                HoverChampType.setText("Class: " + c.getHeroClass());
//                HoverChampHP.setText("HP: " + c.getCurrentHP() + "/" + c.getMaxHP());
//                HoverChampMana.setText("Mana: " + c.getMana());
//                HoverChampActionPoints.setText("Action Pts: " + c.getCurrentActionPoints() + "/" + c.getMaxActionPointsPerTurn());
//                HoverChampAttackDmg.setText("Attack Damage: " + c.getAttackDamage());
//
//                HoverChampAbilities.removeAllItems();
//                HoverChampAppliedEffects.removeAllItems();
//                for (Effect e : c.getAppliedEffects()) {
//                    HoverChampAppliedEffects.addItem(e.getName() + " - " + e.getDuration() + " turn(s)");
//                }
//
//                for (Ability a : c.getAbilities()) {
//                    if (a instanceof DamagingAbility) {
//                        HoverChampAbilities.addItem(a.getName() + " - Damage: " + ((DamagingAbility) a).getDamageAmount() + " HP");
//                    } else if (a instanceof HealingAbility) {
//                        HoverChampAbilities.addItem(a.getName() + " - Heal: " + ((HealingAbility) a).getHealAmount() + " HP");
//                    } else if (a instanceof CrowdControlAbility) {
//                        HoverChampAbilities.addItem(a.getName() + " - Effect: " + ((CrowdControlAbility) a).getEffect().getName() + " - Duration: " + ((CrowdControlAbility) a).getEffect().getDuration());
//                    }
//                }
//
//                this.revalidate();
//                this.repaint();
//            }
//        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
//        if (mouseEvent.getSource() instanceof JButton) {
//            if (((JButton) mouseEvent.getSource()).getText().equals("")) { // Empty
//                return;
//            } else if (((JButton) mouseEvent.getSource()).getText().equals("=============")) { // Cover
//                Cover cvr = null;
//                Boolean allBreak = false;
//                for (int i = 0; i < buttonBoard.length; i++) {
//                    for (int j = 0; j < buttonBoard[i].length; j++) {
//                        if (buttonBoard[i][j] == (Object) mouseEvent.getSource()) {
//                            cvr = (Cover) Board[i][j];
//                            allBreak = true;
//                            break;
//                        }
//                    }
//                    if (allBreak) break;
//                }
//                HoverChampName.setText("Cover");
//                HoverChampType.setText("-");
//                HoverChampHP.setText("HP: " + cvr.getCurrentHP());
//                HoverChampMana.setText("-");
//                HoverChampActionPoints.setText("-");
//                HoverChampAttackDmg.setText("-");
//
//                HoverChampAbilities.removeAllItems();
//                HoverChampAppliedEffects.removeAllItems();
//            } else { // Champ
//                Boolean allBreak = false;
//                Champion c = null;
//                for (int i = 0; i < buttonBoard.length; i++) {
//                    for (int j = 0; j < buttonBoard[i].length; j++) {
//                        if (buttonBoard[i][j] == (Object) mouseEvent.getSource()) {
//                            System.out.println("Karingeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//                            c = controller.getChamp(((JButton) buttonBoard[i][j]).getText());
//                            allBreak = true;
//                            break;
//                        }
//                    }
//                    if (allBreak) break;
//                }
//
//                HoverChampName.setText("Champion Name: " + c.getName());
//                HoverChampType.setText("Class: " + c.getHeroClass());
//                HoverChampHP.setText("HP: " + c.getCurrentHP() + "/" + c.getMaxHP());
//                HoverChampMana.setText("Mana: " + c.getMana());
//                HoverChampActionPoints.setText("Action Pts: " + c.getCurrentActionPoints() + "/" + c.getMaxActionPointsPerTurn());
//                HoverChampAttackDmg.setText("Attack Damage: " + c.getAttackDamage());
//
//                HoverChampAbilities.removeAllItems();
//                HoverChampAppliedEffects.removeAllItems();
//                for (Effect e : c.getAppliedEffects()) {
//                    HoverChampAppliedEffects.addItem(e.getName() + " - " + e.getDuration() + " turn(s)");
//                }
//
//                for (Ability a : c.getAbilities()) {
//                    if (a instanceof DamagingAbility) {
//                        HoverChampAbilities.addItem(a.getName() + " - Damage: " + ((DamagingAbility) a).getDamageAmount() + " HP");
//                    } else if (a instanceof HealingAbility) {
//                        HoverChampAbilities.addItem(a.getName() + " - Heal: " + ((HealingAbility) a).getHealAmount() + " HP");
//                    } else if (a instanceof CrowdControlAbility) {
//                        HoverChampAbilities.addItem(a.getName() + " - Effect: " + ((CrowdControlAbility) a).getEffect().getName() + " - Duration: " + ((CrowdControlAbility) a).getEffect().getDuration());
//                    }
//                }
//
//                this.revalidate();
//                this.repaint();
//            }
//        }

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    public void updateCenter() {
        this.revalidate();
        this.repaint();

        for (int i = 0; i < 5; i++) { // Blind copy
            for (int j = 0; j < 5; j++) {
                JButton b = new JButton((Board[i][j] == null) ? "" : Board[i][j].toString());
                b.addActionListener(this);
                buttonBoard[i][j] = b;
            }
        }

        gameBoard.removeAll();

        for (int row = 4; row >= 0; row--) {
            for (int col = 0; col < 5; col++) {
                gameBoard.add((JButton) buttonBoard[row][col]);
            }
        }
        gameBoard.repaint();

        this.revalidate();
        this.repaint();
    }

    public void updateSouth() {

        CurrentPlayerName.setText("Player Name: "+(controller.getPlayer1().getTeam().contains(controller.getCurrentChampion())? controller.getPlayer1().getName():controller.getPlayer2().getName()));
        ChampName.setText("Champion: "+controller.getCurrentChampion().getName() + "  Status: " + controller.getCurrentChampion().getCondition());
        ChampType.setText("Class: "+controller.getCurrentChampion().getHeroClass());
        ChampHP.setText("HP: "+controller.getCurrentChampion().getCurrentHP()+"/"+controller.getCurrentChampion().getMaxHP());
        ChampMana.setText("Mana: "+controller.getCurrentChampion().getMana());
        ChampActionPoints.setText("Action Pts: "+controller.getCurrentChampion().getCurrentActionPoints()+"/"+controller.getCurrentChampion().getMaxActionPointsPerTurn());
        ChampAttackDmg.setText("Attack Damage: "+controller.getCurrentChampion().getAttackDamage() + "  Range: " + controller.getCurrentChampion().getAttackRange());

        ChampAbilities.removeAllItems();
        ChampAppliedEffects.removeAllItems();
        for (Effect e: controller.getCurrentChampion().getAppliedEffects()) {
            ChampAppliedEffects.addItem(e.getName()+" - "+e.getDuration()+" turn(s)");
        }

        for (Ability a: controller.getCurrentChampion().getAbilities()) {
            if (a instanceof DamagingAbility) {
                ChampAbilities.addItem(a.getName()+" - Damage: "+((DamagingAbility) a).getDamageAmount()+" HP");
            } else if (a instanceof HealingAbility) {
                ChampAbilities.addItem(a.getName()+" - Heal: "+((HealingAbility) a).getHealAmount()+" HP");
            } else if (a instanceof CrowdControlAbility) {
                ChampAbilities.addItem(a.getName() + " - Effect: "+((CrowdControlAbility) a).getEffect().getName()+ " - Duration: "+((CrowdControlAbility) a).getEffect().getDuration());
            }
        }

        this.revalidate();
        this.repaint();
    }
}