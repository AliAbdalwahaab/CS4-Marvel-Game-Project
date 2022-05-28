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

public class StartScreenGUI extends JFrame {
	JPanel contentPane;
    JLabel imageLabel = new JLabel();
    JLabel headerLabel = new JLabel();

    
	public static void main(String[] args) throws MalformedURLException {
		SwingUtilities.invokeLater(new Runnable() {
		      @Override
		      public void run() {
		        JFrame frame = new JFrame();
		        frame.add(new ImagePanel());

		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setSize(400, 400);
		        frame.setVisible(true);
		      }
	   });
	
	}
	
}

class ImagePanel extends JPanel {

	  Image image;

	  public ImagePanel() {
	    image = Toolkit.getDefaultToolkit().createImage("C:/Users/omar.nour/Downloads/8f5.gif"); // 
	  }

	  @Override
	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (image != null) {
	      g.drawImage(image, 0, 0, this);
	    }
	  }

	}
