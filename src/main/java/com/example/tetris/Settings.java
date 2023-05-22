package com.example.tetris;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Settings extends Pane {

     static UserControl userControl = new UserControl();
    StackPane pane = new StackPane();
    Button continue_game = new Button("Continue Game");
    ToggleButton Modes = new ToggleButton("Day Mode ");
    Button controls = new Button("Controls");
    ToggleButton music = new ToggleButton("Music :ON");
    VBox vBox =new VBox(10);
    Image background = new Image("C:\\Users\\User\\Desktop\\Tetris\\src\\settings_background.jpg");
    Image newImage = new Image("C:\\Users\\User\\Desktop\\Tetris\\src\\whiteBackground.jpg");


    Stage stage_userControl = new Stage();
    Scene scene_userControl = new Scene(userControl);
    static int stageClose = 0;

    public Settings(){
        setPane();
        setButtonsStyle();
    }

    public void setPane(){
        vBox.getChildren().addAll(continue_game, music, Modes, controls);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        pane.getChildren().add(vBox);
        pane.setPrefWidth(250);
        pane.setPrefHeight(400);

        continue_game.setOnAction(e ->{
            Tetris.timeline.play();
            stageClose = 1;
        });

        Modes.setOnAction(e -> {
            if(Modes.isSelected()){ // set the day mode

                Tetris.background.setImage(newImage);
                Tetris.line_right.setStroke(Color.rgb(0,51,102));
                Tetris.frame.setStroke(Color.rgb(0,51,102));
                Modes.setText("Dark Mode");

                Tetris.timeline.pause();
            }
            else { // cancel the day mode

                Tetris.background.setImage(Tetris.background_image);
                Tetris.line_right.setStroke(Color.rgb(255,204,0));
                Tetris.frame.setStroke(Color.rgb(255,204,0));
                Modes.setText("Day Mode");

                Tetris.timeline.pause();
            }
        });
        controls.setOnAction(e -> {
            stage_userControl.setScene(scene_userControl);
            stage_userControl.show();
            stage_userControl.setResizable(false);
        });
        music.setOnAction(e -> {
            if( music.isSelected()) {
                music.setText("Music :OFF");
                Tetris.musicPlayer.pause();
            }else {
                music.setText("Music :ON");
                Tetris.musicPlayer.play();
                Tetris.musicPlayer.setOnEndOfMedia(() -> {
                    Tetris.musicPlayer.seek(Tetris.musicPlayer.getStartTime()); // Seek to the beginning
                    Tetris.musicPlayer.play(); // Replay the media file
                });
            }

        });

        pane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
    }
    public void setButtonsStyle(){
        continue_game.getStyleClass().add("button");
        music.getStyleClass().add("button");
        Modes.getStyleClass().add("button");
        controls.getStyleClass().add("button");

    }
}
