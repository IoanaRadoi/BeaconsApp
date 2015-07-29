package algo;

import com.kilobolt.robotgame.Background;
import com.kilobolt.robotgame.GameScreen;

import java.util.Random;

/**
 * Created by Ioana.Radoi on 5/15/2015.
 */
public class Robot {

//    private double x;
//    private double y;
    private double orientation = 0;

    //cele 3 constante de noise
    private double forward_noise;
    private double turn_noise;
    private double sense_noise;

    //private double landmarks[][];  //beacons


    private double world_size;

    private double distances_beacons[];  //distanta pana la beacons
    private double measurements[];    //
    private double prob_should_be;   //cum ar trebui sa fie prob

    private Background bg1 = GameScreen.getBg1();
    private Background bg2 = GameScreen.getBg2();

    private double centerX = 100;
    private double centerY = 240;

    private boolean rightPressed;
    private boolean leftPressed;
    private boolean forwardPressed;


    public Robot() {
//        landmarks = new double[4][2];
//        //stabilire pozitie beacons (4 beacons)
//        landmarks[0][0] = 20;
//        landmarks[0][1] = 20;
//        landmarks[1][0] = 80;
//        landmarks[1][1] = 80;
//        landmarks[2][0] = 20;
//        landmarks[2][1] = 80;
//        landmarks[3][0] = 80;
//        landmarks[3][1] = 20;
        world_size = 100;
        forward_noise = 0;
        turn_noise = 0;
        sense_noise = 0;
        distances_beacons = new double[3];

        for (int k=0; k<3; k++){
            distances_beacons[k] = 0;
        }
        this.measurements = new double[100];
        orientation = 0;

    }




    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public double getForward_noise() {
        return forward_noise;
    }

    public void setForward_noise(double forward_noise) {
        this.forward_noise = forward_noise;
    }

    public double getTurn_noise() {
        return turn_noise;
    }

    public void setTurn_noise(double turn_noise) {
        this.turn_noise = turn_noise;
    }

    public double getSense_noise() {
        return sense_noise;
    }

    public void setSense_noise(double sense_noise) {
        this.sense_noise = sense_noise;
    }


    public double getWorld_size() {
        return world_size;
    }

    public void setWorld_size(int world_size) {
        this.world_size = world_size;
    }

    public double[] getDistances_beacons() {
        return distances_beacons;
    }

    public void setDistances_beacons(double[] distances_beacons) {
        for (int k=0; k<3; k++) {
            if (distances_beacons[k]!=0) {
                this.distances_beacons[k] = distances_beacons[k];
            }

            System.out.println("din interior k = " + k + " dist " + this.distances_beacons[k] + " venita " + distances_beacons[k]);
        }
    }

    public double[] getMeasurements() {
        return measurements;
    }

    public void setMeasurements(double[] measurements) {
        this.measurements = measurements;
    }

    public double getProb_should_be() {
        return prob_should_be;
    }

    public void setProb_should_be(double prob_should_be) {
        this.prob_should_be = prob_should_be;
    }


    public void setPos(double x, double y, double orientation){  //checked
        this.orientation = orientation;
    }

    public void setNoise(double new_f_noise, double new_t_noise, double new_s_noise){  //checked
        this.forward_noise = new_f_noise;
        this.turn_noise = new_t_noise;
        this.sense_noise = new_s_noise;
    }

    public void senseRobot(int[][] landmarks){  //distantele pana la beacons, pe care le primeste programul  //checked
        for(int i = 0; i< landmarks.length; i++){
            Random randomno = new Random();
            distances_beacons[i] = Math.sqrt(Math.pow(this.getCenterX() - landmarks[i][0],2) + Math.pow(this.getCenterY() - landmarks[i][1],2));
            //distances_beacons[i] += randomno.nextGaussian()* this.sense_noise;  //numar intre - sense_noise si + sense_noise
        }
    }

    public void robotMove(double turn, double forward){   //checked
        //TODO orientare
        Random randomno = new Random();
        this.orientation +=turn + randomno.nextGaussian()* this.turn_noise;   //ma rotesc
        this.orientation %= 2 * Math.PI;


        double dist = forward + randomno.nextGaussian()* this.forward_noise;  //ma misc in fata
//        x = x + Math.cos(orientation) * dist;
//        y = y + Math.sin(orientation) * dist;

        //TODO truncate dupa size
        //trunchiem ca sa incapa in spatiu
//        this.x %= world_size;
//        this.y %= world_size;
    }


//    public double functionGaussian(double mu, double sigma, double x){
//        return Math.exp(- (Math.pow((mu - x),2)) / Math.pow(sigma,2) / 2.0) / Math.sqrt(2.0 * 3.14159 * Math.pow(sigma, 2));
//    }

//    //TODO
//    public double probShouldBe(){
//        return this.prob_should_be;
//    }



    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }


    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }


    public void moveForward(double turn, double forward){

        //cand rotesc sagetica, rotesc imaginea cu totul, deci nu aveam nevoie la rotire sa tin minte cat era initial
        //pentru translatare inainte am nevoie sa stiu ce orientare are cu totul

//        this.orientation += turn;
//        this.orientation %= 2 * Math.PI;

//        this.centerX += Math.cos(turn) * forward;
//        this.centerY += Math.sin(turn) * forward;

        if (isForwardPressed()) {

                if (centerX>=780)
                    centerX=780;

                if (centerX<=20)
                    centerX=20;

                if (centerY<=20)
                    centerY=20;

                if(centerY>=570)
                    centerY=570;

                this.centerX += Math.cos(turn * (Math.PI / 180)) * forward;
                this.centerY -= Math.sin(turn * (Math.PI / 180)) * forward;

        }

    }


    public boolean isForwardPressed() {
        return forwardPressed;
    }

    public void setForwardPressed(boolean forwardPressed) {
        this.forwardPressed = forwardPressed;
    }




}
