package com.nasumilu.cop2552.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

	private final List<Image> IMAGES = new ArrayList<>();
	private final Random RANDOM = new Random();
	
	public App() {
		super();
		// init our images
		for(int i=1; i<7; i++) {
			this.IMAGES.add(i - 1, new Image(App.class.getResource("Die" + i + ".png").toExternalForm()));
		}
	}
	
    @Override
    public void start(@SuppressWarnings("exports") Stage stage) {

        var scene = new Scene(this.buildScene(), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
    
    private VBox buildScene() {
    	final var dieOne = new ImageView();
    	final var dieTwo = new ImageView();
    	
    	var vbox = new VBox();
    	vbox.setSpacing(20);
    	vbox.setAlignment(Pos.CENTER);
    	
    	var imageHbox = new HBox();
    	imageHbox.setSpacing(15);
    	imageHbox.setAlignment(Pos.CENTER);
    	imageHbox.getChildren().addAll(dieOne, dieTwo);
    	
    	final var score = new Label("Score: 0");
    	
    	final var exitButton = new Button("Exit");
    	exitButton.setOnAction(e -> Platform.exit());
    	
    	final var rollButton = new Button("Roll");
    	rollButton.setOnAction(e -> {
    		int size = this.IMAGES.size();
    		int one = this.RANDOM.nextInt(size);
    		int two = this.RANDOM.nextInt(size);
    		dieOne.setImage(this.IMAGES.get(one));
    		dieTwo.setImage(this.IMAGES.get(two));
    		score.setText("Score: " + (one + two + 2));
    	});
    	
    	
    	var controlHbox = new HBox();
    	controlHbox.setSpacing(150);
    	controlHbox.setAlignment(Pos.CENTER);
    	controlHbox.getChildren().addAll(exitButton, score, rollButton);
    	
    	
    	vbox.getChildren().addAll(imageHbox, controlHbox);
    	
    	
    	
    	return vbox;
    	
    }

    public static void main(String[] args) {
        launch();
    }

}