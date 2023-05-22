package com.example.tetris;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Tetris extends Application {

    // The variables
    public static final int MOVE = 25;
    public static final int SIZE = 25;
    public static int XMAX = SIZE * 12;
    public static int YMAX = SIZE * 24;
    public static int[][] MESH = new int[XMAX / SIZE][YMAX / SIZE];
    static Pane group = new Pane();
    private static Form object;
    private static final Scene scene = new Scene(group, XMAX*2, YMAX + 7);
    private static int top = 0;
    private static boolean game = true;
    private static Form nextObj = Controller.makeRect();
    private static int linesNumbers = 0;
    public static int score = 0;
     public static int highScore = 0;
     ShowNext shownext;
     public static int countChange = 0;
     File file = new File("highScore.txt");
     static final Timeline timeline = new Timeline();
     static final Intro intro = new Intro();
     static final File musicFile = new File("C:\\Users\\User\\Desktop\\Tetris\\src\\Intro_Game.mp4");
     static final Media musicMedia = new Media(musicFile.toURI().toString());
     static final MediaPlayer musicPlayer = new MediaPlayer(musicMedia);
     static final Image icon = new Image("C:\\Users\\User\\Desktop\\tetris-master\\Tetris icon.png");
     static Image background_image = new Image("C:\\Users\\User\\Desktop\\Tetris\\src\\blackBackground.jpg");
     static ImageView background = new ImageView(background_image);
     static  Color frameColor = Color.rgb(255,204,0);
     static Line line_right = new Line(XMAX + 5, 0, XMAX + 5, YMAX);
     static  Color  lineColor = Color.rgb(255,204,0);
     static final Rectangle frame = new Rectangle(0, 0, XMAX*2, YMAX + 7);
     Stage stage_settings = new Stage();
     Settings settingsPane = new Settings();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage)  {

        ImageView InstructionsIcon = new ImageView("C:\\Users\\User\\Desktop\\Tetris\\src\\instructions icon.png");
        InstructionsIcon.setX(XMAX + 130);
        InstructionsIcon.setY(500);
        InstructionsIcon.setFitHeight(50);
        InstructionsIcon.setFitWidth(50);
        InstructionsIcon.setOnMouseClicked(mouseEvent -> {
            timeline.pause();
            Scene instructions_scene = Settings.userControl.getScene();
            Stage instruction_stage = new Stage();
            instruction_stage.setScene(instructions_scene);
            instruction_stage.show();
         });
        group.getChildren().add(InstructionsIcon);

        ImageView settingsIcon = new ImageView("C:\\Users\\User\\Desktop\\Tetris\\src\\Settings_Icon.png");
        settingsIcon.setX(XMAX*2 - 60);
        settingsIcon.setY(8);
        settingsIcon.setFitHeight(50);
        settingsIcon.setFitWidth(50);
        settingsIcon.setOnMouseClicked(mouseEvent -> {
            timeline.pause();
            stage_settings.show();
            stage_settings.setResizable(false  );
        });
        group.getChildren().add(settingsIcon);

        ToggleButton pause = new ToggleButton("Pause");
        pause.getStyleClass().add("button");
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css" )).toExternalForm());
        pause.setOnAction(event -> {
            if(pause.isSelected()){
                pause.setText("Continue");
                timeline.pause();
            }else{
                pause.setText("Pause");
                timeline.play();
            }

        });
        pause.setFocusTraversable(false);
        pause.setLayoutX(XMAX + 20);
        pause.setLayoutY(YMAX - 50);
        group.getChildren().add(pause);

        Button exit = new Button("Exit");
        exit.getStyleClass().add("button");
        exit.setOnAction(event -> System.exit(0));
        exit.setFocusTraversable(false);
        exit.setLayoutX(XMAX*2 - 80);
        exit.setLayoutY(YMAX - 50);
        group.getChildren().add(exit);

        // Add Music while playing

        Scene sceneMedia = new Scene(intro.stackPane);
        stage.setScene(sceneMedia);
        stage.getIcons().add(icon);
        stage.setTitle("T E T R I S");
        stage.show();
        stage.setResizable(false);


       Scene scene_settings = new Scene(settingsPane.pane);
       scene_settings.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css" )).toExternalForm());
       stage_settings.setScene(scene_settings);


        sceneMedia.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){

                intro.stop();
                stage.setScene(scene);
                timeline.play();
                musicPlayer.play();

            }
        });

        // Background grid
        background.setFitWidth(XMAX + 5);
        background.setFitHeight(YMAX + 10);
        group.getChildren().add(background);

        /* nested for-each loop to iterate over a 2D integer array 'MESH'
        and fill each element of the array with the value '0' */
        for (int[] mesh : MESH) {
            Arrays.fill(mesh, 0);
        }

        // to get High Score from file
        getHighScore();

        // Interface Frame

        line_right.setStroke(lineColor);
        line_right.setStrokeWidth(5);

        frame.setFill(null);
        frame.setStroke(frameColor);
        frame.setStrokeWidth(10);
        group.getChildren().add(frame);

        Text scoreText = new Text("Score : ");
        scoreText.setStyle(
                """
                        -fx-font-family: "AGA Arabesque";
                        -fx-font-size: 22;
                        -fx-text-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #05d8dfff 0.0%,
                         #05d8dfff 5.25%, #026881ff 89.75%, #026881ff 100.0%);
                        -fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #05d8dfff 0.0%,
                         #05d8dfff 5.25%, #026881ff 89.75%, #026881ff 100.0%);""");
        scoreText.setY(150);
        scoreText.setX(XMAX + 20);

        Text level = new Text("Lines : ");
        level.setStyle("""
                -fx-font-family: "AGA Arabesque";
                -fx-font-size: 22;
                -fx-text-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #05d8dfff 0.0%,
                 #05d8dfff 5.25%, #026881ff 89.75%, #026881ff 100.0%);
                -fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #05d8dfff 0.0%, #05d8dfff 5.25%,
                 #026881ff 89.75%, #026881ff 100.0%);""");
        level.setY(200);
        level.setX(XMAX + 20);
        level.setFill(Color.CYAN);

        Text nextShape = new Text("Next : ");
        nextShape.setStyle("""
                -fx-font-family: "AGA Arabesque";
                -fx-font-size: 22;
                -fx-text-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%,
                 #05d8dfff 0.0%, #05d8dfff 5.25%, #026881ff 89.75%, #026881ff 100.0%);
                -fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #05d8dfff 0.0%,
                 #05d8dfff 5.25%, #026881ff 89.75%, #026881ff 100.0%);""");
        nextShape.setY(300);
        nextShape.setX(XMAX + 20);
        nextShape.setFill(Color.CYAN);

        Text HighScore = new Text("High Score : " + highScore);
        HighScore.setStyle(
                """
                        -fx-font-family: "Broadway";
                        -fx-font-size: 26;
                        -fx-text-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%,
                         #a900c7ff 0.0%, #a900c7ff 5.25%, #9c0d7aff 89.75%, #9c0d7aff 100.0%);
                        -fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%,
                         #a900c7ff 0.0%, #a900c7ff 5.25%, #9c0d7aff 89.75%, #9c0d7aff 100.0%);""");
        HighScore.setX(XMAX + 40);
        HighScore.setY(90);
        HighScore.setFill(Color.CYAN);
        group.getChildren().addAll(HighScore, scoreText, line_right, level, nextShape);

        Form block = nextObj;
        group.getChildren().addAll(block.a, block.b, block.c, block.d);
        moveOnKeyPress(block);
        object = block;
        nextObj = Controller.makeRect();


        // To show the new Shape
        shownext = new ShowNext(nextObj.getName());
        group.getChildren().add(ShowNext.gridpane);

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(300), event -> {
                    if(Settings.stageClose == 1){
                        stage_settings.close();
                    }
                    group.requestFocus();
                    if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0 || object.d.getY() == 0) {
                        top++;
                    } else {
                        top = 0;
                    }

                    if (top == 2) {
                        Text over = new Text("GAME OVER");
                        over.setStyle("""
                                /*If it is an external font, you need to load it; @font-face {...} */
                                -fx-font-family: "Broadway";
                                -fx-font-size: 90;
                                -fx-text-fill: #e52817ff;
                                -fx-fill: #e52817ff;""");

                        over.setY(300);
                        over.setX(15);
                        group.getChildren().add(over);
                        if(score > highScore){
                            setHighScore();
                        }
                        game = false;
                    }

                    if (top == 7) {
                        System.exit(0);
                    }

                    if (game) {
                        MoveDown(object);
                        scoreText.setText("Score: " + score);
                        level.setText("Lines: " + linesNumbers);
                    }
                })
        );
    }

    // Create the control system of the block(form)
    private  void moveOnKeyPress(Form form) {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case RIGHT -> Controller.MoveRight(form);
                case DOWN -> {
                    MoveDown(form);
                    score++; // When he moves down ( more faster more score)
                }
                case LEFT -> Controller.MoveLeft(form);
                case UP -> MoveTurn(form);
                case SHIFT -> ChangeShape();
                case S -> {
                    stage_settings.show();
                    timeline.pause();
                }
                case R -> timeline.play();
                case SPACE -> timeline.pause();
            }
        });
    }

    // Rotate the block each rotation by 90 degree clockwise
    private void MoveTurn(Form form) {
        int f = form.form;
        Rectangle a = form.a;
        Rectangle b = form.b;
        Rectangle c = form.c;
        Rectangle d = form.d;
        switch (form.getName()) {
            /* these numbers in the canRotate method refer to units of moving
            in x_direction [1] -> one unit to Right, [-1] one unit to Left
            in y_direction [1] -> one unit to Up, [-1] one unit to Down
            rotate around b [b doesn't change]
             */
            case "j":
                if (f == 1 && canRotate(a, 1, -1) && canRotate(c, -1, -1) && canRotate(d, -2, -2)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && canRotate(a, -1, -1) && canRotate(c, -1, 1) && canRotate(d, -2, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && canRotate(a, -1, 1) && canRotate(c, 1, 1) && canRotate(d, 2, 2)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && canRotate(a, 1, 1) && canRotate(c, 1, -1) && canRotate(d, 2, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case "l":
                if (f == 1 && canRotate(a, 1, -1) && canRotate(c, 1, 1) && canRotate(b, 2, 2)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    MoveUp(form.b);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveRight(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 2 && canRotate(a, -1, -1) && canRotate(b, 2, -2) && canRotate(c, 1, -1)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveRight(form.b);
                    MoveRight(form.b);
                    MoveDown(form.b);
                    MoveDown(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && canRotate(a, -1, 1) && canRotate(c, -1, -1) && canRotate(b, -2, -2)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    MoveDown(form.b);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveLeft(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 4 && canRotate(a, 1, 1) && canRotate(b, -2, 2) && canRotate(c, -1, 1)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveLeft(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.b);
                    MoveUp(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    form.changeForm();
                    break;
                }
                break;
            case "o":
                break;
            case "s":
                if (f == 1 && canRotate(a, -1, -1) && canRotate(c, -1, 1) && canRotate(d, 0, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && canRotate(a, 1, 1) && canRotate(c, 1, -1) && canRotate(d, 0, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && canRotate(a, -1, -1) && canRotate(c, -1, 1) && canRotate(d, 0, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && canRotate(a, 1, 1) && canRotate(c, 1, -1) && canRotate(d, 0, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case "t":
                if (f == 1 && canRotate(a, 1, 1) && canRotate(d, -1, -1) && canRotate(c, -1, 1)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 2 && canRotate(a, 1, -1) && canRotate(d, -1, 1) && canRotate(c, 1, 1)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.d);
                    MoveUp(form.d);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && canRotate(a, -1, -1) && canRotate(d, 1, 1) && canRotate(c, 1, -1)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 4 && canRotate(a, -1, 1) && canRotate(d, 1, -1) && canRotate(c, -1, -1)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveRight(form.d);
                    MoveDown(form.d);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    form.changeForm();
                    break;
                }
                break;
            case "z":
                if (f == 1 && canRotate(b, 1, 1) && canRotate(c, -1, 1) && canRotate(d, -2, 0)) {
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && canRotate(b, -1, -1) && canRotate(c, 1, -1) && canRotate(d, 2, 0)) {
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && canRotate(b, 1, 1) && canRotate(c, -1, 1) && canRotate(d, -2, 0)) {
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && canRotate(b, -1, -1) && canRotate(c, 1, -1) && canRotate(d, 2, 0)) {
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case "i":
                if (f == 1 && canRotate(a, 2, 2) && canRotate(b, 1, 1) && canRotate(d, -1, -1)) {
                    MoveUp(form.a);
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.a);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && canRotate(a, -2, -2) && canRotate(b, -1, -1) && canRotate(d, 1, 1)) {
                    MoveDown(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.a);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && canRotate(a, 2, 2) && canRotate(b, 1, 1) && canRotate(d, -1, -1)) {
                    MoveUp(form.a);
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.a);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && canRotate(a, -2, -2) && canRotate(b, -1, -1) && canRotate(d, 1, 1)) {
                    MoveDown(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.a);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
            case "c":
                if (f == 1 && canRotate(a, 0, 2) && canRotate(c, -1, 3) && canRotate(d, -3, 3)) {
                    MoveUp(form.a);
                    MoveUp(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveUp(form.c);
                    MoveUp(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && canRotate(a, 2, 0) && canRotate(c, 3, 1) && canRotate(d, 3, 3)) {
                    MoveRight(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveRight(form.c);
                    MoveRight(form.c);
                    MoveUp(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && canRotate(a, 0, -2) && canRotate(c, 1, -3) && canRotate(d, 3, -3)) {
                    MoveDown(form.a);
                    MoveDown(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveDown(form.c);
                    MoveDown(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && canRotate(a, -2, 0) && canRotate(c, -3, -1) && canRotate(d, -3, -3)) {
                    MoveLeft(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveLeft(form.c);
                    MoveLeft(form.c);
                    MoveDown(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    MoveDown(form.d);

                    form.changeForm();
                    break;
                }
                break;
        }


    }
    private void RemoveRows(Pane pane) {
        /* These lists are used to store information about rectangles (Nodes) in the game,
         lines that are full, and rectangles that need to be updated, respectively.
         */
        ArrayList<Node> rectangles = new ArrayList<>();
        ArrayList<Integer> lines = new ArrayList<>();
        ArrayList<Node> newRectangles = new ArrayList<>();

        int full = 0; // The variable is used to count the number of filled positions in each line.

        // It counts the number of filled positions (MESH[j][i] == 1) in each line.
        for (int i = 0; i < MESH[0].length; i++) {
            for (int[] mesh : MESH) {
                if (mesh[i] == 1)
                    full++;
            }
            // If the count equals the length of the line,the line index (i) is added to the lines list.
            if (full == MESH.length)
                lines.add(i);
            full = 0;
        }
        if (lines.size() > 0)
            do {
                // Retrieve all rectangles from the pane and store them in the 'rectangles' list
                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle)
                        rectangles.add(node);
                }
                // Update the score and linesNumbers, each line = 50 points;
                score += 50;
                linesNumbers++;

                // Iterate over the rectangles
                for (Node node : rectangles) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY() == lines.get(0) * SIZE) {
                        // Remove the rectangle from the grid and the pane
                        MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                        pane.getChildren().remove(node);
                    } else {
                        // Store rectangles that need to be updated in the 'newRectangles' list
                        newRectangles.add(node);
                    }
                }

                // Update the position of rectangles above the removed line
                for (Node node : newRectangles) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY() < lines.get(0) * SIZE) {
                        MESH[(int) (a.getX() / SIZE)][(int) (a.getY() / SIZE)] = 0;
                        a.setY(a.getY() + SIZE);
                    }
                }

                // Remove the processed line from the 'lines' list
                lines.remove(0);


                // Clear the lists for the next iteration
                rectangles.clear();
                newRectangles.clear();

                // Update the 'MESH' array based on the remaining rectangles
                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle)
                        rectangles.add(node);
                }
                for (Node node : rectangles) {
                    Rectangle a = (Rectangle) node;
                    try {
                        MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        // Ignore any out-of-bounds exceptions
                    }
                }
                rectangles.clear();
            } while (lines.size() > 0);
    }

    // Move each Rectangle when rotating
    private void MoveDown(Rectangle rect) {
        if (rect.getY() + MOVE < YMAX)
            rect.setY(rect.getY() + MOVE);
    }
    private void MoveRight(Rectangle rect) {
        if (rect.getX() + MOVE <= XMAX - SIZE)
            rect.setX(rect.getX() + MOVE);
    }
    private void MoveLeft(Rectangle rect) {
        if (rect.getX() - MOVE >= 0)
            rect.setX(rect.getX() - MOVE);
    }
    private void MoveUp(Rectangle rect) {
        if (rect.getY() - MOVE > 0)
            rect.setY(rect.getY() - MOVE);
    }

    // Move down the shape
    private void MoveDown(Form form) {
        /* It first checks if any of the objects in the form (a, b, c, d) have reached the bottom of the grid (YMAX)
         or there is another rectangle above it, If the value is 1, it means there is an obstacle below */
        if (form.a.getY() == YMAX - SIZE || form.b.getY() == YMAX - SIZE || form.c.getY() == YMAX - SIZE
                || form.d.getY() == YMAX - SIZE || moveA(form) || moveB(form) || moveC(form) || moveD(form)) {
            /* If any object in the form reaches the bottom or has an obstacle below,
            set the corresponding position in the MESH array to 1 */
            MESH[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
            MESH[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
            MESH[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
            MESH[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;

            // Remove any filled rows and update the game UI
            RemoveRows(group);
            frame.setX(0);
            frame.setY(0);


            // Perform actions related to the next shape
            shownext.delNextShape();
            group.getChildren().remove(shownext.getGridPane());
            Form a = nextObj;
            nextObj = Controller.makeRect();
            try {
                shownext.setName(nextObj.getName());
                shownext.MakeNextShape(nextObj.getName());
                group.getChildren().add(shownext.getGridPane());

            }catch (java.lang.IllegalArgumentException e){
                // do nothing
            }

            // Update the current object and its position
            object = a;
            group.getChildren().addAll(a.a, a.b, a.c, a.d);

            // Register key events for the new object
            moveOnKeyPress(a);
        }

        // Check if all positions below the current objects are clear (value is 0) in the MESH array
        if (form.a.getY() + MOVE < YMAX && form.b.getY() + MOVE < YMAX && form.c.getY() + MOVE < YMAX
                && form.d.getY() + MOVE < YMAX) {
            // If all positions are clear (value is 0), move each object
            int movea = MESH[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1];
            int moveb = MESH[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1];
            int movec = MESH[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1];
            int moved = MESH[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1];

            // Check if all the move variables are equal to 0, indicating that all positions below are clear
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                // Move each object down by the MOVE value
                form.a.setY(form.a.getY() + MOVE);
                form.b.setY(form.b.getY() + MOVE);
                form.c.setY(form.c.getY() + MOVE);
                form.d.setY(form.d.getY() + MOVE);
            }
        }
    }

    // check is there is the next place[when I move down] is empty or not[to avoid collision]
    private boolean moveA(Form form) {
        return (MESH[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1] == 1);
    }
    private boolean moveB(Form form) {
        return (MESH[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1] == 1);
    }
    private boolean moveC(Form form) {
        return (MESH[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1] == 1);
    }
    private boolean moveD(Form form) {
        return (MESH[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1] == 1);
    }

    /* canRotate method checks if a rectangle can move to a new position[xb, yb] without colliding
     with other rectangles or going outside the boundaries of the game area.*/
    private boolean canRotate(Rectangle rect, int x, int y) {
        boolean xb = false;
        boolean yb = false;
        if (x >= 0) // to Right
            xb = rect.getX() + x * MOVE <= XMAX - SIZE;
        if (x < 0) // to Left
            xb = rect.getX() + x * MOVE >= 0;
        if (y >= 0) // to Up
            yb = rect.getY() - y * MOVE > 0;
        if (y < 0) // to Down
            yb = rect.getY() + y * MOVE < YMAX;

        return xb && yb && MESH[((int) rect.getX() / SIZE) + x][((int) rect.getY() / SIZE) - y] == 0;
    }
    private void getHighScore() {
        try {
            Scanner scan = new Scanner(file);
            highScore = scan.nextInt();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!!!");
        }
    }
    private void setHighScore() {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(score);
            printWriter.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!!!");
        }
    }
    private void ChangeShape() {
        if (countChange < 3) {
            try {
                String name = nextObj.getName();
                shownext.delNextShape();
                group.getChildren().remove(shownext.getGridPane());
                nextObj = Controller.makeRect();
                while (name.equals(nextObj.getName())){
                    nextObj = Controller.makeRect();
                }
                shownext.setName(nextObj.getName());
                shownext.MakeNextShape(nextObj.getName());
                group.getChildren().add(shownext.getGridPane());
                countChange++;
            }catch (java.lang.IllegalArgumentException e){
                // do nothing
            }

        } else {
            Text text = new Text("Sorry, All attempts \nhave been exhausted.");
            text.setFill(Color.RED);
            text.setX(XMAX + 60);
            text.setY(460);
            text.setStyle("""
                    -fx-font-family: "AGA Arabesque";
                    -fx-font-size: 22;
                    -fx-text-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%,
                     #a900c7ff 0.0%, #a900c7ff 5.25%, #9c0d7aff 89.75%, #9c0d7aff 100.0%);
                    -fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%,
                     #a900c7ff 0.0%, #a900c7ff 5.25%, #9c0d7aff 89.75%, #9c0d7aff 100.0%);""");
            FadeTransition fadetransition = new FadeTransition(Duration.millis(3000),text);
            fadetransition.setFromValue(1);
            fadetransition.setToValue(0);
            fadetransition.setByValue(.1);
            fadetransition.play();
            group.getChildren().add(text);
        }
    }
}