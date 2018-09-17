import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class Tree extends Obstacle {
	public static double spawnFreq = 0.2;
	public Tree() {
		color = Color.GREEN.darker();
		speed = 0;
		length = 100;
	}

	public String toString() {
		return "Tree";
	}
}