package com.example.tetris;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Form {
    Rectangle a;
    Rectangle b;
    Rectangle c;
    Rectangle d;
    LinearGradient color;
    private final String name;
    public int form = 1;

    public Form(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.name = name;

        // Set the Color of the 7 forms
        switch (name) {
            case "c" ->{
                color = new LinearGradient(
                        0.0,0.0,1.0,0.0,true,CycleMethod.NO_CYCLE,
                        new Stop(0.0,new Color( 0.0, 0.78, 0.99, 1.0)),
                        new Stop(1.0,new Color( 0.99, 0.99, 0.11, 1.0)));
                this.a.setStroke(Color.rgb(204, 204, 204));
                this.b.setStroke(Color.rgb(204, 204, 204));
                this.c.setStroke(Color.rgb(204, 204, 204));
                this.d.setStroke(Color.rgb(204, 204, 204));
            }
            case "j" -> {
                color = new LinearGradient(
                        0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.1, 0.2, 0.22, 1.0)),
                        new Stop(0.5, new Color(0.18, 0.31, 0.35, 1.0)),
                        new Stop(1.0, new Color(0.22, 0.41, 0.49, 1.0)));
                this.a.setStroke(Color.rgb(204, 204, 204));
                this.b.setStroke(Color.rgb(204, 204, 204));
                this.c.setStroke(Color.rgb(204, 204, 204));
                this.d.setStroke(Color.rgb(204, 204, 204));
            }
            case "l" -> {
                color = new LinearGradient(
                        0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.99, 0.87, 0.34, 1.0)),
                        new Stop(1.0, new Color(1.0, 0.64, 0.31, 1.0)));
                this.a.setStroke(Color.rgb(255, 255, 255));
                this.b.setStroke(Color.rgb(255, 255, 255));
                this.c.setStroke(Color.rgb(255, 255, 255));
                this.d.setStroke(Color.rgb(255, 255, 255));
            }
            case "o" -> {
                color = new LinearGradient(
                        0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.93, 0.15, 0.06, 1.0)),
                        new Stop(1.0, new Color(0.97, 0.68, 0.11, 1.0)));
                this.a.setStroke(Color.rgb(204, 255, 255));
                this.b.setStroke(Color.rgb(204, 255, 255));
                this.c.setStroke(Color.rgb(204, 255, 255));
                this.d.setStroke(Color.rgb(204, 255, 255));
            }
            case "s" -> {
                color = new LinearGradient(
                        0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.0, 0.78, 0.99, 1.0)),
                        new Stop(1.0, new Color(0.99, 0.99, 0.11, 1.0)));
                this.a.setStroke(Color.rgb(204, 255, 255));
                this.b.setStroke(Color.rgb(204, 255, 255));
                this.c.setStroke(Color.rgb(204, 255, 255));
                this.d.setStroke(Color.rgb(204, 255, 255));
            }
            case "t" -> {
                color = new LinearGradient(
                        0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.18, 0.55, 0.95, 1.0)),
                        new Stop(1.0, new Color(0.0, 0.93, 1.0, 1.0)));
                this.a.setStroke(Color.rgb(204, 255, 255));
                this.b.setStroke(Color.rgb(204, 255, 255));
                this.c.setStroke(Color.rgb(204, 255, 255));
                this.d.setStroke(Color.rgb(204, 255, 255));
            }
            case "z" -> {
                color = new LinearGradient(
                        0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.99, 0.43, 0.49, 1.0)),
                        new Stop(1.0, new Color(0.75, 0.91, 0.99, 1.0)));
                this.a.setStroke(Color.rgb(204, 255, 255));
                this.b.setStroke(Color.rgb(204, 255, 255));
                this.c.setStroke(Color.rgb(204, 255, 255));
                this.d.setStroke(Color.rgb(204, 255, 255));
            }
            case "i" -> {
                color = new LinearGradient(
                        0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.5, 0.0, 0.99, 1.0)),
                        new Stop(1.0, new Color(0.88, 0.01, 0.98, 1.0)));
                this.a.setStroke(Color.rgb(204, 204, 255));
                this.b.setStroke(Color.rgb(204, 204, 255));
                this.c.setStroke(Color.rgb(204, 204, 255));
                this.d.setStroke(Color.rgb(204, 204, 255));
            }
            case "p" -> {
                color = new LinearGradient(
                        0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.5, 0.0, 0.99, 1.0)),
                        new Stop(1.0, new Color(0.88, 0.01, 0.98, 1.0)));
                this.d.setStroke(Color.rgb(204, 204, 255));
            }
        }

        // color of form
        this.a.setFill(color);
        this.b.setFill(color);
        this.c.setFill(color);
        this.d.setFill(color);


        // Change stroke on DarkMode [not Done]
        this.a.setStroke(Color.WHITE);
        this.b.setStroke(Color.WHITE);
        this.c.setStroke(Color.WHITE);
        this.d.setStroke(Color.WHITE);

        this.a.setStrokeWidth(3);
        this.b.setStrokeWidth(3);
        this.c.setStrokeWidth(3);
        this.d.setStrokeWidth(3);

        // shape of form
        this.a.setArcWidth(12);
        this.a.setArcHeight(12);
        this.b.setArcWidth(12);
        this.b.setArcHeight(12);
        this.c.setArcWidth(12);
        this.c.setArcHeight(12);
        this.d.setArcWidth(12);
        this.d.setArcHeight(12);

        colorAnimation();
    }

    public String getName() {
        return name;
    }
    public void changeForm() {
        if (form != 4) {
            form++;
        } else {
            form = 1;
        }
    }
    public void colorAnimation(){
        FadeTransition fadeA = new FadeTransition();
        setFade(fadeA, a);
        FadeTransition fadeB = new FadeTransition();
        setFade(fadeB, b);
        FadeTransition fadeC = new FadeTransition();
        setFade(fadeC, c);
        FadeTransition fadeD = new FadeTransition();
        setFade(fadeD, d);
    }
    public void setFade(FadeTransition fade, Node node){
        fade.setNode(node);
        fade.setDuration(Duration.millis(500));
        fade.setFromValue(1.0);
        fade.setToValue(0.9);
        fade.setCycleCount(FadeTransition.INDEFINITE);
        fade.setAutoReverse(true);
        fade.play();
    }
}