/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import acmx.export.javax.swing.JLabel;
import acmx.export.javax.swing.JTextField;
import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		
		canvas = new FacePamphletCanvas();
		add(canvas);
		
		//add JTextFields and Buttons in the North region
		add(new JLabel("Name"), NORTH);
		name = new JTextField(TEXT_FIELD_SIZE);
		add(name, NORTH);		
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);
		
		//add JTextFields and Buttons in the West region
		changeStatus = new JTextField(TEXT_FIELD_SIZE);
		add(changeStatus, WEST);
		add(new JButton("Change Status"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		changePicture = new JTextField(TEXT_FIELD_SIZE);
		add(changePicture, WEST);
		add(new JButton("Change Picture"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		addFriend = new JTextField(TEXT_FIELD_SIZE);
		add(addFriend, WEST);
		add(new JButton("Add Friend"), WEST);
		
		addActionListeners();
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {  	
    	String cmd = e.getActionCommand();
    	String profileName = name.getText();
    	String currentProfile;
    	
    	if(database.containsProfile(profileName)) {
    		currentProfile = database.getProfile(profileName).getName();
    	} else {
    		currentProfile = null;
    	}

    	
    	if(cmd.equals("Add")) {
    		FacePamphletProfile profile = new FacePamphletProfile(profileName);
    		currentProfile = addProfile(profile);
    	}else if (cmd.equals("Delete")) {
    		currentProfile = deleteProfile(profileName);
    	}else if (cmd.equals("Lookup")) {
    		currentProfile = lookupProfile(profileName);
    	}
    	
    	if (cmd.equals("Change Status")) {
    		changeStatus(currentProfile);
    	}else if(cmd.equals("Change Picture")) {
    		changePicture(currentProfile);
    	}else if(cmd.equals("Add Friend")) {
    		addFriend(currentProfile);
    	}
    	
	}
    
    //add new profile to the database and return the name, 
    //if the profile is already in the database, display the message saying that the profile already exists
    private String addProfile(FacePamphletProfile profile) { 	
    	if (database.containsProfile(profile.getName())) {
    		canvas.displayProfile(database.getProfile(profile.getName()));
			canvas.showMessage("The profile " + profile.getName() + " already exists.");
		}else {
			database.addProfile(profile);	
			canvas.displayProfile(database.getProfile(profile.getName()));
			canvas.showMessage("New profile " + profile.getName() + " created.");
		}   	
    	return profile.getName();
    }
    //delete profile if it is in the database, if not, display message saying that profile doesn't exist
    private String deleteProfile(String profileName) {
    	if (database.containsProfile(profileName)) {
    		canvas.showMessage("The profile " + profileName + " is deleted ");
    		database.deleteProfile(profileName);
		}else {
			canvas.showMessage("The profile " + profileName + " does not exist.");
		}
		return null;
    }
    //lookup the profile in the database, if it's not there, display the message saying that the profile doesn't exist
    private String lookupProfile(String profileName) {
    	if (database.containsProfile(profileName)) {
    		canvas.displayProfile(database.getProfile(profileName));
    		canvas.showMessage("Displaying: " + profileName);
			return profileName;
		}else {
			canvas.showMessage("The profile " + profileName +  " doesn't exist.");
			return null;
		}
    }
    //check if the current profile is set, and if it is, change the status
    private void changeStatus(String currentProfile) {
       	if (currentProfile != null) {
       		database.getProfile(currentProfile).setStatus(changeStatus.getText());
       		//canvas.displayProfile(database.getProfile(currentProfile));
       		canvas.showMessage("Status updated to : " + database.getProfile(currentProfile).getStatus());
		}else {
			canvas.showMessage("Please select a profile.");
		}
    }
    //check if the current profile is set, and if it is, change the picture
    private void changePicture(String currentProfile) {
    	if (currentProfile != null) {
    		GImage image = null;
            try {
            image = new GImage("images/" + changePicture.getText());           
            database.getProfile(currentProfile).setImage(image);
           // canvas.displayProfile(database.getProfile(currentProfile));
            canvas.showMessage("Image for the profile " + currentProfile + " has been changed to display " + changePicture.getText());
            } catch (ErrorException ex) {
            	canvas.showMessage("Unable to open image " + changePicture.getText());
            }         
    	}else {
    		canvas.showMessage("Please select a profile to change picture.");
    	}
    }
  
    private void addFriend(String currentProfile) {
    	//check if the current profile is set, and if it is, add a friend
    	if (currentProfile != null) {
    		if (database.containsProfile(addFriend.getText())) {
    			Iterator<String> friendsIterator = database.getProfile(currentProfile).getFriends();
    			if (friendsIterator.hasNext()) {
    				checkFriendsList(currentProfile, friendsIterator);  	
    			}else {
    				addFriendToList(currentProfile);
    			}  					
    		}else {
    			canvas.showMessage(addFriend.getText() + " does not exist.");
    		}
    	}else {
    		canvas.showMessage("No current profile is selected. Please select a profile.");
    	}
    }
    
    private void checkFriendsList(String currentProfile, Iterator<String> friendsIterator) {
    	while(friendsIterator.hasNext()) {
			if(friendsIterator.next().equals(addFriend.getText())) {
				canvas.showMessage(currentProfile + " already has " + addFriend.getText() + " as a friend."); 
				break;
			}
    	}
    }
    
	private void addFriendToList(String currentProfile) {
		//if the list is not empty: 
		//add a friend to the currentProfile
		database.getProfile(currentProfile).addFriend(addFriend.getText());
		//add the current profile to the list of friends of the added friend
		database.getProfile(addFriend.getText()).addFriend(currentProfile);
		canvas.showMessage(addFriend.getText() + " added as a friend.");
	}
	

	private JTextField name;
	private JTextField changeStatus;
	private JTextField changePicture;
	private JTextField addFriend;
	private FacePamphletDatabase database = new FacePamphletDatabase();
	private FacePamphletCanvas canvas;
}
