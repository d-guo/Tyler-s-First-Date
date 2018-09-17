import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class deathScreen {
	public Rectangle retryButton = new Rectangle(300, 350, 300, 125);;

	BufferedImage background;

	public deathScreen() {
		try {
			background = ImageIO.read(new File("background.jpg"));
		}
		catch(IOException e) {
		}
	}

	public void render(Graphics g) {	//dead screen shows final score, cause of death, and retry button which goes back to the menu
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.PINK);
		g2d.drawImage(background, 0, 0, null);

		Font font = new Font("Baskerville", Font.BOLD, 50);
		g2d.setFont(font);
		g2d.setColor(Color.BLACK);
		g2d.drawString("Oof, you didn't make it", 180, 130);
		g2d.drawString("Cause of death: " + screen.causeOfDeath, 200, 200);

		g2d.fill(retryButton);

		g2d.setColor(Color.RED);
		g2d.drawString("FINAL SCORE: " + screen.score, 250, 300);

		font = new Font("Baskerville", Font.BOLD, 50);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);

		g2d.drawString("Try Again", 335, 425);
	}
}