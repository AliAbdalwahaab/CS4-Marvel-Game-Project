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
    private JLabel BG;
    private JButton Exit;
    private ActionListener Exitlistener;

    public GameOverScreen(GameController gameController) {
        this.gameController = gameController;
        this.setLayout(new BorderLayout());
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setPreferredSize(new Dimension(width, height));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        Info = new JLabel("" + gameController.getWinner() + " is the winner!");
        Info.setOpaque(false);
        Exit = new JButton("Exit Marvel: Ultimate War");
        BG = new JLabel();
        ImageIcon image = new ImageIcon("Marvel Ultimate War.jpg");
        image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        BG.setIcon(image);
        Exit.addActionListener(this);

        //TODO: check out why the the image is not displayed in background.

        this.add(Exit, BorderLayout.SOUTH);
        this.add(BG, BorderLayout.CENTER);
        this.add(Info, BorderLayout.CENTER);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Exit){
            this.dispose();
        }
    }
}
