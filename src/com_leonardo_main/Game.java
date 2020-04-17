package com_leonardo_main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import com_leonardo_entities.Enemy;
import com_leonardo_entities.Entity;
import com_leonardo_entities.Player;
import com_leonardo_entities.Shoot;
import com_leonardo_graficos.Spritesheet;
import com_leonardo_graficos.UI;
//import com_leonardo_world.Camera;
import com_leonardo_world.World;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;

	private BufferedImage image;

	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<Shoot> bullets;
	public static Spritesheet spritesheet;
	public static World world;

	public static Player player;
	public UI ui;
	// public static int countToReborn = 500;

	public static Random rand;

	private int LEVEL = 1, MAX_LEVEL = 2;

	public static String gameState = "MENU";
	private boolean gameoverMessage = true;
	private int framesGameoverMessage = 0;

	public boolean restartGame = false;

	public Menu menu;

	public Game() {
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		// Inicio de Objetos
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<Shoot>();

		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
		entities.add(player);

		world = new World("/level1.png");

		menu = new Menu();

	}

	public void initFrame() {
		frame = new JFrame("Shoot the Zumbis");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}

	public void tick() {
		if (gameState.equals("VIVO")) {
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}

			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).tick();
			}

			if (enemies.size() == 0) {
				// Proximo Level
				LEVEL++;
				if (LEVEL > MAX_LEVEL) {
					LEVEL = 0;
				}

				String newWorld = "Level" + LEVEL + ".png";
				World.levelingGame(newWorld);
			}
		} else if (gameState.equals("GAMEOVER")) {
			this.framesGameoverMessage++;
			if (this.framesGameoverMessage == 25) {
				this.framesGameoverMessage = 0;
				if (this.gameoverMessage) {
					this.gameoverMessage = false;

				} else {
					this.gameoverMessage = true;
				}
			}

			if (restartGame) {
				this.restartGame = false;
				gameState = "VIVO";
				LEVEL = 1;
				String newWorld = "level" + LEVEL + ".png";
				World.levelingGame(newWorld);
			}
		} else if (gameState.equals("MENU")) {
			menu.tick();
		}

	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();

		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Renderização do Jogo
		// Graphics2D g2 = (Graphics2D) g;
		// g2.rotate(Math.toRadians(45), 90 + 8, 90 + 8);

		world.render(g);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}

		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}

		ui.render(g);
		// ** */
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		// if (!World.inDeathWorld) {
		g.setColor(new Color(0, 0, 0));
		g.fillRect(345, 5, 100, 20);
		g.setFont(new Font("arial", Font.BOLD, 17));
		g.setColor(Color.white);
		g.drawString("Munição: " + Game.player.AMMO, 350, 20);

		// } else {
		// g.setColor(new Color(0, 0, 0));
		// g.fillRect(395 - Camera.x, 385 - Camera.y, 150, 20);
		// g.setFont(new Font("arial", Font.BOLD, 17));
		// g.setColor(Color.white);
		// g.drawString("Novo Jogo em: " + countToReborn-- / 100, 400 - Camera.x, 400 -
		// Camera.y);
		// if (countToReborn == 0) {
		// World.inDeathWorld = false;
		// countToReborn = 500;
		// World.levelingGame("level1.png");
		// }
		// }

		if (gameState.equals("GAMEOVER")) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.setColor(Color.white);
			g.drawString("GAME OVER", (WIDTH * SCALE) / 2 - 80, (HEIGHT * SCALE) / 2);
			if (gameoverMessage)
				g.drawString("-> Pressione Enter para Reiniciar <-", (WIDTH * SCALE) / 2 - 260,
						(HEIGHT * SCALE) / 2 + 40);
		} else if (gameState.equals("MENU")) {
			menu.render(g);
		}
		bs.show();

	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}

		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			if (gameState.equals("MENU")) {
				menu.up = true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;

			if (gameState.equals("MENU")) {
				menu.down = true;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.isShooting = true;
		}
		if (gameState.equals("GAMEOVER")) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				restartGame = true;
			}
		}

		else if (gameState.equals("MENU")) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER && menu.options[menu.currentOption] == "Novo Jogo") {
				gameState = "VIVO";
				menu.pause = false;
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER && menu.options[menu.currentOption] == "Sair") {
				System.exit(1);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			menu.pause = true;
			gameState = "MENU";
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseShoot = true;
		player.mouseX = (e.getX() / SCALE);
		player.mouseY = (e.getY() / SCALE);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
