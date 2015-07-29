package com.kilobolt.robotgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import android.graphics.Color;
import android.graphics.Paint;

import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.Screen;


import algo.Particle;
import algo.Proximitate;
import algo.Robot;
import auxiliar.Constants;

public class GameScreen extends Screen {
    enum GameState {
        Ready, Running, Paused, GameOver
    }

    GameState state = GameState.Ready;

    // Variable Setup

    private static Background bg1, bg2;
    private static Robot robot;
    private static Proximitate proximitate;
    private int landmarks[][];

    private Image character;
    private Image particulax;
    private auxiliar.Pair pGasit, ultimP;
    private float angle;
    private int index_beacon;
    private long start;

    private ArrayList<Tile> tilearray = new ArrayList<Tile>();

    int livesLeft = 1;
    Paint paint, paint2;

    String gasit;

    public GameScreen(Game game) {
        super(game);

        // Initialize game objects here

        bg1 = new Background(0, 0);
        bg2 = new Background(2160, 0);
        robot = new Robot();

        proximitate = new Proximitate();

        landmarks = new int[3][2];
        landmarks[0][0] = 200;
        landmarks[0][1] = 220;
        landmarks[1][0] = 600;
        landmarks[1][1] = 220;
        landmarks[2][0] = 1000;
        landmarks[2][1] = 220;

        character = Assets.character;
        particulax = Assets.particulax;

        loadMap();

        // Defining a paint object
        paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        paint2 = new Paint();
        paint2.setTextSize(100);
        paint2.setTextAlign(Paint.Align.CENTER);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.WHITE);


        angle = 0;
        index_beacon = 0;
    }

    private void loadMap() {
        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        Scanner scanner = new Scanner(SampleGame.map);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // no more lines to read
            if (line == null) {
                break;
            }

            if (!line.startsWith("!")) {
                lines.add(line);
                width = Math.max(width, line.length());

            }
        }
        height = lines.size();

        for (int j = 0; j < 12; j++) {
            String line = (String) lines.get(j);
            for (int i = 0; i < width; i++) {

                if (i < line.length()) {
                    char ch = line.charAt(i);
                    Tile t = new Tile(i, j, Character.getNumericValue(ch));
                    tilearray.add(t);
                }

            }
        }

    }

    @Override
    public void update(float deltaTime) {

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        if (state == GameState.Ready)
            updateReady(touchEvents);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents) {

        if (touchEvents.size() > 0)
            state = GameState.Running;
    }


    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {

        // This is identical to the update() method from our Unit 2/3 game.

        // 1. All touch input is handled here:
        int len = touchEvents.size();

        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {

                if (inBounds(event, 0, 285, 65, 65)) {
                    angle += 22.5; //(PI/8)

                } else if (inBounds(event, 0, 350, 65, 65)) {
                    robot.setForwardPressed(true);


                } else if (inBounds(event, 0, 415, 65, 65)) {
                    angle -= 22.5; //(PI/8)

                }

            }

            if (event.type == TouchEvent.TOUCH_UP) {

                robot.setLeftPressed(false);
                robot.setRightPressed(false);
                robot.setForwardPressed(false);
            }

        }


        updateTiles();
        animate();
        angle %= 360;

        robot.moveForward(angle, 3);
    }

    @Override
    public void updateParticle() {


        gasit = proximitate.getMin();  //aici am id-ul beacon-ului cel mai apropiat

    }


    public static double maxWeights(double[] weights) {
        double max = weights[0];
        for (Double w : weights) {
            if (max < w)
                max = w;
        }
        return max;
    }

    //algoritm particles filtering, particulele cu greutate mare, le dublez, adica particulele cu probabilitate mica vor fi inlocuite cu
    //cele cu particule cu probabilitate mare, adica acestea cu probailitate mare se vor dubla
    public static Particle[] getProbabilitiesSetNewParticles(Particle[] particles) {
        //int sizeW = p3.size();
        Random rn = new Random();
        int index = rn.nextInt(Constants.NR_PARTICLES);  //luam random una din greutati, din cele Constants.NR_PARTICLES ale particulelor
        double beta = 0;

        double[] weights = new double[Constants.NR_PARTICLES];
        for (int k = 0; k < particles.length; k++) {
            weights[k] = particles[k].getWeight();
        }

        double max = maxWeights(weights);

        Particle[] part3 = new Particle[Constants.NR_PARTICLES];
        Random r2 = new Random();

        for (int i = 0; i < weights.length; i++) {
            beta += r2.nextDouble() * 2 * max;
            while (beta > weights[index]) {
                beta -= weights[index];
                index = (index + 1) % Constants.NR_PARTICLES;
            }
            try {
                part3[i] = (Particle) particles[index].clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            //System.out.println("Part i = " + i + " Val " + part3[i]);
        }

        return part3;

    }

    private boolean inBounds(TouchEvent event, int x, int y, int width,
                             int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y
                && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (inBounds(event, 0, 0, 800, 240)) {

                    if (!inBounds(event, 0, 0, 35, 35)) {
                        resume();
                    }
                }

                if (inBounds(event, 0, 240, 800, 240)) {
                    nullify();
                    goToMenu();
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {

    }

    private void updateTiles() {

        for (int i = 0; i < tilearray.size(); i++) {
            Tile t = (Tile) tilearray.get(i);
            t.update();
        }

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawImage(Assets.background, bg1.getBgX(), bg1.getBgY());
        g.drawImage(Assets.background, bg2.getBgX(), bg2.getBgY());

//proximity
        if (gasit!=null) {
            System.out.println("Gasit: " + gasit);
            if (gasit.equals("212/64069")) {
                g.drawImage(Assets.particulax, 100, 100);
            } else if (gasit.equals("212/64093")) {
                g.drawImage(Assets.particulax, 300, 100);
            } else if (gasit.equals("213/1321")) {
                g.drawImage(Assets.particulax, 500, 100);
            } else if (gasit.equals("212/64621")) {
                g.drawImage(Assets.particulax, 700, 100);
            } else if (gasit.equals("212/64087")) {
                g.drawImage(Assets.particulax, 100, 300);
            } else if (gasit.equals("212/64598")) {
                g.drawImage(Assets.particulax, 300, 300);
            } else if (gasit.equals("213/1311")) {
                g.drawImage(Assets.particulax, 500, 300);
            } else if (gasit.equals("213/1316")) {
                g.drawImage(Assets.particulax, 700, 300);
            } else if (gasit.equals("213/1323")) {
                g.drawImage(Assets.particulax, 100, 500);
            } else if (gasit.equals("22784/59692")) {
                g.drawImage(Assets.particulax, 300, 500);
            } else if (gasit.equals("55367/4626")) {
                g.drawImage(Assets.particulax, 500, 500);
            } else if (gasit.equals("62637/27728")) {
                g.drawImage(Assets.particulax, 700, 500);
            }
        }

        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();

    }

    private void paintTiles(Graphics g) {
        for (int i = 0; i < tilearray.size(); i++) {
            Tile t = (Tile) tilearray.get(i);
            if (t.type != 0) {
                g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY());
            }
        }
    }

    public void animate() {

    }

    private void nullify() {


        paint = null;
        bg1 = null;
        bg2 = null;
        robot = null;
        character = null;
        System.gc();

    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();
        g.drawARGB(155, 0, 0, 0);
        g.drawString("Tap to Start.", 400, 240, paint);

    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.button, 0, 0, 0, 195, 35, 35);

    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        // Darken the entire screen so you can display the Paused screen.
        g.drawARGB(155, 0, 0, 0);
        g.drawString("Resume", 400, 165, paint2);
        g.drawString("Menu", 400, 360, paint2);

    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1281, 801, Color.BLACK);
        g.drawString("GAME OVER.", 400, 240, paint2);
        g.drawString("Tap to return.", 400, 290, paint);

    }

    @Override
    public void pause() {
        if (state == GameState.Running)
            state = GameState.Paused;

    }

    @Override
    public void resume() {
        if (state == GameState.Paused)
            state = GameState.Running;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        pause();
    }

    private void goToMenu() {
        // TODO Auto-generated method stub
        game.setScreen(new MainMenuScreen(game));

    }

    public static Background getBg1() {
        // TODO Auto-generated method stub
        return bg1;
    }

    public static Background getBg2() {
        // TODO Auto-generated method stub
        return bg2;
    }

    public static Robot getRobot() {
        // TODO Auto-generated method stub
        return robot;
    }

    private static class Pair {
        public int x, y;

        public double distance(int x, int y) {
            return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
        }
    }


    public static Proximitate getProximitate() {
        return proximitate;
    }

    public static void setProximitate(Proximitate proximitate) {
        GameScreen.proximitate = proximitate;
    }
}