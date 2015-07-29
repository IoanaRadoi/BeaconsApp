package algo;

import org.apache.commons.math3.analysis.function.Gaussian;

import java.util.ArrayList;
import java.util.Random;

import auxiliar.Constants;

/**
 * Created by Ioana.Radoi on 5/15/2015.
 */
public class MainClass {

    //calculam greutatea maxima a tuturor particulelor
    public static double maxWeights(double [] weights){
        double max = weights[0];
        for(Double w:weights){
            if(max<w)
                max = w;
        }
        return  max;
    }

    //preluam un numar Double Random
    public static double getNextRandDouble(double start, double end){
        double random = new Random().nextDouble();
        double result = start + (random * (end - start));
        return result;
    }

    //algoritm particles filtering, particulele cu greutate mare, le dublez, adica particulele cu probabilitate mica vor fi inlocuite cu
    //cele cu particule cu probabilitate mare, adica acestea cu probailitate mare se vor dubla
    public static Particle[] getProbabilitiesSetNewParticles(Particle[] particles){
        //int sizeW = p3.size();
        Random rn = new Random();
        int index = rn.nextInt(1000);  //luam random una din greutati, din cele 1000 ale particulelor
        double beta = 0;

        double [] weights = new double[1000];
        for (int k=0; k<particles.length; k++){
            weights[k] = particles[k].getWeight();
        }

        double max = maxWeights(weights);

        Particle [] part3 = new Particle[1000];
        Random r2 = new Random();

        for(int i=0; i<weights.length; i++){
            beta+=r2.nextDouble()*2*max;
            while (beta > weights[index]){
                beta-=weights[index];
                index = (index + 1) % 1000;
            }
            try {
                part3[i] = (Particle)particles[index].clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            //System.out.println("Part i = " + i + " Val " + part3[i]);
        }

        return part3;

    }


    public static void main(String[] args) throws CloneNotSupportedException {

        for (int i=0; i<20; i++) {
            System.out.println(auxiliar.NumberGenerator.randomDouble(200, 350));
        }
    }
}
