/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	public void run(){
		while(leftIsClear()){
			row();
			if (facingEast()){
				turnWest();
			}else{
				turnEast();
			}
		}
		
		
	}

	private void row(){
		while (frontIsClear()){
			putBeeper();
			if (frontIsClear()){
				move();
			}
			if (frontIsClear()){
				move();
			}
		}
	}
	private void turnWest(){
		turnLeft();
		move();
		turnAround();
		turnRight();
	}
	private void turnEast(){
		turnRight();
		if (frontIsClear()){
			move();
			turnRight();
		}else{
			turnRight();
		}

		
	}
}

