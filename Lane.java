import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public abstract class Lane {	//contains y coordinate, color, type of obstacle, arraylist of obstacles in lane, direction of lane
	public int vertDist;
	public int width = 200;
	public Color color;
	public String killType;
	public ArrayList <Obstacle> obsList;
	public int direction;
	public BufferedImage image;

	public boolean isInitial = true;
	public boolean isRoad = false;
	public boolean isTracks = false;
	public boolean isMeadow = false;
	public boolean isRiver = false;

	public boolean collided(int lox, int loy) {	//determines whether or not character has collided with obstacles
		for(int i = 0; i < obsList.size(); i++) {
			if(loy == vertDist + 25 && lox + 50 >= obsList.get(i).position && lox <= obsList.get(i).position + obsList.get(i).length) {
				return true;
			}
		}
		return false;
	}

	public void setVertDist(int d) {	//change y coordinate
		vertDist = d;
	}

	public abstract void spawnObstacle();	//create an obstacle

	public abstract boolean kills(int lox, int loy);	//determines whether or not collision will kill

	public abstract void setColor(Color c);	//change color
}