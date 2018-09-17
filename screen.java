import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.net.URI;
import java.util.ArrayList;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class screen extends JPanel implements ActionListener, KeyListener, MouseListener {
	public ArrayList<Lane> lanes;	//Lane includes Tracks, Meadow, Road, River
	public boolean isInitial;	//used as condition to spawn first 10 lanes on screen

	public int lox;	//location of character (lox, loy)
	public int loy;
	public int vex;	//velocity of character <vex,vey>
	public int vey;
	public int screenSpeed;

	public Timer t = new Timer(10, this);	//timer
	public int ticks;	//keeps track of times program has gone through actionPerformed

	public boolean gameOver;	//true when player loses
	public static int score;
	public static String causeOfDeath;

	BufferedImage tree;
	BufferedImage train;
	BufferedImage trainn;
	BufferedImage car1;
	BufferedImage car2;
	BufferedImage car1n;
	BufferedImage car2n;
	BufferedImage log1;
	BufferedImage log2;
	BufferedImage log3;
	BufferedImage log4;
	BufferedImage log5;
	BufferedImage tyler;

	public static STATE state = STATE.MENU;
	public menu startScreen;
	public tutorialScreen tut;
	public deathScreen end;

	public static enum STATE{
		MENU,
		GAME,
		TUTORIAL,
		DEAD
	};

	public screen(){
		super();	//creates JPanel
		startScreen = new menu();
		tut = new tutorialScreen();
		end = new deathScreen();
		setFocusable(true);	//allow input to be taken from keyboard
		setFocusTraversalKeysEnabled(false);
		addKeyListener(this);
		addMouseListener(this);
		start();
		score = 0;
	}

	public void start() {
		lanes = new ArrayList<Lane>();	//initialize ArrayList lanes
		lox = 425;	//start location of character set at center of third lane from bottoom
		loy = 725;
		isInitial = true;	//duh
		gameOver = false;
		for(int i = 0; i < 11; i++) {	//create first 11 lanes (adds to ArrayList lanes)
			if(i == 0 || i == 1 || i == 2 || i == 3 || i == 4) {
				lanes.add(new Meadow());	//start lane must be a meadow
				if(i == 0) {
					lanes.get(lanes.size() - 1).vertDist = 900;
				}
				else {
					lanes.get(lanes.size() - 1).vertDist = lanes.get(lanes.size() - 2).vertDist - 100;
				}

				for(int j = 0; j < 10; j++) {
					if(Math.random() > Tree.spawnFreq) {
						continue;
					}
					lanes.get(lanes.size() - 1).spawnObstacle();
					lanes.get(lanes.size() - 1).obsList.get(lanes.get(lanes.size() - 1).obsList.size() - 1).position = (int) ((Math.random() * 1000) / 100) * 100;
				}
				lanes.get(lanes.size() - 1).isInitial = false;
			}
			else {
				spawnLane();
			}
		}
		try {
			tree = ImageIO.read(new File("tree.png"));
			car1 = ImageIO.read(new File("car1.png"));
			car2 = ImageIO.read(new File("car2.png"));
			car1n = ImageIO.read(new File("car1n.png"));
			car2n = ImageIO.read(new File("car2n.png"));
			train = ImageIO.read(new File("train.png"));
			trainn = ImageIO.read(new File("trainn.png"));
			log1 = ImageIO.read(new File("log1.jpg"));
			log2 = ImageIO.read(new File("log2.jpg"));
			log3 = ImageIO.read(new File("log3.jpg"));
			log4 = ImageIO.read(new File("log4.jpg"));
			log5 = ImageIO.read(new File("log5.jpg"));
			tyler = ImageIO.read(new File("tyler.png"));
		}
		catch(IOException e) {
			System.out.println("??");
		}
		screenSpeed = 1;
		t.start();	//starts Timer
		ticks = 0;
	}

	public void paintCharacter(Graphics g) {	//paints black 50x50 rectangle at lox, loy
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.black);
		g2d.drawImage(tyler, lox, loy, null);
	}

	public void spawnLane() {	//generates a random lane to ArrayList lanes along with obstacles (if any)
		double p = Math.random();

		if(p < 0.25) {	//Tracks: trains not generated since we only want one (at most) per track at a random time
			lanes.add(new Tracks());
			lanes.get(lanes.size() - 1).vertDist = lanes.get(lanes.size() - 2).vertDist - 100;
			/*lanes.get(lanes.size() - 1).spawnObstacle();
			lanes.get(lanes.size() - 1).obsList.get(lanes.get(lanes.size() - 1).obsList.size() - 1).position = (int) ((Math.random() * 1200) / 100) * 100 - 100;
			lanes.get(lanes.size() - 1).isInitial = false;*/
			return;
		}
		if(p < 0.5) {	//Road: car is generated with length of 100 or 200, speed of 2 or 3 per tick
			lanes.add(new Road());
			lanes.get(lanes.size() - 1).vertDist = lanes.get(lanes.size() - 2).vertDist - 100;
			lanes.get(lanes.size() - 1).spawnObstacle();
			lanes.get(lanes.size() - 1).obsList.get(lanes.get(lanes.size() - 1).obsList.size() - 1).position = (int) ((Math.random() * 1200) / 100) * 100 - 100;
			lanes.get(lanes.size() - 1).isInitial = false;
			return;
		}
		if(p < 0.75) {	//River: log is generated with length 100, 200, 300, 400, 500 speed of 2 or 3 per tick
			lanes.add(new River());
			lanes.get(lanes.size() - 1).vertDist = lanes.get(lanes.size() - 2).vertDist - 100;
			lanes.get(lanes.size() - 1).spawnObstacle();
			lanes.get(lanes.size() - 1).obsList.get(lanes.get(lanes.size() - 1).obsList.size() - 1).position = (int) ((Math.random() * 1200) / 100) * 100 - 100;
			lanes.get(lanes.size() - 1).isInitial = false;
			return;
		}
		lanes.add(new Meadow());	//Meadow: spawns trees at random
		lanes.get(lanes.size() - 1).vertDist = lanes.get(lanes.size() - 2).vertDist - 100;
		for(int i = 0; i < 10; i++) {
			if(Math.random() > Tree.spawnFreq) {
				continue;
			}
			lanes.get(lanes.size() - 1).spawnObstacle();
			lanes.get(lanes.size() - 1).obsList.get(lanes.get(lanes.size() - 1).obsList.size() - 1).position = (int) ((Math.random() * 1000) / 100) * 100;
		}
		lanes.get(lanes.size() - 1).isInitial = false;

		return;
	}

	public void paintLane(Graphics g) {	//paints Lane
		Graphics2D g2d = (Graphics2D) g;

		if(isInitial) {	//if is 0th tick, paint first 11 lanes
			for (int i = 0; i < 11; i++) {
				g2d.setColor(lanes.get(i).color);
				//lanes.get(i).setVertDist(lanes.get(i).vertDist - i * 100 + 900);
				//g2d.fillRect(0, lanes.get(i).vertDist, 900, 100);
				g2d.drawImage(lanes.get(i).image, 0, lanes.get(i).vertDist, null);
			}
		}
		else {	//paints Lane according to vertDist
			for(int i = 0; i < lanes.size(); i++) {
				g2d.setColor(lanes.get(i).color);
				//g2d.fillRect(0, lanes.get(i).vertDist, 900, 100);
				g2d.drawImage(lanes.get(i).image, 0, lanes.get(i).vertDist, null);
			}
		}
	}

	public int currentLane() {	//returns index of current lane in ArrayList lanes
		for(int i = 0; i < lanes.size(); i++) {
			if(lanes.get(i).vertDist + 25 == loy) {
				return i;
			}
		}
		return 0;
	}

	public void addObstacleToLane() {	//generates random obstacle (if chance permits it) at left or right of the screen
		double p;
		for(int i = 0; i < lanes.size(); i++) {
			p = Math.random();
			if(lanes.get(i).isMeadow) { //meadow doesn't spawn any obstacles since the trees are created at the same time as the meadows
				continue;
			}
			if(lanes.get(i).isTracks && p <= Train.trainSpawnFreq) {	//spawn train according to spawn frequency
				if(((Tracks)(lanes.get(i))).hasSpawned) {	//if a train has already been spawned on this track before, don't spawn another one
					continue;
				}
				else {	//otherwise, start spawning a train
					((Tracks)(lanes.get(i))).startSpawnTrain();
					continue;
				}
			}
			else if(lanes.get(i).isTracks) {
				continue;
			}
			if(p > lanes.get(i).obsList.get(lanes.get(i).obsList.size() - 1).spawnFreq) {	//spawn obstacles according to spawn frequencies
				continue;
			}
			else {
				lanes.get(i).spawnObstacle();
			}
			if(lanes.get(i).direction == 1) {	//if lanes goes to the right, spawn left of obstacle at -300
				lanes.get(i).obsList.get(lanes.get(i).obsList.size() - 1).position = -300;
				if(lanes.get(i).obsList.get(lanes.get(i).obsList.size() - 2).position <= lanes.get(i).obsList.get(lanes.get(i).obsList.size() - 1).length - 300) {
					lanes.get(i).obsList.remove(lanes.get(i).obsList.size() - 1);
				}
			}
			else {	//if lane goes to the left, spawn right of obstacle at 1200
				lanes.get(i).obsList.get(lanes.get(i).obsList.size() - 1).position = 1200 - lanes.get(i).obsList.get(lanes.get(i).obsList.size() - 1).length;
				if(lanes.get(i).obsList.get(lanes.get(i).obsList.size() - 2).position + lanes.get(i).obsList.get(lanes.get(i).obsList.size() - 2).length >= 1200) {
					lanes.get(i).obsList.remove(lanes.get(i).obsList.size() - 1);
				}
			}
		}
	}

	public void paintObstacles(Graphics g) {	//paints obstacles according to properties
		Graphics2D g2d = (Graphics2D) g;

		for(int i = 0; i < lanes.size(); i++) {
			for(int j = 0; j < lanes.get(i).obsList.size(); j++) {
				g2d.setColor(lanes.get(i).obsList.get(j).color);
				if(lanes.get(i).isMeadow) {
					g2d.drawImage(tree, lanes.get(i).obsList.get(j).position, lanes.get(i).vertDist, null);
				}
				if(lanes.get(i).isRoad) {
					if(lanes.get(i).direction == 1) {
						if(lanes.get(i).obsList.get(j).length == 100) {
							g2d.drawImage(car1, lanes.get(i).obsList.get(j).position, lanes.get(i).vertDist, null);
						}
						else if(lanes.get(i).obsList.get(j).length == 200) {
							g2d.drawImage(car2, lanes.get(i).obsList.get(j).position, lanes.get(i).vertDist, null);
						}
					}
					else if(lanes.get(i).direction == -1) {
						if(lanes.get(i).obsList.get(j).length == 100) {
							g2d.drawImage(car1n, lanes.get(i).obsList.get(j).position, lanes.get(i).vertDist, null);
						}
						else if(lanes.get(i).obsList.get(j).length == 200) {
							g2d.drawImage(car2n, lanes.get(i).obsList.get(j).position, lanes.get(i).vertDist, null);
						}
					}
				}
				if(lanes.get(i).isTracks) {
					if(lanes.get(i).direction == 1) {
						g2d.drawImage(train, lanes.get(i).obsList.get(j).position, lanes.get(i).vertDist, null);
					}
					else if(lanes.get(i).direction == -1) {
						g2d.drawImage(trainn, lanes.get(i).obsList.get(j).position, lanes.get(i).vertDist, null);
					}
				}
				if(lanes.get(i).isRiver) {
					if(lanes.get(i).obsList.get(j).length == 100) {
						g2d.drawImage(log1, lanes.get(i).obsList.get(j).position, lanes.get(i).vertDist, null);
					}
					else if(lanes.get(i).obsList.get(j).length == 200) {
						g2d.drawImage(log2, lanes.get(i).obsList.get(j).position, lanes.get(i).vertDist, null);
					}
					else if(lanes.get(i).obsList.get(j).length == 300) {
						g2d.drawImage(log3, lanes.get(i).obsList.get(j).position, lanes.get(i).vertDist, null);
					}
					else if(lanes.get(i).obsList.get(j).length == 400) {
						g2d.drawImage(log4, lanes.get(i).obsList.get(j).position, lanes.get(i).vertDist, null);
					}
					else if(lanes.get(i).obsList.get(j).length == 500) {
						g2d.drawImage(log5, lanes.get(i).obsList.get(j).position, lanes.get(i).vertDist, null);
					}
				}
			}
		}
	}

	public void paintWarning(Graphics g, Tracks tr) {	//paints red in track lane to warn of incoming train
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.RED.brighter().brighter());
		g2d.fillRect(0, tr.vertDist, 900, 100);
	}

	public void paintComponent(Graphics g) {	//all paint methods in here
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);

		if(state == STATE.GAME) {
			paintLane(g);	//paint lanes first
			isInitial = false;	//not initial (duh)

			paintObstacles(g);	//paint obstacles
			
			paintCharacter(g);	//paint character

			for(int i = 0; i < lanes.size(); i++) {	//if the lane is a track and it will spawn a train soon, paint flashing warning signals
				if(!lanes.get(i).isTracks) {
					continue;
				}
				if(((Tracks)lanes.get(i)).willSpawn && !((Tracks)lanes.get(i)).hasSpawned && ((Tracks)lanes.get(i)).timer % 5 == 0) {
					paintWarning(g, ((Tracks)lanes.get(i)));
				}
			}

			g2d.setColor(Color.BLACK);	//paint score onto board
			Font font = new Font("Baskerville", Font.BOLD, 50);
  			g2d.setFont(font);
			g2d.drawString("Score: " + score, 10, 75);
		}
		else if(state == STATE.MENU) {	//paint menu
			startScreen.render(g);
		}
		else if(state == STATE.TUTORIAL) {	//paint tutorial
			tut.render(g);
		}
		else if(state == STATE.DEAD) {	//paint dead screen
			end.render(g);
		}

	}

	public void actionPerformed(ActionEvent e) {	//all movement in here
		if(state == STATE.GAME) {
			if(loy > 300 && loy < 600) {	//speed of screen moving down changes depending on position of player (higher on screen = faster screen speed)
				screenSpeed = 3;
			}
			else if(loy <= 300) {
				screenSpeed = 8;
			}
			else {
				screenSpeed = 1;
			}

			ticks++;	//keep track of times gone through

			if(lox < -50 || lox > 900 || loy > 1000) {	//if character goes off screen, reset game and go to dead screen
				causeOfDeath = "Off Screen";
				gameOver = true;
				state = STATE.DEAD;
				reset();
				return;
			}
			for(int i = 0; i < lanes.size(); i++) {	//if character dies, reset game and go to dead screen
				if(lanes.get(i).kills(lox, loy)) {
					causeOfDeath = lanes.get(i).killType;
					gameOver = true;
					state = STATE.DEAD;
					reset();
					return;
				}
			}

			if(lanes.get(0).vertDist >= 1000) {	//if bottom lane goes off screen, remove it and make a new lane at top of screen
				lanes.remove(0);
				spawnLane();
			}

			for(int i = 0; i < lanes.size(); i++) {	//screen moves down at previously specified speed
				lanes.get(i).setVertDist(lanes.get(i).vertDist + screenSpeed);
			}

			for(int i = 0; i < lanes.size(); i++) {	//move obstacles according to their speeds
				for(int j = 0; j < lanes.get(i).obsList.size(); j++) {
					lanes.get(i).obsList.get(j).position = lanes.get(i).obsList.get(j).position + lanes.get(i).obsList.get(j).speed * lanes.get(i).direction;
				}
			}

			loy = loy + screenSpeed;	//move character down at same speed as the screen
			int loyp = loy;
			int loxp = lox;
			loy = loy + vey;	//move character according to velocities (set by key inputs)
			lox = lox + vex;

			if(ticks % 100 == 0) {	//every 100 ticks, there is a possibility of generating a new osbtacle in each lane (possibility depends on each obstacle)
				addObstacleToLane();
			}
			for(int i = 0; i < lanes.size(); i++) {		//if lane is a track and a train is incoming
				if(!lanes.get(i).isTracks) {
					continue;
				}
				if(((Tracks)lanes.get(i)).willSpawn && !((Tracks)lanes.get(i)).hasSpawned) {
					((Tracks)lanes.get(i)).timer--;	//decrease timer by one
					if(((Tracks)lanes.get(i)).timer == 0) {
						((Tracks)lanes.get(i)).spawnObstacle();
						if(lanes.get(i).direction == 1) {	//spawn train at location depending on the lane's direction
							lanes.get(i).obsList.get(0).position = -1000;
						}
						if(lanes.get(i).direction == -1) {
							lanes.get(i).obsList.get(0).position = 1000;
						}

					}
					if(((Tracks)lanes.get(i)).timer <= 0) {	//once train has been spawned, don't spawn another train on the same track
						((Tracks)lanes.get(i)).willSpawn = false;
						((Tracks)lanes.get(i)).hasSpawned = true;
					}
				}
			}
			if(lanes.get(currentLane()).isRiver) {	//move character with the river if it is on it
				lox = lox + lanes.get(currentLane()).obsList.get(lanes.get(currentLane()).obsList.size() - 1).speed * lanes.get(currentLane()).direction;
			}

			if(lanes.get(currentLane()).isMeadow && lanes.get(currentLane()).collided(lox, loy) || lox < 0 || lox > 900 || loy > 1000 || loy < -50) {	//don't let character move onto bushes or offscreen
				loy = loy - vey;
				lox = lox - vex;
			}

			if(loy == loyp + 100) {	//keep track of score
				score--;
			}
			if(loy == loyp - 100) {
				score++;
			}

			if(!lanes.get(currentLane()).isRiver) {	//unless character is on the river, fit it to the nearest 100 x 100 block
				fitCharacter();
			}

			vey = 0;	//set speed of character back to 0
			vex = 0;

			repaint();	//paint changes
		}
		else if(state == STATE.MENU) {
			score = 0;
			causeOfDeath = "";
			repaint();
		}
		else if(state == STATE.TUTORIAL) {
			repaint();
		}
		else if(state == STATE.DEAD) {
			repaint();
		}
	}

	public void fitCharacter() {	//aligns character to grid by rounding
		int loxp = (int) (lox / 100) * 100;
		lox = lox % 100;
		if(lox < 75) {
			lox = loxp + 25;
			return;
		}
		else {
			lox = loxp + 125;
			return;
		}
	}

	public void keyReleased(KeyEvent e) {
		//not invoked
	}

	public void keyPressed(KeyEvent e) {	//input from arrows
		int keyCode = e.getKeyCode();
			if(state == STATE.GAME) {
				if (keyCode == KeyEvent.VK_UP) {
				vey = -100;
			}
			if (keyCode == KeyEvent.VK_DOWN) {
				vey = 100;
			}
			if (keyCode == KeyEvent.VK_RIGHT) {
				vex = 100;
			}
			if (keyCode == KeyEvent.VK_LEFT) {
				vex = -100;
			}

			if(keyCode == KeyEvent.VK_SLASH) {
				score += 20;
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		//not invoked
	}

	public void mousePressed(MouseEvent e) {	//allows player to use mouse to click on buttons
		int mx = e.getX();
		int my = e.getY();

		if(state == STATE.MENU) {
			if(mx >= 300 && mx <= 600 && my >= 250 && my <= 375) {	//START
				screen.state = STATE.GAME;
			}
			if(mx >= 300 && mx <= 600 && my >= 450 && my <= 575) {	//TUTORIAL
				try {	//open link
					Desktop d = Desktop.getDesktop();
					d.browse(new URI("http://tinyurl.com/4poyc6x"));
				}
				catch(Exception ee) {
					System.out.println("current platform not supported");
				}
				screen.state = STATE.TUTORIAL;
			}
			if(mx >= 300 && mx <= 600 && my >= 650 && my <= 775) {	//EXIT
				System.exit(1);
			}
		}
		else if(state == STATE.TUTORIAL) {
			if(mx >= 0 && mx <= 70 && my >= 0 && my <= 30) {
				screen.state = STATE.MENU;
			}
		}
		else if(state == STATE.DEAD) {
			if(mx >= 300 && mx <= 600 && my >= 350 && my <= 475) {
				screen.state = STATE.MENU;
			}
		}
	}
	public void mouseReleased(MouseEvent e) {
		//not invoked
	}
	public void mouseExited(MouseEvent e) {
		//not invoked
	}
	public void mouseEntered(MouseEvent e) {
		//not invoked
	}
	public void mouseClicked(MouseEvent e) {
		//not invoked
	}
	public void reset() {
		start();
	}
}