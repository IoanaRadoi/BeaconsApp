package algo;

import org.apache.commons.math3.analysis.function.Gaussian;

import java.util.Random;

import auxiliar.Constants;

/**
 * Created by Ioana.Radoi on 5/28/2015.
 */
public class Particle implements Cloneable {


    private double x;
    private double y;
    private double weight;
    private double orientation;
    private static double forward_noise;
    private static double turn_noise;
    private static double sense_noise;

    //private static double landmarks[][];  //beacons
    private static double world_size;
    private static double world_size_x;
    private static double world_size_y;


    public Particle() {
        world_size_x = 1200;
        world_size_y = 550;

        Random randomno = new Random();
        //ii dam particulei la initializare un x si un y random, si o orientare random
        x = randomno.nextDouble() * world_size_x;
        y = auxiliar.NumberGenerator.randomDouble(200, 350);//randomno.nextDouble() * world_size_y;
        weight = 0;
        orientation = randomno.nextDouble() * 2 * Math.PI;
        orientation %= 2 * Math.PI;
        sense_noise = 50;
        turn_noise = Math.PI / 4;
        forward_noise = 100;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }


    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
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

    public void setNoise(double new_f_noise, double new_t_noise, double new_s_noise) {
        this.forward_noise = new_f_noise;
        this.turn_noise = new_t_noise;
        this.sense_noise = new_s_noise;
    }

    //calculez probabilitatea pentru fiecare particula
    //care este probabilitatea ca distantele de la particula la toate beacon-urile sa se mapeze pe distantele de la device la toate beaconurile
    public double measurementProb(double[] measurements, int landmarks[][]) {

        double prob = 1.0;

        double dist = 0;
        for (int i = 0; i < landmarks.length; i++) {
            //calculez distanta de la particula la beaconu-ul curent
            dist = Math.sqrt(Math.pow(this.x - landmarks[i][0], 2) + Math.pow(this.y - landmarks[i][1], 2));
            //calculez care este probabilitatea ca distanta sa se mapeze pe distanta obtinuta de la beaconu-ul curent fata de robot
            Gaussian gaussian = new Gaussian(dist, this.sense_noise);
            if (gaussian.value(measurements[i]) == 0.0) {
                prob *= auxiliar.Constants.PROB_ZERO;

            } else {
                prob *= gaussian.value(measurements[i]);
            }
        }

        return prob;
    }


    public Particle(double x, double y, double weight, double orientation) {
        this.x = x;
        this.y = y;
        this.weight = weight;
        this.orientation = orientation;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Particle(this.x, this.y, this.weight, this.orientation);
    }
}
