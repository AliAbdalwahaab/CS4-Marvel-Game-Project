package views;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class StartScreenGUI extends JComponent {
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
        JLabel label = new JLabel();
        ImageIcon image = new ImageIcon("THH-LOGO.gif");
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        label.setIcon(image);
        f.setBackground(Color.BLACK);
        f.setContentPane(label);

        //f.add(label, SwingConstants.CENTER);
        f.setVisible(true);
        TimeUnit.SECONDS.sleep(21);

        f.dispose();
        new CharacterSelectionView();

    }
}


