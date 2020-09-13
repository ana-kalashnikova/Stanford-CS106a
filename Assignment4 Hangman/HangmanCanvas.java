/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	
/** Resets the display so that only the scaffold appears */
	public void reset() {
		setScaffold();
	}
	
	private void setScaffold() {
		
		
		double xStart = (getWidth() - (BEAM_LENGTH * 1.5)) / 2;
		double yStart = (getHeight() - SCAFFOLD_HEIGHT) / 4; 
		double xEnd = xStart;
		
		double yEnd = yStart + SCAFFOLD_HEIGHT;
		GLine scaffoldHeight = new GLine(xStart, yStart, xEnd, yEnd); 
		add(scaffoldHeight);
		
		double xEndBeam = xStart + BEAM_LENGTH;
		double yEndBeam = yStart;
		GLine scaffoldBeam = new GLine(xStart, yStart, xEndBeam, yEndBeam);
		add(scaffoldBeam);
		
		double xStartRope = xEndBeam;
		double yStartRope = yStart;
		xEndRope = xStartRope;
		yEndRope = yStartRope + ROPE_LENGTH;
		GLine scaffoldRope = new GLine(xStartRope, yStartRope, xEndRope, yEndRope);
		add(scaffoldRope);
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		xLabel1 = (getWidth() - (BEAM_LENGTH * 1.5)) / 2;
		yLabel1 = (getHeight() / 2) + (SCAFFOLD_HEIGHT / 2) ;
		label1 = new GLabel(word, xLabel1, yLabel1);
		add(label1);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		wrongLetters += letter;
		GLabel label2 = new GLabel(wrongLetters, xLabel1, yLabel1 + (label1.getHeight() * 2));
		add(label2);
		switch(Hangman.guess) {
		case 7: drawHead(); break;
		case 6: drawBody(); break;
		case 5: drawRightArm(); break;
		case 4: drawLeftArm(); break;
		case 3: drawRightLeg(); break;
		case 2: drawLeftLeg(); break;
		case 1: drawRightFoot(); break;
		case 0: drawLeftFoot(); break;
		}
	}
	private void drawHead() {
		double xHead = xEndRope - HEAD_RADIUS;
		double yHead = yEndRope;
		add(new GOval(xHead, yHead, HEAD_RADIUS*2, HEAD_RADIUS*2));
	}
	
	
	private void drawBody() {
		double xBodyStart =  xEndRope;
		yBodyStart = yEndRope + HEAD_RADIUS*2;
		double xBodyEnd = xBodyStart;
		yBodyEnd = yBodyStart +  BODY_LENGTH;
		add(new GLine(xBodyStart, yBodyStart, xBodyEnd, yBodyEnd));
	}
	private void drawRightArm() {
		double xUpperRightArmStart = xEndRope;
		double yRightArm = yBodyStart + ARM_OFFSET_FROM_HEAD;
		double xUpperRightArmEnd = xEndRope + UPPER_ARM_LENGTH;
		double xLowerRightArmStart = xUpperRightArmEnd;
		double yLowerRightArmStart = yRightArm;
		double xLowerRightArmEnd = xLowerRightArmStart;
		double yLowerRightArmEnd = yRightArm + LOWER_ARM_LENGTH;

		add(new GLine(xUpperRightArmStart, yRightArm, xUpperRightArmEnd, yRightArm));
		add(new GLine(xLowerRightArmStart, yLowerRightArmStart, xLowerRightArmEnd, yLowerRightArmEnd));
	}
		
	private void drawLeftArm() {
		double xUpperLeftArmStart = xEndRope;
		double yUpperLeftArm = yBodyStart + ARM_OFFSET_FROM_HEAD;
		double xUpperLeftArmEnd = xUpperLeftArmStart - UPPER_ARM_LENGTH;
		double xLowerLeftArmStart = xUpperLeftArmEnd;
		double yLowerLeftArmStart = yUpperLeftArm;
		double xLowerLeftArmEnd = xLowerLeftArmStart;
		double yLowerLeftArmEnd = yLowerLeftArmStart + LOWER_ARM_LENGTH;
		
		add(new GLine(xUpperLeftArmStart, yUpperLeftArm, xUpperLeftArmEnd, yUpperLeftArm));
		add(new GLine(xLowerLeftArmStart, yLowerLeftArmStart, xLowerLeftArmEnd, yLowerLeftArmEnd));
	}
	private void drawRightLeg() {
		double xRightUpperLegStart = xEndRope;
		double yRightUpperLegStart = yBodyEnd;
		double xRightUpperLegEnd = xRightUpperLegStart + HIP_WIDTH;
		double yRightUpperLegEnd = yRightUpperLegStart;
		double xRightLowerLegStart = xRightUpperLegEnd;
		double yRightLowerLegStart = yRightUpperLegEnd;
		double xRightLowerLegEnd = xRightLowerLegStart;
		double yRightLowerLegEnd = yRightUpperLegEnd + LEG_LENGTH;

		add(new GLine (xRightUpperLegStart, yRightUpperLegStart, xRightUpperLegEnd, yRightUpperLegEnd));
		add(new GLine (xRightLowerLegStart, yRightLowerLegStart, xRightLowerLegEnd, yRightLowerLegEnd));
	}
	private void drawLeftLeg() {
		double xLeftUpperLegStart = xEndRope;
		double yLeftUpperLegStart = yBodyEnd;
		double xLeftUpperLegEnd = xEndRope - HIP_WIDTH;
		double yLeftUpperLegEnd = yLeftUpperLegStart;
		double xLeftLowerLegStart = xLeftUpperLegEnd;
		double yLeftLowerLegStart = yLeftUpperLegEnd;
		xLeftLowerLegEnd = xLeftUpperLegEnd;
		yLeftLowerLegEnd = yLeftUpperLegEnd + LEG_LENGTH;
		
		add(new GLine(xLeftUpperLegStart, yLeftUpperLegStart, xLeftUpperLegEnd, yLeftUpperLegEnd));
		add(new GLine(xLeftLowerLegStart, yLeftLowerLegStart, xLeftLowerLegEnd, yLeftLowerLegEnd));
	}
	private void drawRightFoot() {
		double xRightFootStart = xLeftLowerLegEnd + HIP_WIDTH * 2;
		double yFoot = yLeftLowerLegEnd;
		double xRightFootEnd = xRightFootStart + FOOT_LENGTH;
		add(new GLine(xRightFootStart, yFoot, xRightFootEnd, yFoot));
	}
	private void drawLeftFoot() {
		double xLeftFootStart = xLeftLowerLegEnd;
		double xLeftFootEnd = xLeftFootStart - FOOT_LENGTH;
		double yFoot = yLeftLowerLegEnd;
		add(new GLine(xLeftFootStart, yFoot, xLeftFootEnd, yFoot));
	}

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

	private GLabel label1;
	private double xLabel1;
	private double yLabel1;
	private double xEndRope;
	private double yEndRope;
	private double yBodyStart;
	private double yBodyEnd;
	private double xLeftLowerLegEnd;
	private double yLeftLowerLegEnd;
	
	
	private String wrongLetters = "";
	
}
