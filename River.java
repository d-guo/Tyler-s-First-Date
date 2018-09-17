import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class River extends Lane {
	int speedLim;
	public River() {
		try {
			image = ImageIO.read(new File("river.jpg"));
		}
		catch(IOException e) {
		}
		color = Color.cyan;
		direction = (int) Math.pow(-1, (int) (Math.random() * 2));
		obsList = new ArrayList<Obstacle>();
		speedLim = (int) ((Math.random() * 2) / 1) * 1 + 2;
		killType = "River";
		isRiver = true;
	}

	public void spawnObstacle() {
		obsList.add(new Log());
		obsList.get(obsList.size() - 1).speed = speedLim;
	}

	public boolean kills(int lox, int loy) {
		if(loy == vertDist + 25 && !collided(lox, loy)) {
			return true;
		}
		return false;
	}

	public void setColor(Color c){
		color = c;
	}

	public String toString() {
		return "river";
	}
}