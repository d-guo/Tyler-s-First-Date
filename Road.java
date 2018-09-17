import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class Road extends Lane {
	int speedLim;
	public Road() {
		try {
			image = ImageIO.read(new File("road.jpg"));
		}
		catch(IOException e) {
		}
		color = Color.lightGray;
		direction = (int) Math.pow(-1, (int) (Math.random() * 2));
		obsList = new ArrayList<Obstacle>();
		speedLim = (int) ((Math.random() * 2) / 1) * 1 + 2;
		killType = "Car";
		isRoad = true;
	}

	public void spawnObstacle() {
		obsList.add(new Car());
		obsList.get(obsList.size() - 1).speed = speedLim;
	}

	public boolean kills(int lox, int loy) {
		if(collided(lox, loy)) {
			return true;
		}
		return false;
	}
	
	public void setColor(Color c) {
		color = c;
	}

	public String toString() {
		return "road";
	}
}