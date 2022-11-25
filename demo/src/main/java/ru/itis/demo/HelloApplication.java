package ru.itis.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.Key;
import java.util.Random;

public class HelloApplication extends Application {

    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;
    private final static int STEP = 10;

    private Pane root;
    private Snake snake;

    private Rectangle food;

    private Direction direction;

    private void newRandomFood() {
        Random random = new Random();
         food = new Rectangle(random.nextInt(WIDTH), random.nextInt(HEIGHT), 10, 10);
        food.setFill(Color.RED);
        root.getChildren().add(food);
    }

    private void step() {
        for (int i =  snake.getLen() -1; i >= 0; i--) {
            if(i == 0 ){
                snake.getTails().get(i).setX(snake.getX());
                snake.getTails().get(i).setY(snake.getY());
            } else {
                snake.getTails().get(i).setX(snake.getTails().get(i-1).getX());
                snake.getTails().get(i).setY(snake.getTails().get(i-1).getY());
            }
        }
        if (direction.equals(Direction.UP)) {
            snake.setY(snake.getY() - STEP);
        } else if (direction.equals(Direction.DOWN)) {
            snake.setY(snake.getY() + STEP);
        } else if (direction.equals(Direction.LEFT)) {
            snake.setX(snake.getX() - STEP);
        } else if (direction.equals(Direction.RIGHT)) {
            snake.setX(snake.getX() + STEP);
        }
    }

    private void move() {
        Platform.runLater( () -> {
            step();
            if(hit()) {
                snake.eat(food);
                newRandomFood();
            }
            else if(isDeath()) {
                root.getChildren().clear();

            }
        });
    }

    private boolean hit() {
        return food.intersects(snake.getBoundsInLocal());
    }


    private void createSnake() {
        snake = new Snake(WIDTH/2, HEIGHT/2, 10, 10);
        root.getChildren().add(snake);
    }
    @Override
    public void start(Stage stage) throws IOException {
         root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);
        stage.setTitle("snake");
        direction = Direction.UP;

        Runnable r = () -> {
            try {
                while (true) {
                    move();
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                throw  new IllegalArgumentException();
            }
        };
        createSnake();
        newRandomFood();
        Scene scene = new Scene(root);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (code.equals(KeyCode.UP)) {
                direction = Direction.UP;
            } else if (code.equals(KeyCode.DOWN)) {
                direction = Direction.DOWN;
            } else if (code.equals(KeyCode.LEFT)) {
                direction = Direction.LEFT;
            } else if (code.equals(KeyCode.RIGHT)) {
                direction = Direction.RIGHT;
            }
        });

        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.start();
    }

//    private void background(GraphicsContext graphicsContext) {
//        for (int i = 0; i <; i++) {
//
//        }
//
//    }

    private boolean isDeath() {
        for (int i = 0; i < snake.getLen(); i++) {
            if( snake.getX()  == snake.getTails().get(i).getX() -1&& snake.getY()  == snake.getTails().get(i).getY() -1 ) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        launch();
    }
}