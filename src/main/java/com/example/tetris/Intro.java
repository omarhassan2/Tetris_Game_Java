package com.example.tetris;

import javafx.animation.FadeTransition;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;

public class Intro extends Pane {

     double width = 500;
     double height = 283;
     StackPane stackPane = new StackPane();
     File introFile = new File("C:\\Users\\User\\Desktop\\Tetris\\src\\Intro_Game.mp4");
     Media introMedia = new Media(introFile.toURI().toString());
    // Add INTRO before start the game
     MediaPlayer introPlayer = new MediaPlayer(introMedia);
     MediaView introView = new MediaView(introPlayer);

    LinearGradient introFrame = new LinearGradient(
            0.0,0.0,1.0,0.0,true, CycleMethod.NO_CYCLE,
            new Stop(0.0,new Color( 0.5, 0.0, 0.99, 1.0)),
            new Stop(1.0,new Color( 0.88, 0.01, 0.98, 1.0)));
    Rectangle frame = new Rectangle(width + 10, height + 10 );


    public Intro(){
        frame.setFill(null);
        frame.setStroke(introFrame);
        frame.setStrokeWidth(12);
        stackPane.getChildren().add(introView);
        stackPane.getChildren().add(frame);
        introView.setFitHeight(height);
        introView.setFitWidth(width);
        colorAnimation();
        introPlayer.play();
    }

    public void stop(){
        introPlayer.stop();
    }
    public void colorAnimation(){
        FadeTransition fade = new FadeTransition(Duration.millis(500), frame);
        fade.setFromValue(1.0);
        fade.setToValue(0.7);
        fade.setCycleCount(FadeTransition.INDEFINITE);
        fade.setAutoReverse(true);
        fade.play();
    }
}
