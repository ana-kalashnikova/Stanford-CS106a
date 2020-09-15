/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
	private static final int DELAY = 2000;
	
	private static final int PADDLE_HITS_SPEED_INCREASE = 7;
	// speed of moving ball
	private static final double SPEED_DELAY = 10.0;
	
	private static final double SPEED_INCREASE = 0.5;

/* Method: run() */
/** Runs the Breakout program. */
	public void init() {
		addMouseListeners();
	}
	
	public void run() {
		setUpBricks();
		setUpPaddle();
		playGame();
	}
	
	// set up the initial bricks 
	private void setUpBricks() {
		int brickXoffset = (getWidth() - WIDTH) / 2; // space between side of window and bricks
		int x = brickXoffset;
		int y = BRICK_Y_OFFSET;
		for (int i = 0; i < NBRICK_ROWS; i++) { 
			for (int j = 0; j < NBRICKS_PER_ROW; j++) {
				GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
				add(brick);
				brick.setFilled(true);
				//set the colors
				if (i == 0 || i == 1) {
					brick.setFillColor(Color.RED);
				} else if (i == 2 || i == 3) {
					brick.setFillColor(Color.ORANGE);
				} else if (i == 4 || i == 5) {
					brick.setFillColor(Color.YELLOW);
				} else if (i == 6 || i == 7) {
					brick.setFillColor(Color.GREEN);
				} else {
					brick.setFillColor(Color.CYAN);
				}
		
				x += BRICK_WIDTH + BRICK_SEP;
			}
			x = brickXoffset;
			y += BRICK_HEIGHT + BRICK_SEP;
		}
	}

	// set up the paddle
	private void setUpPaddle() {
		int x = (getWidth() - PADDLE_WIDTH) / 2;
		int y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		add(paddle);
		paddle.setFilled(true);
		paddle.setFillColor(Color.BLACK);
	}
	
	// move the the paddle with the mouse
	public void mouseMoved(MouseEvent e) {
		double xMin = (getWidth() - WIDTH) / 2.0; // left boundary
		double xMax = (getWidth() - WIDTH) / 2.0 + WIDTH - PADDLE_WIDTH;	// right boundary
		if (e.getX() >= xMin && e.getX() <= xMax){ // if the mouse is within the boundaries move paddle to where the mouse is
			paddle.move(e.getX() - paddle.getX(), 0);
		}
	}
	
	//set up the ball
	private void bouncingBall() {
		//create the ball
		ball = new GOval(getWidth() / 2, getHeight() / 2, BALL_RADIUS * 2, BALL_RADIUS * 2);
		add(ball);
		ball.setFilled(true);
		ball.setFillColor(Color.BLACK);
		// set directions of ball movement
		vy = 3.0; // move 3px downward
		vx = rgen.nextDouble(1.0, 3.0); // x direction is random from 1 to 3, and set up to be negative half the time
		if (rgen.nextBoolean(0.5)) vx = -vx;
	}
	
	//check if ball has hit another object, and if it did, return that object
	private GObject getCollidingObject() {
		// check if each corner of the ball has touched another object
		if (getElementAt(ball.getX(), ball.getY()) != null) {  
			return getElementAt(ball.getX(), ball.getY());
		} else if (getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY()) != null) {
			return getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY());
		}else if (getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY() + (2 * BALL_RADIUS)) != null) {
			return getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY() + (2 * BALL_RADIUS));
		}else if (getElementAt(ball.getX(), ball.getY() + (2 * BALL_RADIUS)) != null) {
			return getElementAt(ball.getX(), ball.getY() + (2 * BALL_RADIUS));
		}else {
			return null;
		}
	}
	
	// 
	private void game() {
		while (totalBricks != 0) { // while there are bricks
			AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
			ball.move(vx, vy);
			pause(speedDelay);
			collider = getCollidingObject();
			if (collider != null) { // check if the ball has hit an object
				if (collider == paddle) { // if it's a paddle, bounce of
					bounceClip.play();
					vy = -vy;
					ball.move(vx, vy);
					ballPaddleHits++;
					if ((ballPaddleHits % PADDLE_HITS_SPEED_INCREASE) == 0) speedDelay -= SPEED_INCREASE;
				}else { // if it's a brick, remove that brick, and then bounce of
					bounceClip.play();
					remove(collider);
					totalBricks --;
					vy = -vy;
					ball.move(vx, vy);
				}				
			}
			//check if the ball hits the wall, and bounce of
			if (ball.getY() <= 0) {
				vy = -vy;
				ball.move(vx, vy);
			} else if (ball.getX() >= getWidth()-BALL_RADIUS*2 || ball.getX() <= 0) {
				vx = -vx;
				ball.move(vx,  vy);
			} else if (ball.getY() >= paddle.getY()) { // if the ball missed the paddle, stop the game
				break;
			}
		}
	}

	private void playGame() {
		for (int i = NTURNS; i <= NTURNS; i--) {			
			bouncingBall(); 
			speedDelay = SPEED_DELAY;
			//wait for click to begin the game
			waitForClick();
			game();		
			if (totalBricks == 0) { // add winner label if user removed all bricks
				GLabel winner = new GLabel("You've won!");
				double x = (getWidth() - winner.getWidth()) / 2;
				double y = (getHeight() - winner.getHeight()) / 2;
				add(winner, x, y);
				remove(ball);
			} else { // add looser label if user has lost, but still has attempts left
				remove(ball);
				GLabel looser = new GLabel("You have " + i + " attempts left!");
				double x = (getWidth() - looser.getWidth()) / 2;
				double y = (getHeight() - looser.getHeight()) / 2;
				add(looser, x, y);
				pause(DELAY);
				remove(looser);		
				if (i == 0) { // add Game Over label if all attempts are used and the user has lost
					GLabel gameOver = new GLabel("Game Over.");
					double gameOverX = (getWidth() - looser.getWidth()) / 2;
					double gameOverY = (getHeight() - looser.getHeight()) /2;
					add(gameOver, gameOverX, gameOverY);
					break;
				}
			}			
			pause(DELAY);
			setUpBricks();
		}
	}

	
	GRect paddle;
	GOval ball;
	GObject collider;
	private double speedDelay = SPEED_DELAY; // speed of ball movement
	private double vx, vy; // directions of ball movement
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private int totalBricks = NBRICKS_PER_ROW * NBRICK_ROWS;
	private int ballPaddleHits;
	
}
