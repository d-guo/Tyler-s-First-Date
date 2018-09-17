import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public abstract class Obstacle {	//stores position on obstacle in lane (x coordinate), speed, length, spawn frequency of obstacle
	public int position;

	public Color color;
	public int speed;
	public int length;
	public int width = 100;
	public double spawnFreq;
}