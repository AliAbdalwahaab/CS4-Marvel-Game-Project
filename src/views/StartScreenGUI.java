package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import views.CharacterSelectionView.*;

public class StartScreenGUI extends JFrame {
    JPanel contentPane;
    JLabel imageLabel = new JLabel();
    JLabel headerLabel = new JLabel();


    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        JFrame f = new JFrame();
        f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        Icon icon = new ImageIcon("THH-LOGO_F2.gif");
        JLabel label = new JLabel();

        label.setIcon(icon);
        f.add(label);
        f.setVisible(true);
        TimeUnit.SECONDS.sleep(22);
        f.dispose();
        new CharacterSelectionView();

    }

}

