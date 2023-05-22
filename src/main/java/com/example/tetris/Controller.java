package com.example.tetris;

import javafx.scene.shape.Rectangle;
import java.util.Random;

public class Controller {
    // Getting the numbers and the MESH from Tetris
    public static final int MOVE = Tetris.MOVE;
    public static final int SIZE = Tetris.SIZE;
    public static int XMAX = Tetris.XMAX;
    public static int[][] MESH = Tetris.MESH;

    public static void MoveRight(Form form) {
        // check if the squares reach the end of frame[XMax]
        if (form.a.getX() + MOVE <= XMAX - SIZE && form.b.getX() + MOVE <= XMAX - SIZE
                && form.c.getX() + MOVE <= XMAX - SIZE && form.d.getX() + MOVE <= XMAX - SIZE) {
            int movea = MESH[((int) form.a.getX() / SIZE) + 1][((int) form.a.getY() / SIZE)];
            int moveb = MESH[((int) form.b.getX() / SIZE) + 1 ][((int) form.b.getY() / SIZE)];
            int movec = MESH[((int) form.c.getX() / SIZE) + 1 ][((int) form.c.getY() / SIZE)];
            int moved = MESH[((int) form.d.getX() / SIZE) + 1][((int) form.d.getY() / SIZE)];

            // check if the squares to the right are free and the block can be moved
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                form.a.setX(form.a.getX() + MOVE);
                form.b.setX(form.b.getX() + MOVE);
                form.c.setX(form.c.getX() + MOVE);
                form.d.setX(form.d.getX() + MOVE);
            }
        }
    }

    public static void MoveLeft(Form form) {
        // check if the squares reach the end of frame[start point (0)]
        if (form.a.getX() - MOVE >= 0 && form.b.getX() - MOVE >= 0 && form.c.getX() - MOVE >= 0
                && form.d.getX() - MOVE >= 0) {
            int movea = MESH[((int) form.a.getX() / SIZE) - 1][((int) form.a.getY() / SIZE)];
            int moveb = MESH[((int) form.b.getX() / SIZE) - 1][((int) form.b.getY() / SIZE)];
            int movec = MESH[((int) form.c.getX() / SIZE) - 1][((int) form.c.getY() / SIZE)];
            int moved = MESH[((int) form.d.getX() / SIZE) - 1][((int) form.d.getY() / SIZE)];

            // check if the squares to the left are free and the block can be moved
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                form.a.setX(form.a.getX() - MOVE);
                form.b.setX(form.b.getX() - MOVE);
                form.c.setX(form.c.getX() - MOVE);
                form.d.setX(form.d.getX() - MOVE);
            }
        }
    }

    public static Form makeRect() {
        Random random = new Random();
        int block = random.nextInt(131); // Generates a random integer between 0 and 130 (inclusive)
        String name;
        Rectangle a = new Rectangle(SIZE-1, SIZE-1), b = new Rectangle(SIZE-1, SIZE-1), c = new Rectangle(SIZE-1, SIZE-1),
                d = new Rectangle(SIZE-1, SIZE-1);
        // Create first Form [form = 1]
        if (block < 15) {
            a.setX(XMAX / 2.0 - SIZE);
            b.setX(XMAX / 2.0 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX / 2.0 );
            c.setY(SIZE);
            d.setX(XMAX / 2.0 + SIZE);
            d.setY(SIZE);
            name = "j";
        } else if (block < 30) {
            a.setX(XMAX / 2.0 + SIZE);
            b.setX(XMAX / 2.0 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX / 2.0);
            c.setY(SIZE);
            d.setX(XMAX / 2.0 + SIZE);
            d.setY(SIZE);
            name = "l";
        } else if (block < 45) {
            a.setX(XMAX / 2.0 - SIZE);
            b.setX(XMAX / 2.0);
            c.setX(XMAX / 2.0 - SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2.0);
            d.setY(SIZE);
            name = "o";
        } else if (block < 60) {
            a.setX(XMAX / 2.0 + SIZE);
            b.setX(XMAX / 2.0);
            c.setX(XMAX / 2.0);
            c.setY(SIZE);
            d.setX(XMAX / 2.0 - SIZE);
            d.setY(SIZE);
            name = "s";
        } else if (block < 75) {
            a.setX(XMAX / 2.0 - SIZE);
            b.setX(XMAX / 2.0);
            c.setX(XMAX / 2.0);
            c.setY(SIZE);
            d.setX(XMAX / 2.0 + SIZE);
            name = "t";
        } else if (block < 90) {
            a.setX(XMAX / 2.0 + SIZE);
            b.setX(XMAX / 2.0);
            c.setX(XMAX / 2.0 + SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2.0 + SIZE + SIZE);
            d.setY(SIZE);
            name = "z";
        } else if(block < 105) {
            a.setX(XMAX / 2.0 - SIZE - SIZE);
            b.setX(XMAX / 2.0 - SIZE);
            c.setX(XMAX / 2.0);
            d.setX(XMAX / 2.0 + SIZE);
            name = "i";
        }
        else if(block < 120){
            a.setX(XMAX / 2.0 - SIZE);
            a.setY(SIZE);
            b.setX(XMAX / 2.0);
            c.setX(XMAX / 2.0 - SIZE);
            c.setY(SIZE + SIZE);
            d.setX(XMAX / 2.0);
            d.setY(SIZE + SIZE + SIZE);
            name = "c";
        }
        else {
            a.setX(XMAX / 2.0 - SIZE);
            b.setX(XMAX / 2.0 - SIZE);
            c.setX(XMAX / 2.0 - SIZE);
            d.setX(XMAX / 2.0 - SIZE);
            name = "p";
        }

        return new Form(a, b, c, d, name);
    }
}
