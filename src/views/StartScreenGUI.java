package views;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class StartScreenGUI extends JComponent {

    private Clip clip;
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

        File audioFile = new File("The Avengers Theme.wav").getAbsoluteFile();
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
        clip.start();


        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JLabel label = new JLabel();
        ImageIcon image = new ImageIcon("THH-LOGO.gif");
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        label.setIcon(image);
        f.setBackground(Color.BLACK);
        f.setContentPane(label);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //f.add(label, SwingConstants.CENTER);
        f.setVisible(true);
        TimeUnit.SECONDS.sleep(21);
        f.dispose();
        new CharacterSelectionView(this);

    }

    public Clip getClip() {
        return this.clip;
    }
}


