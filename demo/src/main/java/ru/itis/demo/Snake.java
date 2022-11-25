package ru.itis.demo;

import javafx.scene.control.skin.TextInputControlSkin;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Snake extends Rectangle {
    private List<Rectangle> tails;
    private final static int STEP = 10;
    private  int len = 0;
    private Direction direction;
    public Snake(int i, int i1, int i2, int i3) {
        super(i, i1, i2, i3);
        tails = new ArrayList<>();
        direction = Direction.UP;
    }

    private Rectangle endTail() {
        if (len == 0) {
            return this;
        }
        return tails.get(len - 1);
    }
    public void eat(Rectangle food) {
        Rectangle tail = endTail();
        food.setY(tail.getY());
        food.setX(tail.getX());
        tails.add(len++, food);

    }
    public void step() {

        if (direction.equals(Direction.UP)) {
            setY(getY() - STEP);
        } else if (direction.equals(Direction.DOWN)) {
            setY(getY() + STEP);
        } else if (direction.equals(Direction.LEFT)) {
            setX(getX() - STEP);
        } else if (direction.equals(Direction.RIGHT)) {
            setX(getX() + STEP);
        }
    }

    public List<Rectangle> getTails() {
        return tails;
    }

    public int getLen() {
        return len;
    }

    public Direction getDirection() {
        return direction;
    }
}
