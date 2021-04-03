package com.nasumilu.cop2552.dice;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class PrimaryController implements Initializable {

	private List<Image> images = new ArrayList<>();
	private final Random RANDOM = new Random();
	
	@FXML
	private ImageView diceOne;
	@FXML
	private ImageView diceTwo;
	@FXML
	private Label scoreField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// load the images when initializing the controller
		for(int i = 1; i<7; i++) {
			this.images.add(i - 1, new Image(PrimaryController.class.getResource("Die" + i + ".png").toExternalForm()));
		}
	}
	
	private int random() {
		return this.RANDOM.nextInt(this.images.size());
	}
    
    @FXML
    public void onRollButtonClick() {
    	int one = this.random();
    	int two = this.random();
    	
    	this.diceOne.setImage(this.images.get(one));
    	this.diceTwo.setImage(this.images.get(two));
    	
    	this.scoreField.setText("Score: " + (one + two + 2));
    }
    
    @FXML
    public void onExitButtonClick() {
    	Platform.exit();
    }


}
