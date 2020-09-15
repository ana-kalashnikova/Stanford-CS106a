/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		if (message != null) {
			remove(message);
		}
		message = new GLabel(msg);	
		message.setFont(MESSAGE_FONT);
		double x = (getWidth() - message.getWidth()) / 2;
		double y = getHeight() - message.getHeight() - BOTTOM_MESSAGE_MARGIN;
		add(message, x, y);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		//display name
		double nameY = displayName(profile);
		//display image
		double imageY = displayImage(profile, nameY);
		//display status
		displayStatus(profile, imageY +  + IMAGE_HEIGHT);
		//display friends
		displayFriends(profile, imageY);
		
	}
	private double displayName(FacePamphletProfile profile) {
		GLabel displayName = new GLabel(profile.getName());
		displayName.setFont(PROFILE_NAME_FONT);
		displayName.setColor(Color.BLUE);
		add(displayName, LEFT_MARGIN, TOP_MARGIN + displayName.getHeight());
		return TOP_MARGIN + displayName.getHeight();
	}
	private double displayImage(FacePamphletProfile profile, double nameY) {
		double imageX = LEFT_MARGIN;
		double imageY = nameY + IMAGE_MARGIN;
		if(profile.getImage() == null) {		 
			add(new GRect(imageX, imageY, IMAGE_WIDTH, IMAGE_HEIGHT));
			GLabel noImage = new GLabel("No Image");
			noImage.setFont(PROFILE_IMAGE_FONT);
			double labelX = LEFT_MARGIN + ((IMAGE_WIDTH - noImage.getWidth()) / 2);
			double labelY = imageY + ((IMAGE_HEIGHT - noImage.getHeight()) / 2);
			add(noImage, labelX, labelY);
		}else {
			GImage profileImage = profile.getImage();
			profileImage.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(profileImage, imageX, imageY);
		}
		return imageY;
	}
	
	private void displayStatus(FacePamphletProfile profile, double imageY) {
		double labelX = LEFT_MARGIN;
		double labelY = imageY + STATUS_MARGIN;
		if (profile.getStatus().equals("")) {
			add(new GLabel("No Current Status", labelX, labelY));
		}else {
			add(new GLabel(profile.getStatus(), labelX, labelY));
		}
	}
	
	private void displayFriends(FacePamphletProfile profile, double imageY) {
		GLabel friends = new GLabel("Friends: ");
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friends, getWidth() / 2, imageY + IMAGE_MARGIN);	
		Iterator<String> friendsIterator = profile.getFriends();
		double xFriend = getWidth() / 2;
		double yFriend = friends.getY() + friends.getHeight();
		while(friendsIterator.hasNext()) {
			GLabel addFriend = new GLabel(friendsIterator.next());
			addFriend.setFont(PROFILE_FRIEND_FONT);
			add(addFriend, xFriend, yFriend);
			yFriend += addFriend.getHeight();
		}
	}
	
	private GLabel message;
}
