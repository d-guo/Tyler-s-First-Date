import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class menu {
	public Rectangle playButton = new Rectangle(300, 250, 300, 125);
	public Rectangle helpButton = new Rectangle(300, 450, 300, 125);
	public Rectangle exitButton = new Rectangle(300, 650, 300, 125);

	BufferedImage background;

	public menu() {
		try {
			background = ImageIO.read(new File("background.jpg"));
		}
		catch(IOException e) {
		}
	}

	public void render(Graphics g) {	//menu screen shows start button, tutorial button, exit button
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.PINK);
		g2d.drawImage(background, 0, 0, null);

		Font font = new Font("Baskerville", Font.BOLD, 80);
		g2d.setFont(font);
		g2d.setColor(Color.BLACK);
		g2d.drawString("Tyler's First Date", 140, 130);


		g2d.fill(playButton);
		g2d.fill(helpButton);
		g2d.fill(exitButton);


		font = new Font("Baskerville", Font.BOLD, 40);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);

		g2d.drawString("START", 380, 325);
		g2d.drawString("TUTORIAL", 350, 525);
		g2d.drawString("EXIT", 400, 725);
	}
}