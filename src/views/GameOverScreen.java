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
import javax.swing.text.Document;

import engine.Player;
import model.abilities.Ability;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Effect;
import model.abilities.*;
import model.world.Champion;
import model.world.Condition;
import model.world.Cover;

import static model.abilities.AreaOfEffect.*;

public class GameOverScreen extends JFrame implements ActionListener{

    private GameController gameController;
    private JFrame Screen;
    private JLabel Info;
    private JButton Exit;
    private ActionListener Exitlistener;

    public GameOverScreen(GameController gameController){
        this.gameController = gameController;
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        Screen = new JFrame();
        Screen.setPreferredSize(new Dimension(width,height));
        Screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Screen.setVisible(true);
        Info = new JLabel(""+gameController.getWinner()+" is the winner!");
        Exit = new JButton("Exit Marvel: Ultimate War");
        Exit.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Exit){
            Screen.dispose();
        }
    }
}
