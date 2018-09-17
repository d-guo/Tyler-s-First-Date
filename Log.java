import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class Log extends Obstacle {
	public Log() {
		color = Color.ORANGE;
		length = (int) ((Math.random() * 500) / 100) * 100 + 100;
		spawnFreq = 0.8;
	}

	public String toString() {
		return "Log";
	}
}