package com.example.tetris;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class UserControl extends BorderPane{  // to arrangement the elements

    StackPane top = new StackPane(); // make stack pane to the top elements
    public UserControl(){

        Rectangle rec_top = new Rectangle(500,150); //  create top rectangle and add it to the stack pane
        rec_top.setStrokeWidth(10);
        rec_top.setFill(Color.WHITE);
        rec_top.setArcWidth(45);
        rec_top.setArcHeight(45);
        Timeline timeline_top_rec = new Timeline(new KeyFrame(Duration.millis(200),e->{  // edit timeline to color the stroke
            rec_top.setStroke(Color.color(.3, .3,Math.random(),1));

        }));

        timeline_top_rec.setCycleCount(Timeline.INDEFINITE);
        timeline_top_rec.play();
        top.getChildren().add(rec_top); // add rectangle to stackPane
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(15,25,25,25)); // set padding to the top
        setTop(top);  // add the stack pane to the top of borderpane
        Text user_inst = new Text("User Instructions"); // make text to user instruction
        user_inst.setFill(Color.VIOLET); // give it pink color
        user_inst.setFont(Font.font("Algerian", FontWeight.BOLD, FontPosture.ITALIC, 40));
        top.getChildren().add(user_inst); // add the text to the stack pane of top
        Image directions_photo = new Image("C:\\Users\\User\\Desktop\\Tetris\\src\\arrows.jpg");
        ImageView direction = new ImageView(directions_photo);
        direction.setLayoutX(350);
        direction.setLayoutY(320);
        direction.setFitWidth(190);
        direction.setFitHeight(190);

        GridPane all_instructions = new GridPane();
        all_instructions.setAlignment(Pos.CENTER);
        //all_instructions.setPadding(new Insets(25,25,25,25));
        Text welcome  = new Text ("""
                 Hello! welcome to tetris instructions
                 Here are some instructions to start game
                  please ! use the keyboard
                \s""");
        Text up = new Text ("1-  to rotate the block\n\n");
        Text down = new Text ("2- to move the block to bottom\n\n");
        Text right = new Text ("3- to move the block to right\n\n");
        Text left = new Text ("4-  to move the block to left\n\n ");
        Text letter_R = new Text ("5-\"R\" ::  to Resume the game\n\n");
        Text space = new Text ("6-\"SPACE\" ::  to pause game \n\n ");
        Text Shift = new Text ("7-\"SHIFT\" ::  to Change the next shape\n ");
        Font font_of_all_texts =  Font.font("Arial",FontWeight.BOLD,FontPosture.ITALIC,15); // to all other texts
        Font font_welcome =  Font.font("Arial",FontWeight.BOLD,FontPosture.ITALIC,20);  // to welcome text

        letter_R.setFont(font_of_all_texts);
        space.setFont(font_of_all_texts);
        Shift.setFont(font_of_all_texts);
        up.setFont(font_of_all_texts);
        down.setFont(font_of_all_texts);
        right.setFont(font_of_all_texts);
        left.setFont(font_of_all_texts);
        welcome.setFont(font_welcome);
        space.setFill(Color.WHITE);
        left.setFill(Color.WHITE);
        right.setFill(Color.WHITE);
        down.setFill(Color.WHITE);
        up.setFill(Color.WHITE);
        welcome.setFill(Color.STEELBLUE);
        space.setFill(Color.WHITE);
        letter_R.setFill(Color.WHITE);
        Shift.setFill(Color.WHITE);
        all_instructions.addColumn(0,welcome,up,down,right,left);
        getChildren().add(direction);
        VBox v1 = new VBox(10);
        v1.getChildren().addAll(letter_R,space,Shift);
        v1.setAlignment(Pos.CENTER);
        setBottom(v1);
        setLeft(all_instructions);
        setAlignment(this, Pos.CENTER);
        setPadding(new Insets(10,50,10,20));
        setBorder(new Border(new BorderStroke( //set border to the scene
                Color.STEELBLUE, BorderStrokeStyle.SOLID,
                new CornerRadii(10), new BorderWidths(10))));
        Timeline t2 = new Timeline(new KeyFrame(Duration.millis(100),e->{
            setBorder(new Border(new BorderStroke(
                    Color.color(.5,.5,Math.random(),1), BorderStrokeStyle.SOLID,
                    new CornerRadii(10), new BorderWidths(10))));
            welcome.setFill(Color.color(0.5,0.5,Math.random(),1)); // edit animation to welcome text
        }));
        t2.setCycleCount(Timeline.INDEFINITE);
        t2.play();

        Image background = new Image("C:\\Users\\User\\Desktop\\Tetris\\src\\settings_background.jpg");

        // Create a BackgroundImage
        BackgroundImage backgroundImage = new BackgroundImage(
                background, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        // Set the background to the Pane
        setBackground(new Background(backgroundImage));
    }
}