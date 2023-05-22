package com.example.tetris;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.LinearGradient;

public class ShowNext extends Pane{
    public static GridPane gridpane = new GridPane();
    public static final int SIZE = Tetris.SIZE;
    LinearGradient color;
    Form form;
    private String name;
    Rectangle a = new Rectangle(SIZE - 1, SIZE - 1),
              b = new Rectangle(SIZE - 1, SIZE - 1),
              c = new Rectangle(SIZE - 1, SIZE - 1),
              d = new Rectangle(SIZE - 1, SIZE - 1);

    public void setName(String name) {
        this.name = name;
    }

    public ShowNext(String name) {

         this.name = name;
         form = new Form(a, b, c, d, name);
        
        this.color = form.color;
        MakeNextShape(this.name);
        gridpane.setPadding(new Insets(350, 0, 0, 400));
    }

    public void MakeNextShape(String name) {

        form = new Form(a,b,c,d,name); 
        this.color = form.color;
        // Clear the existing children before adding new ones
        gridpane.getChildren().clear();

        switch (name) {
            case "j" -> {
                gridpane.add(a, 0, 0);
                gridpane.addRow(1, b, c, d);
                colorShape();
            }
            case "i" -> {
                gridpane.addRow(1, a, b, c, d);
                colorShape();
            }
            case "o" -> {
                gridpane.addRow(0, a, b);
                gridpane.addRow(1, c, d);
                colorShape();
            }
            case "s" -> {
                gridpane.add(a, 1, 0);
                gridpane.add(b, 2, 0);
                gridpane.add(c, 1, 1);
                gridpane.add(d, 0, 1);
                colorShape();
            }
            case "t" -> {
                gridpane.add(a, 1, 1);
                gridpane.addRow(0, b, c, d);
                colorShape();
            }
            case "z" -> {
                gridpane.add(a, 0, 0);
                gridpane.add(b, 1, 0);
                gridpane.add(c, 1, 1);
                gridpane.add(d, 2, 1);
                colorShape();
            }
            case "l" -> {
                gridpane.add(a, 2, 0);
                gridpane.addRow(1, b, c, d);
                colorShape();
            }
            case "c"->{
                gridpane.add(a,1,0);
                gridpane.add(b,0,1);
                gridpane.add(c,0,2);
                gridpane.add(d,1,3);
                colorShape();

            }
            case "p" -> {
                gridpane.add(a,0,0);
                colorShape();

            }

        }

    }
    public GridPane getGridPane() {
        return gridpane;
    }

    public void delNextShape() {
        gridpane.getChildren().removeAll(a, b, c, d);
    }

    private void colorShape() {
        a.setFill(color);
        b.setFill(color);
        c.setFill(color);
        d.setFill(color);
    }

}
