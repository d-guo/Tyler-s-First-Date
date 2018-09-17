import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import javax.swing.*;

public class tutorialScreen {
	public Rectangle backButton = new Rectangle(0, 0, 70, 30);

	public tutorialScreen() {

	}

	public void render(Graphics g) {	//never gonna give you up
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.PINK);
		g2d.fillRect(0, 0, 900, 1000);

		Font font = new Font("Baskerville", Font.BOLD, 50);
		g2d.setFont(font);
		g2d.setColor(Color.BLACK);
		g2d.drawString("Tyler's First Date Tutorial", 140, 130);

		g2d.fill(backButton);

		font = new Font("Baskerville", Font.BOLD, 20);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		g2d.drawString("back", 10, 20);


		font = new Font("Baskerville", Font.BOLD, 30);
		g2d.setFont(font);
		g2d.setColor(Color.BLACK);
		g2d.drawString("You are going to: ", 325, 325);
		g2d.drawString("http://tinyurl.com/4poyc6x", 260, 400);
	}
}