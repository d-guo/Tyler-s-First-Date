import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class Train extends Vehicle {
	public static double trainSpawnFreq = 0.2;
	public Train() {
		length = 1000;
		speed = 20;
		spawnFreq = 0.2;
	}

	public String toString() {
		return "Train";
	}
}