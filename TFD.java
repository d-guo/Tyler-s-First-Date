import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class TFD {
	public static void main(String[] args){
		JFrame window = new JFrame("Tyler's First Date");

		screen scr = new screen();
		window.add(scr);

		window.setSize(900, 1000);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}