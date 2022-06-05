package views;

import controller.GameController;
import engine.Game;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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
    private JLabel Info;
    private JButton Exit;
    private ActionListener Exitlistener;

    public GameOverScreen(GameController gameController) {
    	try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("Marvel Ultimate War.jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
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
        Info.setHorizontalAlignment(JLabel.CENTER);
        Info.setFont(new Font("Chiller",Font.BOLD,200));
        Info.setForeground(Color.WHITE);
        Exit = new JButton("Exit Marvel: Ultimate War");
        Exit.addActionListener(this);
        Exit.setPreferredSize(new Dimension (100,50));


        this.add(Exit, BorderLayout.SOUTH);
        this.add(Info, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Exit){
            this.dispose();
        }
    }
}
