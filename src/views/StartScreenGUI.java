package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import views.CharacterSelectionView.*;

public class StartScreenGUI extends JComponent implements ActionListener {
    JPanel contentPane;
    JLabel imageLabel = new JLabel();
    JLabel headerLabel = new JLabel();
    JFrame playPhase;
    JButton play;


    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        new StartScreenGUI();
    }
    public StartScreenGUI() throws MalformedURLException, InterruptedException {
        JFrame f = new JFrame();

        f.setLayout(new BorderLayout());
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Icon icon = new ImageIcon("THH-LOGO.gif");
        JLabel label = new JLabel();
        label.setIcon(icon);
        f.setContentPane(label);
        //f.add(label, BorderLayout.CENTER);
        f.setBackground(Color.BLACK);
        f.setVisible(true);
        TimeUnit.SECONDS.sleep(22);

        f.setVisible(false);
        JFrame playPhase = new JFrame();
        playPhase.setLayout(new BorderLayout());
        playPhase.setVisible(true);
        play = new JButton("Play");
        play.addActionListener(this);

        playPhase.add(play, BorderLayout.PAGE_END);
        playPhase.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play) {
            playPhase.dispose();
            new CharacterSelectionView();
        }
    }
}

