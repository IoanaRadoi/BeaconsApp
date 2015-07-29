/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

import java.util.Random;

/**
 *
 * @author Gabriel.Gutu
 */
public class NumberGenerator {
    
    public static double randomDouble(double a, double b) {
        return randomDouble(a, b, 0.0);
    }
    
    public static double randomDouble(double a, double b, double error) {
        if (a > b) {
            double temp = a;
            a = b;
            b = temp;
        }
        a = a - error;
        b = b + error;
        Random r = new Random();
        return a + (b - a) * r.nextDouble();
    }
    
    public static int randomInteger(int a, int b) {
        return randomInteger(a, b, 0);
    }
    
    public static int randomInteger(int a, int b, int error) {
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }
        a = a - error;
        b = b + error;
        Random r = new Random();
        int d = r.nextInt(b - a);

        // Debugger.printlnIfEnabled("next random ");
        // Debugger.printlnIfEnabled(d);
        return a + d;

    }
    
    public static double randomDoubleGaussian(double a, double b) {
        return randomDoubleGaussian(a, b, 0.0);
    }
    
    private final static Random rg = new Random();
    
    public static double randomDoubleGaussian(double a, double b, double error) {
        if (a > b) {
            double temp = a;
            a = b;
            b = temp;
        }
        a = a - error;
        b = b + error;
        return (a + b) / 2 + ((b - a) / 2) * rg.nextGaussian();
    }
    
    public static double randomIntegerGaussian(int a, int b) {
        return randomIntegerGaussian(a, b, 0);
    }
    
    public static double randomIntegerGaussian(int a, int b, int error) {
        return Math.round(randomDoubleGaussian(a, b, error));
    }
    
}
