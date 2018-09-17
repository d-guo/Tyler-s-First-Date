import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class Car extends Vehicle {
	public Car() {
		length = (int) ((Math.random() * 200) / 100) * 100 + 100;
		spawnFreq = 0.2;
	}

	public String toString() {
		return "Car";
	}
}