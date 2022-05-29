package views;

import engine.Game;
import model.world.Champion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LeaderSelectionGUI extends JFrame implements ActionListener {

    JPanel centerPanel;
    ArrayList<JButton> btns;

    public LeaderSelectionGUI(Game thisGame) {

        this.setTitle("Selector");
        this.setBounds(0,0,1920,1080);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0,5));

        btns = new ArrayList<JButton>();

        for (Champion c: thisGame.getAvailableChampions()) {
            JButton b = new JButton();
            b.setText(c.getName());
            b.addActionListener(this);
            centerPanel.add(b);
            btns.add(b);
            this.revalidate();
            this.repaint();
        }

        this.add(centerPanel,BorderLayout.CENTER);
        centerPanel.setVisible(true);
        this.revalidate();
        this.repaint();

    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    public static void main(String[] args) {
        //new LeaderSelectionGUI(thisGame);
    }
}
