import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class Tracks extends Lane {
	public int trackDist = 20;
	public int timer = 100;
	public boolean hasSpawned = false;
	public boolean willSpawn = false;

	public Tracks() {
		try {
			image = ImageIO.read(new File("tracks.jpg"));
		}
		catch(IOException e) {
		}
		color = Color.darkGray;
		direction = (int) Math.pow(-1, (int) (Math.random() * 2));
		obsList = new ArrayList<Obstacle>();
		killType = "Train";
		isTracks = true;
	}

	public void startSpawnTrain() {
		willSpawn = true;
	}
	public void spawnObstacle() {
		obsList.add(new Train());
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
		return "tracks";
	}
}