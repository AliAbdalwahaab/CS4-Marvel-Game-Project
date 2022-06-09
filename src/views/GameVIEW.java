package views;

import controller.GameController;
import model.abilities.*;
import model.effects.Effect;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;
import model.world.Hero;
import model.world.Villain;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import static model.abilities.AreaOfEffect.*;

public class GameVIEW extends JFrame implements ActionListener, MouseListener {

    private GameController controller;
    private JPanel gameBoard;

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
    private JPanel DpadMode;
    private JPanel abilitiesPanel;
    private JPanel abiltiesInfoPanel;
    private JTextArea abilitiesInfoText;
    private JPanel selectAbilities;
    private JButton useLeaderAbility;
    private JComboBox selfTargetBox;
    private JButton selfTargetButton;
    private JComboBox teamTargetBox;
    private JButton teamTargetButton;
    private JComboBox singleTargetBox;
    private JButton singleTargetButton;
    private JComboBox directionalTargetBox;
    private JButton directionalTargetButton;
    private JComboBox surroundTargetBox;
    private JButton surroundTargetButton;
    private Ability abilityToBeCast;
    private JPanel rightPanel;
    private JButton upDirection;
    private JButton leftDirection;
    private JButton rightDirection;
    private JButton downDirection;
    private JButton attack;
    private boolean castAbilityFlag = false;
    private boolean attackFlag = false;
    private boolean castSingleTarget = false;
    private JPanel northPanel;
    private JLabel leftLdrAbility;
    private JLabel rightLdrAbility;

    private boolean attackLED = false;

    private boolean castAbilityLED = false;

    private JLabel labelmoveLED;
    private JLabel labelattackLED;
    private JLabel labelcastAbilityLED;

    private Object[][] Board;
    private Object[][] buttonBoard;
    private Clip clip;
    private boolean moveLED = true;
    private Color clr;

    public GameVIEW(GameController controller) {
        this.controller = controller;
        this.setTitle("Marvel: Ultimate War");
        ImageIcon icon = new ImageIcon("The Hateful Hackers.png");
        this.setIconImage(icon.getImage());
        addMouseListener(this);
        this.setVisible(true);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0, 0, 1920, 1080);

        File audioFile = new File("Game music.wav").getAbsoluteFile();
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(audioFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            clip.open(audioInputStream);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);

        //Board (CENTER)
        gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(5, 5));
        add(gameBoard, BorderLayout.CENTER);
        Board = controller.getBoard();
        buttonBoard = new Object[5][5];
        for (int i = 0; i < 5; i++) { // Blind copy
            for (int j = 0; j < 5; j++) {
                //"<html>fnord<br />foo</html>"
                JButton b = new JButton((Board[i][j] == null) ? "" : "<html>" + Board[i][j].toString() + "<br>" + "HP : " +  ((Board[i][j] instanceof Champion) ? ((Champion)Board[i][j]).getCurrentHP() : ((Cover)Board[i][j]).getCurrentHP()) + "</html>");
                if (controller.getPlayer1().getTeam().contains(Board[i][j])) {
                    b.setBackground(Color.decode(controller.getPlayer1().getColor()));
                } else if (controller.getPlayer2().getTeam().contains(Board[i][j])) {
                    b.setBackground(Color.decode(controller.getPlayer2().getColor()));
                } else{
                    b.setBackground(Color.DARK_GRAY);
                }
                if (Board[i][j] instanceof Cover) {
                    b.setIcon(resizeIcon(new ImageIcon("COVER.png"), 70, 70));
                } else if (Board[i][j] instanceof Champion) {
                    b.setIcon(resizeIcon(new ImageIcon(((Champion)Board[i][j]).getName() +".png"), 70, 110));
                }

                b.setForeground(Color.white);
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
        abilitiesPanel.setPreferredSize(new Dimension(250,400));
        selectAbilities = new JPanel(new GridLayout(17,1));
        abiltiesInfoPanel = new JPanel(new GridLayout(1,1));
        abilitiesInfoText = new JTextArea("Hover over cast ability buttons see info");
        abilitiesInfoText.setEditable(false);
        abilitiesInfoText.setPreferredSize(new Dimension(150,250));

        abilitiesInfoText.setEditable(false);
        clr = Color.decode(controller.getPlayer1().getTeam().contains(controller.getCurrentChampion()) ? controller.getPlayer1().getColor() : controller.getPlayer2().getColor());
        JLabel leaderAbility = new JLabel("Leader Ability");
        leaderAbility.setForeground(Color.white);
        useLeaderAbility = new JButton("Use Leader Ability");
        useLeaderAbility.setBackground(clr);
        useLeaderAbility.setForeground(Color.white);
        useLeaderAbility.addActionListener(this);
        useLeaderAbility.addMouseListener(this);
        useLeaderAbility.addMouseListener(this);
        boolean bool = (controller.getGame().getFirstPlayer().getLeader() == controller.getCurrentChampion() || controller.getGame().getSecondPlayer().getLeader() == controller.getCurrentChampion())?
        		(controller.getGame().getFirstPlayer().getLeader() == controller.getCurrentChampion()?
        				!(controller.getGame().isFirstLeaderAbilityUsed()):(controller.getGame().getSecondPlayer().getLeader() == controller.getCurrentChampion()?!(controller.getGame().isSecondLeaderAbilityUsed()):false)):false;
        useLeaderAbility.setEnabled(bool);
        JLabel singleTarget = new JLabel("Single Target");
        singleTarget.setForeground(Color.white);
         singleTargetBox = new JComboBox();
         singleTargetBox.setBackground(Color.LIGHT_GRAY);
         singleTargetButton = new JButton("Cast Single Target Ability");
         singleTargetButton.setBackground(clr);
         singleTargetButton.setForeground(Color.white);
         singleTargetButton.addActionListener(this);
         singleTargetButton.addMouseListener(this);
        JLabel selfTarget = new JLabel("Self Target");
        selfTarget.setForeground(Color.white);
         selfTargetBox = new JComboBox();
         selfTargetBox.setBackground(Color.LIGHT_GRAY);
        selfTargetButton = new JButton("Cast Self Target Ability");
        selfTargetButton.setBackground(clr);
        selfTargetButton.setForeground(Color.white);
        selfTargetButton.addActionListener(this);
        selfTargetButton.addMouseListener(this);
        JLabel teamTarget = new JLabel("Team Target");
        teamTarget.setForeground(Color.white);
         teamTargetBox = new JComboBox();
         teamTargetBox.setBackground(Color.LIGHT_GRAY);
        teamTargetButton = new JButton("Cast Team Target Ability");
        teamTargetButton.setBackground(clr);
        teamTargetButton.setForeground(Color.white);
        teamTargetButton.addActionListener(this);
        teamTargetButton.addMouseListener(this);
        JLabel directionalTarget = new JLabel("Directional Target");
        directionalTarget.setForeground(Color.white);
         directionalTargetBox = new JComboBox();
         directionalTargetBox.setBackground(Color.LIGHT_GRAY);
        directionalTargetButton = new JButton("Cast Directional Ability");
        directionalTargetButton.setBackground(clr);
        directionalTargetButton.setForeground(Color.white);
        directionalTargetButton.addActionListener(this);
        directionalTargetButton.addMouseListener(this);
        JLabel surroundTarget = new JLabel("Surround Target");
        surroundTarget.setForeground(Color.white);
         surroundTargetBox = new JComboBox();
         surroundTargetBox.setBackground(Color.LIGHT_GRAY);
         
        surroundTargetButton = new JButton("Cast Surround Ability");
        surroundTargetButton.setBackground(clr);
        surroundTargetButton.setForeground(Color.white);
        surroundTargetButton.addActionListener(this);
        surroundTargetButton.addMouseListener(this);

        for (Ability a: controller.getCurrentChampion().getAbilities()) {

            if (a.getCastArea() == SELFTARGET) {
                if (a instanceof DamagingAbility) {
                    selfTargetBox.addItem(a.getName()+" - Damage: "+((DamagingAbility) a).getDamageAmount()+" HP");
                } else if (a instanceof HealingAbility) {
                    selfTargetBox.addItem(a.getName()+" - Heal: "+((HealingAbility) a).getHealAmount()+" HP");
                } else if (a instanceof CrowdControlAbility) {
                    selfTargetBox.addItem(a.getName() + " - Effect: "+((CrowdControlAbility) a).getEffect().getName()+ " - Duration: "+((CrowdControlAbility) a).getEffect().getDuration());
                }
            } else if (a.getCastArea() == SINGLETARGET) {
                if (a instanceof DamagingAbility) {
                    singleTargetBox.addItem(a.getName()+" - Damage: "+((DamagingAbility) a).getDamageAmount()+" HP");
                } else if (a instanceof HealingAbility) {
                    singleTargetBox.addItem(a.getName()+" - Heal: "+((HealingAbility) a).getHealAmount()+" HP");
                } else if (a instanceof CrowdControlAbility) {
                    singleTargetBox.addItem(a.getName() + " - Effect: "+((CrowdControlAbility) a).getEffect().getName()+ " - Duration: "+((CrowdControlAbility) a).getEffect().getDuration());
                }
            } else if (a.getCastArea() == TEAMTARGET) {
                if (a instanceof DamagingAbility) {
                    teamTargetBox.addItem(a.getName()+" - Damage: "+((DamagingAbility) a).getDamageAmount()+" HP");
                } else if (a instanceof HealingAbility) {
                    teamTargetBox.addItem(a.getName()+" - Heal: "+((HealingAbility) a).getHealAmount()+" HP");
                } else if (a instanceof CrowdControlAbility) {
                    teamTargetBox.addItem(a.getName() + " - Effect: "+((CrowdControlAbility) a).getEffect().getName()+ " - Duration: "+((CrowdControlAbility) a).getEffect().getDuration());
                }
            } else if (a.getCastArea() == SURROUND) {
                if (a instanceof DamagingAbility) {
                    surroundTargetBox.addItem(a.getName()+" - Damage: "+((DamagingAbility) a).getDamageAmount()+" HP");
                } else if (a instanceof HealingAbility) {
                    surroundTargetBox.addItem(a.getName()+" - Heal: "+((HealingAbility) a).getHealAmount()+" HP");
                } else if (a instanceof CrowdControlAbility) {
                    surroundTargetBox.addItem(a.getName() + " - Effect: "+((CrowdControlAbility) a).getEffect().getName()+ " - Duration: "+((CrowdControlAbility) a).getEffect().getDuration());
                }
            } else if (a.getCastArea() == DIRECTIONAL) {
                if (a instanceof DamagingAbility) {
                    directionalTargetBox.addItem(a.getName()+" - Damage: "+((DamagingAbility) a).getDamageAmount()+" HP");
                } else if (a instanceof HealingAbility) {
                    directionalTargetBox.addItem(a.getName()+" - Heal: "+((HealingAbility) a).getHealAmount()+" HP");
                } else if (a instanceof CrowdControlAbility) {
                    directionalTargetBox.addItem(a.getName() + " - Effect: "+((CrowdControlAbility) a).getEffect().getName()+ " - Duration: "+((CrowdControlAbility) a).getEffect().getDuration());
                }
            }

        }
        selectAbilities.add(leaderAbility);
        selectAbilities.add(useLeaderAbility);
        selectAbilities.add(singleTarget);
        selectAbilities.add(singleTargetBox);
        selectAbilities.add(singleTargetButton);
        selectAbilities.add(selfTarget);
        selectAbilities.add(selfTargetBox);
        selectAbilities.add(selfTargetButton);
        selectAbilities.add(teamTarget);
        selectAbilities.add(teamTargetBox);
        selectAbilities.add(teamTargetButton);
        selectAbilities.add(surroundTarget);
        selectAbilities.add(surroundTargetBox);
        selectAbilities.add(surroundTargetButton);
        selectAbilities.add(directionalTarget);
        selectAbilities.add(directionalTargetBox);
        selectAbilities.add(directionalTargetButton);
        selectAbilities.setBackground(Color.DARK_GRAY);

        abilitiesPanel.add(selectAbilities);
        abiltiesInfoPanel.add(abilitiesInfoText);
        abilitiesPanel.add(abiltiesInfoPanel);

        this.add(abilitiesPanel, BorderLayout.WEST);



        // TurnOrder + Movement &  Attack right (2 grids)
        rightPanel = new JPanel(new GridLayout(2,1));
        turnOrderPanel = new JPanel(new GridLayout(7,1));
        JTextArea Title = new JTextArea("                     TURN ORDER");
        Title.setOpaque(true);
        Title.setBackground(Color.DARK_GRAY);
        Title.setForeground(Color.WHITE);
        Title.setEditable(false);
        Title.setFont(new Font("Arial", Font.BOLD, 20));
        turnOrderPanel.add(Title, BorderLayout.CENTER);
        turnOrderPanel.setBackground(Color.DARK_GRAY);

        for(int i = controller.getGame().getTurnOrder().getQueue().length-1; i>=0; i--){
            JLabel championAndStat = new JLabel();
            championAndStat.setHorizontalAlignment(JLabel.CENTER);
            championAndStat.setOpaque(true);
            Champion c = (Champion) controller.getGame().getTurnOrder().getQueue()[i];

            if(controller.getGame().getFirstPlayer().getLeader() == c  ||  controller.getGame().getSecondPlayer().getLeader() == c ){

                championAndStat.setText(""+ c.getName() +" ("+c.getCondition()+") (Leader)");

                if(controller.getGame().getFirstPlayer().getTeam().contains(c))
                    championAndStat.setBackground(Color.decode(controller.getPlayer1().getColor()));
                else
                    championAndStat.setBackground(Color.decode(controller.getPlayer2().getColor()));

            }

           else if(c != controller.getGame().getFirstPlayer().getLeader() || c != controller.getGame().getSecondPlayer().getLeader()){
                championAndStat.setText("" + c.getName() + " (" + c.getCondition() + ")");

                if (controller.getGame().getFirstPlayer().getTeam().contains(c))
                    championAndStat.setBackground(Color.decode(controller.getPlayer1().getColor()));
                else
                    championAndStat.setBackground(Color.decode(controller.getPlayer2().getColor()));

            }

            turnOrderPanel.add(championAndStat);
        }

        rightPanel.add(turnOrderPanel);
        JPanel wholeMove = new JPanel(new GridLayout(2,1));
        DpadMode = new JPanel(new GridLayout(1,3));
        labelmoveLED = new JLabel("MOVE MODE");
        labelmoveLED.setHorizontalAlignment(JLabel.CENTER);
        labelmoveLED.setOpaque(true);
        labelattackLED = new JLabel("ATTACK MODE");
        labelattackLED.setHorizontalAlignment(JLabel.CENTER);
        labelattackLED.setOpaque(true);
        labelcastAbilityLED = new JLabel("CAST ABILITY MODE");
        labelcastAbilityLED.setHorizontalAlignment(JLabel.CENTER);
        labelcastAbilityLED.setOpaque(true);

        labelmoveLED.setBackground(Color.decode("#366938"));
        labelattackLED.setBackground(Color.decode("#366938"));
        labelcastAbilityLED.setBackground(Color.decode("#366938"));

        DpadMode.add(labelmoveLED);
        DpadMode.add(labelattackLED);
        DpadMode.add(labelcastAbilityLED);
        DpadMode.setFont(new Font("Arial", Font.BOLD, 30));
        wholeMove.add(DpadMode);
        setLEDActive();
        JPanel directionPad = new JPanel(new GridLayout(3,3));
        directionPad.setBackground(Color.decode("#3e423f"));
        JLabel upperLeftEmpty = new JLabel("");
        JLabel upperRightEmpty = new JLabel("");
        JLabel lowerLeftEmpty = new JLabel("");
        JLabel lowerRightEmpty = new JLabel("");
        upDirection = new JButton("UP");
        upDirection.setBackground(controller.getGame().getFirstPlayer().getTeam().contains(controller.getCurrentChampion())?Color.decode("#4d8de8"):Color.RED);
        upDirection.setForeground(Color.white);
        leftDirection = new JButton("LEFT");
        leftDirection.setBackground(controller.getGame().getFirstPlayer().getTeam().contains(controller.getCurrentChampion())?Color.decode("#4d8de8"):Color.RED);
        leftDirection.setForeground(Color.white);
        rightDirection = new JButton("RIGHT");
        rightDirection.setBackground(controller.getGame().getFirstPlayer().getTeam().contains(controller.getCurrentChampion())?Color.decode("#4d8de8"):Color.RED);
        rightDirection.setForeground(Color.white);
        downDirection = new JButton("DOWN");
        downDirection.setBackground(controller.getGame().getFirstPlayer().getTeam().contains(controller.getCurrentChampion())?Color.decode("#4d8de8"):Color.RED);
        downDirection.setForeground(Color.white);
        attack = new JButton("ATTACK");
        attack.setBackground(controller.getGame().getFirstPlayer().getTeam().contains(controller.getCurrentChampion())?Color.decode("#4d8de8"):Color.RED);
        attack.setForeground(Color.white);
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
        wholeMove.add(directionPad);
        rightPanel.add(wholeMove);
        this.add(rightPanel, BorderLayout.EAST);

        // Hover Info (Page Start)
        northPanel = new JPanel(new BorderLayout());
        JPanel names = new JPanel(new BorderLayout());
        JLabel vsText = new JLabel(controller.getPlayer1().getName() + "   vs   " + controller.getPlayer2().getName());
        vsText.setBackground(Color.DARK_GRAY);
        vsText.setFont(new Font("Arial",Font.BOLD,20));
        vsText.setOpaque(true);
        vsText.setHorizontalAlignment(JLabel.CENTER);
        vsText.setForeground(Color.white);
        leftLdrAbility = new JLabel("   Leader Ability: Not Used   ");
        leftLdrAbility.setPreferredSize(new Dimension(270,names.getHeight()));
        leftLdrAbility.setBackground(Color.decode(controller.getPlayer1().getColor()));
        leftLdrAbility.setFont(new Font("Arial",Font.BOLD,15));
        rightLdrAbility = new JLabel("   Leader Ability: Not Used   ");
        rightLdrAbility.setPreferredSize(new Dimension(270, names.getHeight()));
        rightLdrAbility.setBackground(Color.decode(controller.getPlayer2().getColor()));
        leftLdrAbility.setOpaque(true);
        leftLdrAbility.setForeground(Color.white);
        leftLdrAbility.setHorizontalAlignment(JLabel.CENTER);
        rightLdrAbility.setForeground(Color.white);
        rightLdrAbility.setFont(new Font("Arial",Font.BOLD,15));
        rightLdrAbility.setHorizontalAlignment(JLabel.CENTER);
        rightLdrAbility.setOpaque(true);
        names.add(vsText, BorderLayout.CENTER);
        names.add(leftLdrAbility, BorderLayout.WEST);
        names.add(rightLdrAbility, BorderLayout.EAST);

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
        northPanel.add(names, BorderLayout.NORTH);
        northPanel.add(HoverChampInfo, BorderLayout.CENTER);
        this.add(northPanel, BorderLayout.NORTH);

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

        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean map = false;
        boolean alBreak = false;
        for (int i = 0; i < buttonBoard.length; i++) {
            for (int j = 0; j < buttonBoard[i].length; j++) {
                if (buttonBoard[i][j] == (Object) e.getSource() && !castSingleTarget) {
                    map = true;
                    alBreak = true;
                    break;
                } else if (buttonBoard[i][j] == (Object) e.getSource() && castSingleTarget) {
                    boolean found = false;
                    for (Ability a: controller.getCurrentChampion().getAbilities()) {
                        if (a.getCastArea() == SINGLETARGET) {
                            if (((String) singleTargetBox.getSelectedItem()).contains(a.getName())) {
                                abilityToBeCast = a;
                                controller.onCastSingleTargetClicked(abilityToBeCast,i,j);
                                found = true;
                                break;
                            }
                        }

                    }
                    if (!found) controller.onCastSingleTargetClicked(abilityToBeCast,i,j);


                    updateSouth();
                    updateCenter();
                    checkForGameOver(controller.getGameOver());
                    castSingleTarget = false;
                    break;
                }
            }
            if (alBreak) break;
        }

        if (e.getSource() == useLeaderAbility){
                controller.onUseLeaderAbilityClicked();
                System.out.println("used leader ability");
                if (controller.getPlayer1().getTeam().contains(controller.getCurrentChampion())) {
                    leftLdrAbility.setText("   Leader Ability: Used   ");
                } else {
                    rightLdrAbility.setText("   Leader Ability: Used   ");
                }

            updateSouth();
            updateCenter();
            updateButtonColors();
            updateLeftPanel();
            updateNorth();
            checkForGameOver(controller.getGameOver());
        }

        else if (e.getSource() == endTurn) {
           // System.out.println("Yeppp");
            controller.onEndTurnClicked();
            turnOrderPanel.removeAll();
            JTextArea Title = new JTextArea("                    TURN ORDER");
            Title.setOpaque(true);
            Title.setBackground(Color.DARK_GRAY);
            Title.setForeground(Color.WHITE);
            Title.setEditable(false);
            Title.setFont(new Font("Arial", Font.BOLD, 20));
            turnOrderPanel.add(Title, BorderLayout.CENTER);
            turnOrderPanel.setBackground(Color.DARK_GRAY);
            turnOrderPanel.add(Title);
            //System.out.println("===================================================================");
            for(int i = controller.getGame().getTurnOrder().getQueue().length-1; i>=0; i--){
                JLabel championAndStat = new JLabel();
                championAndStat.setHorizontalAlignment(JLabel.CENTER);
                championAndStat.setOpaque(true);
                if (controller.getGame().getTurnOrder().getQueue()[i] != null) {
                    Champion c = (Champion) controller.getGame().getTurnOrder().getQueue()[i];

                    if(controller.getGame().getFirstPlayer().getLeader() == c  ||  controller.getGame().getSecondPlayer().getLeader() == c ){

                        championAndStat.setText(""+ c.getName() +" ("+c.getCondition()+") (Leader)");

                        if(controller.getGame().getFirstPlayer().getTeam().contains(c))
                            championAndStat.setBackground(Color.decode(controller.getPlayer1().getColor()));
                        else
                            championAndStat.setBackground(Color.decode(controller.getPlayer2().getColor()));

                    }

                    else if(c != controller.getGame().getFirstPlayer().getLeader() || c != controller.getGame().getSecondPlayer().getLeader()){
                        championAndStat.setText("" + c.getName() + " (" + c.getCondition() + ")");

                        if (controller.getGame().getFirstPlayer().getTeam().contains(c))
                            championAndStat.setBackground(Color.decode(controller.getPlayer1().getColor()));
                        else
                            championAndStat.setBackground(Color.decode(controller.getPlayer2().getColor()));

                    }

                    turnOrderPanel.add(championAndStat);
                }
            }
            //System.out.println("===================================================================");
            turnOrderPanel.repaint();
            turnOrderPanel.revalidate();
            moveLED =true;
            attackLED =false;
            castAbilityLED = false;
            setLEDActive();


            updateLeftPanel();
            updateButtonColors();
            updateCenter();
            updateSouth();

            checkForGameOver(controller.getGameOver());
        }
        else if (e.getSource() == attack) {
            attackFlag = !attackLED;
            attackLED = !attackLED;
            castAbilityFlag = false;
            moveLED = false;
            castAbilityLED = false;
            setLEDActive();
           //JOptionPane.showMessageDialog(null,"Please input direction of attack using DPAD");
        } else if (e.getSource() == selfTargetButton) {
            boolean found = false;
            for (Ability a: controller.getCurrentChampion().getAbilities()) {
                if (a.getCastArea() == SELFTARGET) {
                    if (((String) selfTargetBox.getSelectedItem()).contains(a.getName())) {
                        abilityToBeCast = a;
                        controller.onCastAbilityClicked(abilityToBeCast,"");
                        found = true;
                        break;
                    }
                }

            }
            if (!found) controller.onCastAbilityClicked(abilityToBeCast,"");


            updateSouth();
            updateCenter();
            updateButtonColors();
            updateLeftPanel();
            checkForGameOver(controller.getGameOver());
        } else if (e.getSource() == singleTargetButton) {

            castSingleTarget = true;
            JOptionPane.showMessageDialog(null,"Please click on the cell containing the element you want to cast the ability onto");


        } else if (e.getSource() == teamTargetButton) {
            boolean found = false;
            for (Ability a: controller.getCurrentChampion().getAbilities()) {
                if (a.getCastArea() == TEAMTARGET) {
                        if (((String) teamTargetBox.getSelectedItem()).contains(a.getName())) {
                            abilityToBeCast = a;
                            controller.onCastAbilityClicked(abilityToBeCast,"");
                            found = true;
                            break;
                        }
                    }

                }
            if (!found) controller.onCastAbilityClicked(abilityToBeCast,"");


            updateSouth();
            updateCenter();
            updateButtonColors();
            updateLeftPanel();
            checkForGameOver(controller.getGameOver());

        } else if (e.getSource() == directionalTargetButton) {
            castAbilityFlag = !castAbilityLED;
            castAbilityLED = !castAbilityLED;
            attackFlag = false;
            attackLED = false;
            moveLED = false;
            setLEDActive();
           // JOptionPane.showMessageDialog(null,"Please input direction of ability using DPAD");


        } else if (e.getSource() == surroundTargetButton) {
            boolean found = false;
            for (Ability a: controller.getCurrentChampion().getAbilities()) {
                if (a.getCastArea() == SURROUND) {
                    if (((String) surroundTargetBox.getSelectedItem()).contains(a.getName())) {
                        abilityToBeCast = a;
                        controller.onCastAbilityClicked(abilityToBeCast,"");
                        found = true;
                        break;
                    }
                }

            }
            if (!found) controller.onCastAbilityClicked(abilityToBeCast,"");


            updateSouth();
            updateButtonColors();
            updateCenter();
            updateLeftPanel();
            checkForGameOver(controller.getGameOver());

        }

        else if (castAbilityFlag) {
            boolean found = false;
            //cast directional ability
            if (e.getSource() == upDirection) {
                System.out.println("Entered");
                for (Ability a: controller.getCurrentChampion().getAbilities()) {
                    if (a.getCastArea() == DIRECTIONAL) {
                        if (((String) directionalTargetBox.getSelectedItem()).contains(a.getName())) {
                            abilityToBeCast = a;
                            controller.onCastAbilityClicked(abilityToBeCast,"UP");
                            found = true;
                            break;
                        }

                    }
                }
                if (!found) controller.onCastAbilityClicked(abilityToBeCast,"UP");



            } else if (e.getSource() == downDirection) {
                for (Ability a: controller.getCurrentChampion().getAbilities()) {
                    if (a.getCastArea() == DIRECTIONAL) {
                        if (((String) directionalTargetBox.getSelectedItem()).contains(a.getName())) {
                            abilityToBeCast = a;
                            controller.onCastAbilityClicked(abilityToBeCast,"DOWN");
                            found = true;
                            break;
                        }

                    }
                }
                if (!found) controller.onCastAbilityClicked(abilityToBeCast,"DOWN");

            } else if (e.getSource() == leftDirection) {
                for (Ability a: controller.getCurrentChampion().getAbilities()) {
                    if (a.getCastArea() == DIRECTIONAL) {
                        if (((String) directionalTargetBox.getSelectedItem()).contains(a.getName())) {
                            abilityToBeCast = a;
                            controller.onCastAbilityClicked(abilityToBeCast,"LEFT");
                            found = true;
                            break;
                        }

                    }
                }
                if (!found) controller.onCastAbilityClicked(abilityToBeCast,"LEFT");

            } else if (e.getSource() == rightDirection) {
                for (Ability a: controller.getCurrentChampion().getAbilities()) {
                    if (a.getCastArea() == DIRECTIONAL) {
                        if (((String) directionalTargetBox.getSelectedItem()).contains(a.getName())) {
                            abilityToBeCast = a;
                            controller.onCastAbilityClicked(abilityToBeCast,"RIGHT");
                            found = true;
                            break;
                        }

                    }
                }
                if (!found) controller.onCastAbilityClicked(abilityToBeCast,"RIGHT");

            }
            castAbilityFlag = false;
            castAbilityLED = false;
            setLEDActive();

            updateSouth();
            updateButtonColors();
            updateCenter();
            updateLeftPanel();
            checkForGameOver(controller.getGameOver());
        } else if (attackFlag){

            //attack
            if (e.getSource() == upDirection) {
                controller.onAttackClicked("UP");
            } else if (e.getSource() == downDirection) {
                controller.onAttackClicked("DOWN");
            } else if (e.getSource() == leftDirection) {
                controller.onAttackClicked("LEFT");
            } else if (e.getSource() == rightDirection) {
                controller.onAttackClicked("RIGHT");
            }
            attackFlag = false;
            attackLED = false;
            setLEDActive();

            updateCenter();
            updateButtonColors();
            updateSouth();
            updateLeftPanel();
            checkForGameOver(controller.getGameOver());
        } else if (e.getSource() instanceof JButton && map) {

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

            else if (fetchText(((JButton) e.getSource()).getText()).equals("COVER")) { // Cover
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
                HoverChampType.setText("Distance :  " + (Math.abs(cvr.getLocation().x - controller.getGame().getCurrentChampion().getLocation().x) + Math.abs(cvr.getLocation().y - controller.getGame().getCurrentChampion().getLocation().y)));
                HoverChampHP.setText("HP: " + cvr.getCurrentHP());
                HoverChampMana.setText("-");
                HoverChampActionPoints.setText("-");
                HoverChampAttackDmg.setText("-");

                HoverChampAbilities.removeAllItems();
                HoverChampAppliedEffects.removeAllItems();
            } else { // Champ info
                Boolean allBreak = false;
                Champion c = null;
                for (int i = 0; i < buttonBoard.length; i++) {
                    for (int j = 0; j < buttonBoard[i].length; j++) {
                        if (buttonBoard[i][j] == (Object) e.getSource()) {
                            //System.out.println("Karingeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                            c = (Champion) Board[i][j];

                            allBreak = true;
                            break;
                        }
                    }
                    if (allBreak) break;
                }
                

                HoverChampName.setText("Champion Name: " + c.getName() +"  Status: "+ c.getCondition());
                HoverChampType.setText("Class: " + c.getHeroClass() + "           Distance: " + (Math.abs(c.getLocation().x - controller.getGame().getCurrentChampion().getLocation().x) + Math.abs(c.getLocation().y - controller.getGame().getCurrentChampion().getLocation().y)));
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
                        HoverChampAbilities.addItem(a.getName() + " - Damage: " + ((DamagingAbility) a).getDamageAmount() + " HP" + " " + a.getCastArea());
                    } else if (a instanceof HealingAbility) {
                        HoverChampAbilities.addItem(a.getName() + " - Heal: " + ((HealingAbility) a).getHealAmount() + " HP" + " " + a.getCastArea());
                    } else if (a instanceof CrowdControlAbility) {
                        HoverChampAbilities.addItem(a.getName() + " - Effect: " + ((CrowdControlAbility) a).getEffect().getName() + " - Duration: " + ((CrowdControlAbility) a).getEffect().getDuration() + " " + a.getCastArea());
                    }
                }

                this.revalidate();
                this.repaint();
            }
        } else if (!(castAbilityFlag) && !(attackFlag) && !(attackLED) && !(castAbilityLED)){ // move
            setLEDActive();
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
            updateButtonColors();
            updateSouth();
            updateLeftPanel();
            checkForGameOver(controller.getGameOver());

        }


    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == singleTargetButton) {
            for (Ability a: controller.getCurrentChampion().getAbilities()) {
                if (a.getCastArea() == SINGLETARGET) {
                    if (singleTargetBox.getSelectedItem() != null && ((String) singleTargetBox.getSelectedItem()).contains(a.getName())) {
                        String toAdd = "";
                        if (a instanceof DamagingAbility) {
                            toAdd += "Damage dealt: "+((DamagingAbility) a).getDamageAmount()+" HP";
                        } else if (a instanceof HealingAbility) {
                            toAdd += "Amount Healed: "+((HealingAbility) a).getHealAmount()+" HP";
                        } else if (a instanceof CrowdControlAbility) {
                            toAdd += "Effect: "+((CrowdControlAbility) a).getEffect().getName()+"\n"
                                    +"Effect type: "+((CrowdControlAbility) a).getEffect().getType()+"\n"
                                    +"Effect duration: "+((CrowdControlAbility) a).getEffect().getDuration()+" turn(s)";
                        }

                        abilitiesInfoText.setText("Name: "+a.getName()+"\n"
                        +"Mana Cost: "+a.getManaCost()+"\n"
                        +"Required Action Points: " + a.getRequiredActionPoints() +"\n"
                        +"Base Cooldown: "+a.getBaseCooldown()+" turn(s)"+"\n"
                        +"Current Cooldown: "+a.getCurrentCooldown()+" turn(s)"+"\n"
                        +"Cast Range: "+a.getCastRange()+"\n"
                        +"Area of Effect: "+a.getCastArea()+"\n"
                        +toAdd);
                        break;
                    }

                }
            }

        } else if (mouseEvent.getSource() == selfTargetButton) {
            for (Ability a: controller.getCurrentChampion().getAbilities()) {
                if (a.getCastArea() == SELFTARGET) {
                    if (selfTargetBox.getSelectedItem() != null && ((String) selfTargetBox.getSelectedItem()).contains(a.getName())) {
                        String toAdd = "";
                        if (a instanceof DamagingAbility) {
                            toAdd += "Damage dealt: "+((DamagingAbility) a).getDamageAmount()+" HP";
                        } else if (a instanceof HealingAbility) {
                            toAdd += "Amount Healed: "+((HealingAbility) a).getHealAmount()+" HP";
                        } else if (a instanceof CrowdControlAbility) {
                            toAdd += "Effect: "+((CrowdControlAbility) a).getEffect().getName()+"\n"
                                    +"Effect type: "+((CrowdControlAbility) a).getEffect().getType()+"\n"
                                    +"Effect duration: "+((CrowdControlAbility) a).getEffect().getDuration()+" turn(s)";
                        }

                        abilitiesInfoText.setText("Name: "+a.getName()+"\n"
                                +"Mana Cost: "+a.getManaCost()+"\n"
                                +"Required Action Points: " + a.getRequiredActionPoints() +"\n"
                                +"Base Cooldown: "+a.getBaseCooldown()+" turn(s)"+"\n"
                                +"Current Cooldown: "+a.getCurrentCooldown()+" turn(s)"+"\n"
                                +"Cast Range: "+a.getCastRange()+"\n"
                                +"Area of Effect: "+a.getCastArea()+"\n"
                                +toAdd);
                        break;
                    }

                }
            }

        } else if (mouseEvent.getSource() == teamTargetButton) {
            for (Ability a: controller.getCurrentChampion().getAbilities()) {
                if (a.getCastArea() == TEAMTARGET) {
                    if (teamTargetBox.getSelectedItem() != null && ((String) teamTargetBox.getSelectedItem()).contains(a.getName())) {
                        String toAdd = "";
                        if (a instanceof DamagingAbility) {
                            toAdd += "Damage dealt: "+((DamagingAbility) a).getDamageAmount()+" HP";
                        } else if (a instanceof HealingAbility) {
                            toAdd += "Amount Healed: "+((HealingAbility) a).getHealAmount()+" HP";
                        } else if (a instanceof CrowdControlAbility) {
                            toAdd += "Effect: "+((CrowdControlAbility) a).getEffect().getName()+"\n"
                                    +"Effect type: "+((CrowdControlAbility) a).getEffect().getType()+"\n"
                                    +"Effect duration: "+((CrowdControlAbility) a).getEffect().getDuration()+" turn(s)";
                        }

                        abilitiesInfoText.setText("Name: "+a.getName()+"\n"
                                +"Mana Cost: "+a.getManaCost()+"\n"
                                +"Required Action Points: " + a.getRequiredActionPoints() +"\n"
                                +"Base Cooldown: "+a.getBaseCooldown()+" turn(s)"+"\n"
                                +"Current Cooldown: "+a.getCurrentCooldown()+" turn(s)"+"\n"
                                +"Cast Range: "+a.getCastRange()+"\n"
                                +"Area of Effect: "+a.getCastArea()+"\n"
                                +toAdd);
                        break;
                    }

                }
            }

        } else if (mouseEvent.getSource() == directionalTargetButton) {
            for (Ability a: controller.getCurrentChampion().getAbilities()) {
                if (a.getCastArea() == DIRECTIONAL) {
                    if (directionalTargetBox.getSelectedItem() != null && ((String) directionalTargetBox.getSelectedItem()).contains(a.getName())) {
                        String toAdd = "";
                        if (a instanceof DamagingAbility) {
                            toAdd += "Damage dealt: "+((DamagingAbility) a).getDamageAmount()+" HP";
                        } else if (a instanceof HealingAbility) {
                            toAdd += "Amount Healed: "+((HealingAbility) a).getHealAmount()+" HP";
                        } else if (a instanceof CrowdControlAbility) {
                            toAdd += "Effect: "+((CrowdControlAbility) a).getEffect().getName()+"\n"
                                    +"Effect type: "+((CrowdControlAbility) a).getEffect().getType()+"\n"
                                    +"Effect duration: "+((CrowdControlAbility) a).getEffect().getDuration()+" turn(s)";
                        }

                        abilitiesInfoText.setText("Name: "+a.getName()+"\n"
                                +"Mana Cost: "+a.getManaCost()+"\n"
                                +"Required Action Points: " + a.getRequiredActionPoints() +"\n"
                                +"Base Cooldown: "+a.getBaseCooldown()+" turn(s)"+"\n"
                                +"Current Cooldown: "+a.getCurrentCooldown()+" turn(s)"+"\n"
                                +"Cast Range: "+a.getCastRange()+"\n"
                                +"Area of Effect: "+a.getCastArea()+"\n"
                                +toAdd);
                        break;
                    }

                }
            }

        } else if (mouseEvent.getSource() == surroundTargetButton) {
            for (Ability a: controller.getCurrentChampion().getAbilities()) {
                if (a.getCastArea() == SURROUND) {
                    if (surroundTargetBox.getSelectedItem() != null && ((String) surroundTargetBox.getSelectedItem()).contains(a.getName())) {
                        String toAdd = "";
                        if (a instanceof DamagingAbility) {
                            toAdd += "Damage dealt: "+((DamagingAbility) a).getDamageAmount()+" HP";
                        } else if (a instanceof HealingAbility) {
                            toAdd += "Amount Healed: "+((HealingAbility) a).getHealAmount()+" HP";
                        } else if (a instanceof CrowdControlAbility) {
                            toAdd += "Effect: "+((CrowdControlAbility) a).getEffect().getName()+"\n"
                                    +"Effect type: "+((CrowdControlAbility) a).getEffect().getType()+"\n"
                                    +"Effect duration: "+((CrowdControlAbility) a).getEffect().getDuration()+" turn(s)";
                        }

                        abilitiesInfoText.setText("Name: "+a.getName()+"\n"
                                +"Mana Cost: "+a.getManaCost()+"\n"
                                +"Required Action Points: " + a.getRequiredActionPoints() +"\n"
                                +"Base Cooldown: "+a.getBaseCooldown()+" turn(s)"+"\n"
                                +"Current Cooldown: "+a.getCurrentCooldown()+" turn(s)"+"\n"
                                +"Required Action Points: "+a.getRequiredActionPoints()+"\n"
                                +"Cast Range: "+a.getCastRange()+"\n"
                                +"Area of Effect: "+a.getCastArea()+"\n"
                                +toAdd);
                        break;
                    }

                }
            }
        } else if (mouseEvent.getSource() == useLeaderAbility && useLeaderAbility.isEnabled()) {
        	String toAdd = "";
			if (controller.getCurrentChampion() instanceof Hero) {
				toAdd += "Removes all negative effects from the players entire team and adds an Embrace effect to them which lasts for 2 turns. " +"\n"+
						"(Can only be used once in the entire game)";
			} else if (controller.getCurrentChampion() instanceof AntiHero) {
				toAdd += "All champions on the board except for the leaders of each team will be stunned for 2 turns."+"\n"+"(Can only be used once in the entire game)";
			} else if (controller.getCurrentChampion() instanceof Villain) {
				toAdd += "Immediately kills all enemy champions with less than 30% health points. " +"\n"+
						"(Can only be used once in the entire game)";
			}
			abilitiesInfoText.setText(toAdd);
			abilitiesInfoText.setLineWrap(true);
        }
        abilitiesInfoText.setBackground(Color.DARK_GRAY);
        abilitiesInfoText.setForeground(Color.white);


    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        abilitiesInfoText.setText("Hover over cast ability buttons see info");
    }

    public void updateLeftPanel() {
    	clr = Color.decode(controller.getPlayer1().getTeam().contains(controller.getCurrentChampion()) ? controller.getPlayer1().getColor() : controller.getPlayer2().getColor());
       
    	useLeaderAbility.setBackground(clr);
    	
         singleTargetButton.setBackground(clr);
         singleTargetButton.setForeground(Color.white);
         
       
        selfTargetButton.setBackground(clr);
        
        
        teamTargetButton.setBackground(clr);
        
        
         
        directionalTargetButton.setBackground(clr);
        
        
        surroundTargetButton.setBackground(clr);
        
        boolean bool = (controller.getGame().getFirstPlayer().getLeader() == controller.getCurrentChampion() || controller.getGame().getSecondPlayer().getLeader() == controller.getCurrentChampion())?
                (controller.getGame().getFirstPlayer().getLeader() == controller.getCurrentChampion()?
                        !(controller.getGame().isFirstLeaderAbilityUsed()):(controller.getGame().getSecondPlayer().getLeader() == controller.getCurrentChampion()?!(controller.getGame().isSecondLeaderAbilityUsed()):false)):false;
        useLeaderAbility.setEnabled(bool);

        singleTargetBox.removeAllItems();
        singleTargetBox.setForeground(Color.white);

        selfTargetBox.removeAllItems();
        selfTargetBox.setForeground(Color.white);
        
        teamTargetBox.removeAllItems();
        teamTargetBox.setForeground(Color.white);
        
        directionalTargetBox.removeAllItems();
        directionalTargetBox.setForeground(Color.white);
        
        surroundTargetBox.removeAllItems();
        surroundTargetBox.setForeground(Color.white);

        for (Ability a : controller.getCurrentChampion().getAbilities()) {

            if (a.getCastArea() == SELFTARGET) {
                if (a instanceof DamagingAbility) {
                    selfTargetBox.addItem(a.getName() + " - Damage: " + ((DamagingAbility) a).getDamageAmount() + " HP");
                } else if (a instanceof HealingAbility) {
                    selfTargetBox.addItem(a.getName() + " - Heal: " + ((HealingAbility) a).getHealAmount() + " HP");
                } else if (a instanceof CrowdControlAbility) {
                    selfTargetBox.addItem(a.getName() + " - Effect: " + ((CrowdControlAbility) a).getEffect().getName() + " - Duration: " + ((CrowdControlAbility) a).getEffect().getDuration());
                }
            } else if (a.getCastArea() == SINGLETARGET) {
                if (a instanceof DamagingAbility) {
                    singleTargetBox.addItem(a.getName() + " - Damage: " + ((DamagingAbility) a).getDamageAmount() + " HP");
                } else if (a instanceof HealingAbility) {
                    singleTargetBox.addItem(a.getName() + " - Heal: " + ((HealingAbility) a).getHealAmount() + " HP");
                } else if (a instanceof CrowdControlAbility) {
                    singleTargetBox.addItem(a.getName() + " - Effect: " + ((CrowdControlAbility) a).getEffect().getName() + " - Duration: " + ((CrowdControlAbility) a).getEffect().getDuration());
                }
            } else if (a.getCastArea() == TEAMTARGET) {
                if (a instanceof DamagingAbility) {
                    teamTargetBox.addItem(a.getName() + " - Damage: " + ((DamagingAbility) a).getDamageAmount() + " HP");
                } else if (a instanceof HealingAbility) {
                    teamTargetBox.addItem(a.getName() + " - Heal: " + ((HealingAbility) a).getHealAmount() + " HP");
                } else if (a instanceof CrowdControlAbility) {
                    teamTargetBox.addItem(a.getName() + " - Effect: " + ((CrowdControlAbility) a).getEffect().getName() + " - Duration: " + ((CrowdControlAbility) a).getEffect().getDuration());
                }
            } else if (a.getCastArea() == SURROUND) {
                if (a instanceof DamagingAbility) {
                    surroundTargetBox.addItem(a.getName() + " - Damage: " + ((DamagingAbility) a).getDamageAmount() + " HP");
                } else if (a instanceof HealingAbility) {
                    surroundTargetBox.addItem(a.getName() + " - Heal: " + ((HealingAbility) a).getHealAmount() + " HP");
                } else if (a instanceof CrowdControlAbility) {
                    surroundTargetBox.addItem(a.getName() + " - Effect: " + ((CrowdControlAbility) a).getEffect().getName() + " - Duration: " + ((CrowdControlAbility) a).getEffect().getDuration());
                }
            } else if (a.getCastArea() == DIRECTIONAL) {
                if (a instanceof DamagingAbility) {
                    directionalTargetBox.addItem(a.getName() + " - Damage: " + ((DamagingAbility) a).getDamageAmount() + " HP");
                } else if (a instanceof HealingAbility) {
                    directionalTargetBox.addItem(a.getName() + " - Heal: " + ((HealingAbility) a).getHealAmount() + " HP");
                } else if (a instanceof CrowdControlAbility) {
                    directionalTargetBox.addItem(a.getName() + " - Effect: " + ((CrowdControlAbility) a).getEffect().getName() + " - Duration: " + ((CrowdControlAbility) a).getEffect().getDuration());
                }
            }
            this.repaint();
            this.revalidate();
        }
    }

    public void updateCenter() {
        this.revalidate();
        this.repaint();

        for (int i = 0; i < 5; i++) { // Blind copy
            for (int j = 0; j < 5; j++) {
                JButton b = new JButton((Board[i][j] == null) ? "" : "<html>" + Board[i][j].toString() + "<br>" + "HP : " +  ((Board[i][j] instanceof Champion) ? ((Champion)Board[i][j]).getCurrentHP() : ((Cover)Board[i][j]).getCurrentHP()) + "</html>");
                if (controller.getPlayer1().getTeam().contains(Board[i][j])) {
                    b.setBackground(Color.decode(controller.getPlayer1().getColor()));
                } else if (controller.getPlayer2().getTeam().contains(Board[i][j])) {
                    b.setBackground(Color.decode(controller.getPlayer2().getColor()));
                } else{
                    b.setBackground(Color.DARK_GRAY);
                }
                if (Board[i][j] instanceof Cover) {
                    b.setIcon(resizeIcon(new ImageIcon("COVER.png"), 70, 70));
                } else if (Board[i][j] instanceof Champion) {
                    b.setIcon(resizeIcon(new ImageIcon(((Champion)Board[i][j]).getName() +".png"), 70, 110));
                }
                b.setForeground(Color.white);
                b.addActionListener(this);
                b.addMouseListener(this);
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

    public void updateNorth() {

    }

    public void updateSouth() {
        String currentColor = "";
        if (controller.getPlayer1().getTeam().contains(controller.getCurrentChampion())){
            currentColor = controller.getPlayer1().getColor();
        } else currentColor = controller.getPlayer2().getColor();
        CurrentChampInfo.setBackground(Color.decode(currentColor));
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

    public static String fetchText(String txt) {
        return txt.substring(6).split("<br>")[0];
    }

    private void setLEDActive (){
        if (attackLED == false && castAbilityLED == false)
            moveLED = true;
        if(moveLED)
            labelmoveLED.setBackground(Color.decode("#0dff15"));
        else
            labelmoveLED.setBackground(Color.decode("#366938"));

        if (attackLED)
            labelattackLED.setBackground(Color.decode("#0dff15"));
        else
            labelattackLED.setBackground(Color.decode("#366938"));

        if (castAbilityLED)
            labelcastAbilityLED.setBackground(Color.decode("#0dff15"));
        else
            labelcastAbilityLED.setBackground(Color.decode("#366938"));


    }

    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    //TODO: call this method checkForGameOver everywhere and on all updates to check if the game is over and dispose of the current screen ****
    //TODO: **** and instantiate a new game over screen.

    public void checkForGameOver(boolean b){
        controller.setGameOver();
        if (controller.getGameOver()) {
            clip.stop();
            JOptionPane.showMessageDialog(null,"Game Over! " + controller.getWinner() + " won!");
            this.dispose();
            new GameOverScreen(controller);
        }
    }

    public void updateButtonColors() {
        upDirection.setBackground(controller.getGame().getFirstPlayer().getTeam().contains(controller.getCurrentChampion())?Color.decode("#4d8de8"):Color.RED);
        downDirection.setBackground(controller.getGame().getFirstPlayer().getTeam().contains(controller.getCurrentChampion())?Color.decode("#4d8de8"):Color.RED);
        leftDirection.setBackground(controller.getGame().getFirstPlayer().getTeam().contains(controller.getCurrentChampion())?Color.decode("#4d8de8"):Color.RED);
        rightDirection.setBackground(controller.getGame().getFirstPlayer().getTeam().contains(controller.getCurrentChampion())?Color.decode("#4d8de8"):Color.RED);
        attack.setBackground(controller.getGame().getFirstPlayer().getTeam().contains(controller.getCurrentChampion())?Color.decode("#4d8de8"):Color.RED);
    }
}
