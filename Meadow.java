import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class Meadow extends Lane {
	public Meadow() {
		try {
			image = ImageIO.read(new File("meadow.jpg"));
		}
		catch(IOException e) {
		}
		color = Color.green;
		obsList = new ArrayList<Obstacle>();
		isMeadow = true;
	}

	public void spawnObstacle() {
		obsList.add(new Tree());
	}

	public boolean kills(int lox, int loy) {
		return false;
	}

	public void setColor(Color c) {
		color = c;
	}

	public String toString() {
		return "meadow";
	}
}