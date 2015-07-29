package algo;


import com.kilobolt.RobotGameApp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import auxiliar.Pair;

import algofingerprinting.Beacon;

/**
 * Created by Ioana.Radoi on 6/22/2015.
 */
public class Fingerprinting {
    private final Map<String, Beacon> beacons = new HashMap<String, Beacon>();

    private Map<String, Integer> valReceived = new HashMap<String, Integer>();

    public Fingerprinting() {
    }

    public void init() {
        long start = System.currentTimeMillis();
        try {
            String root = "map";
            String[] orders = RobotGameApp.instance().getAssets().list(root);
            for (String order: orders) {
                int k = Integer.parseInt(order);
                String[] folders = RobotGameApp.instance().getAssets().list(root + "/" + order);
                for (String folder: folders) {
                    String[] coords = folder.split("\\.");
                    int i = Integer.parseInt(coords[0]);
                    int j = Integer.parseInt(coords[1]);
                    String[] files = RobotGameApp.instance().getAssets().list(root + "/" + order + "/" + folder);
                    for (String file: files) {
                        String id = file.substring(0, file.length() - 4).replace("_", "/");
                        map(RobotGameApp.instance().getAssets().open(root + "/" + order + "/" + folder + "/" + file), id, i, j, k - 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Fingerprinting.init: "  + beacons.size());
        System.out.println("Fingerprinting.init2: " + beacons);

        System.out.println("Fingerprinting.time: " + (System.currentTimeMillis() - start));
    }

    public Map<String, Integer> getValReceived() {
        return valReceived;
    }

    public void setValReceived(Map<String, Integer> valReceived) {
        this.valReceived = valReceived;
    }

    public Pair start() {
        Pair p = algorithm(valReceived);

        System.out.println("Ce mi-a dat: " + p.x + " " + p.y);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return p;

    }

    private void map(InputStream inputStream, String id, int i, int j, int k) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Beacon b = getBeacon(id);
        int index = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] vals = line.split("\t");
            b.probability[i][j][k][index++] = Float.parseFloat(vals[2]);
        }
        reader.close();
    }

    private Beacon getBeacon(String id) {
        Beacon b;
        if (beacons.containsKey(id)) {
            b = beacons.get(id);
        } else {
            b = new Beacon(id);
            beacons.put(id, b);
        }
        return b;
    }

    private Pair algorithm(Map<String, Integer> values) {
        if (beacons.size() == 12) {
            System.out.println("Fingerprinting.algorithm");
        }
        Pair pair = new Pair();
        double[][] probabilities = new double[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                probabilities[i][j] = 1;
            }
        }
        for (Map.Entry<String, Integer> entry : values.entrySet()) {
            Beacon b = beacons.get(entry.getKey());
            if (b != null) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 4; k++) {
                            probabilities[i][j] *= b.probability(i, j, k, entry.getValue());
                        }
                    }
                }
            }
        }
        double sum = 0;
        double probability = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (probabilities[i][j] > probability) {
                    probability = probabilities[i][j];
                    pair.x = i;
                    pair.y = j;
                }
                sum += probabilities[i][j];
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%.4f ", probabilities[i][j] / sum);
            }
            System.out.println();
        }
        return pair;
    }
}
