package algofingerprinting;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RssiReader {

    private Map<String, Map<Long, Integer>> beaconValues = new HashMap<String, Map<Long, Integer>>();

    public RssiReader(int i, int j) {

        if (i > 2 || j > 3) {
            throw new IndexOutOfBoundsException();
        }
        File folder = new File("data/" + i + "." + j);
        for (File file: folder.listFiles()) {
            try {
                addBeacon(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void addBeacon(File file) throws IOException {
        String id = file.getName();
        id = id.substring(0, id.length() - 4);
        Map<Long, Integer> values = new HashMap<Long, Integer>();
        Scanner in = new Scanner(file);
        while (in.hasNextLong()) {
            values.put(in.nextLong(), in.nextInt());
            in.next();
        }
        in.close();
        beaconValues.put(id, values);
    }



    public Map<String, Integer> getValueBeacon() {

        Map<String, Integer> mapFin = new HashMap<String, Integer>();

        //beaconsValues -> toate valorile fiecarui beacon de la o coordonata timp de 60 de secunde
        for (Map.Entry<String, Map<Long, Integer>> entry: beaconValues.entrySet()) {
            Map<Long, Integer> values = entry.getValue();

            ArrayList<Integer> valAllRSSI = new ArrayList<Integer>();

            for(Map.Entry<Long, Integer> entry1: values.entrySet()){
                Integer valRSSI = entry1.getValue();
                valAllRSSI.add(valRSSI);
            }

            Integer popular = findPopular(valAllRSSI);

            mapFin.put(entry.getKey(), popular);
        }

        return mapFin;
    }

    public static int findPopular(List<Integer> a) {
        if (a == null || a.size() == 0)
            return 0;

        Collections.sort(a);

        int previous = a.get(0);
        int popular = previous;
        int count = 1;
        int maxCount = 1;

        for (int i = 1; i < a.size(); i++) {
            if (a.get(i) == previous)
                count++;
            else {
                if (count > maxCount) {
                    popular = a.get(i - 1);
                    maxCount = count;
                }
                previous = a.get(i);
                count = 1;
            }
        }

        return count > maxCount ? a.get(a.size() - 1) : popular;

    }

}
