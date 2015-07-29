package algofingerprinting;

import java.util.Arrays;

/**
 * Created by Ioana.Radoi on 6/22/2015.
 */

public class Beacon {
    public final String id;
    public final float[][][][] probability = new float[3][4][4][60];  //linie, coloana, numarul setului, a cata valuare

    public Beacon(String id) {
        this.id = id;
    }

    public float probability(int i, int j, int k, int val) {
        try {
            float p = probability[i][j][k][val+99];

            return  p != 0 ? p : 0.0001f;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Beacon{" +
                "id='" + id + '\'' +
                ", probability=" + Arrays.toString(probability) +
                '}';
    }
}
