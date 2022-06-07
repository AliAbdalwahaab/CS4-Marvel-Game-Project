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

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

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
    	    ImageIcon img = new ImageIcon(ImageIO.read(new File("Marvel Background.png")));
            int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            img.setImage(img.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
			this.setContentPane(new JLabel(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
	File audioFile = new File("The Avengers Theme Win Screen.wav").getAbsoluteFile();
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(audioFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Clip clip = null;
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
        clip.start();
    	
    	this.gameController = gameController;
        this.setLayout(new BorderLayout());
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setPreferredSize(new Dimension(width, height));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        JLabel ll = new JLabel("" + gameController.getWinner() + " is the winner!");
        //ll.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        Info = ll;

        Info.setOpaque(false);
        Info.setHorizontalAlignment(JLabel.CENTER);
        Info.setFont(new Font("Arial",Font.BOLD,70));
        Info.setForeground(Color.RED);
        //Info.setBackground(Color.DARK_GRAY);

        Exit = new JButton("Exit Marvel: Ultimate War");
        Exit.setFont(new Font("Arial", Font.ITALIC,40));
        Exit.addActionListener(this);
        Exit.setPreferredSize(new Dimension (100,50));
        Exit.setForeground(Color.WHITE);
        Exit.setBackground(Color.DARK_GRAY);


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

    public static void main(String[] args) {
        GameController gm = new GameController(new Game(new Player("1"), new Player("2")));
        gm.setWinner(new Player("KARINGE"));
        new GameOverScreen(gm);
    }
}
