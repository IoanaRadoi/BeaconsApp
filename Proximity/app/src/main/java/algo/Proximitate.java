package algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Ioana.Radoi on 6/23/2015.
 */
public class Proximitate {

    private Map<String, Integer> valReceivedProximitate = new TreeMap<String, Integer>();

    public Proximitate() {
    }

    public Map<String, Integer> getValReceivedProximitate() {
        return valReceivedProximitate;
    }

    public void setValReceivedProximitate(Map<String, Integer> valReceivedProximitate) {
        this.valReceivedProximitate = valReceivedProximitate;
    }




    public String getMin(){
        Integer minValue=Integer.MIN_VALUE;
        String key = null;
        for (Map.Entry<String, Integer> entry: valReceivedProximitate.entrySet()) {
           if(entry.getValue() > minValue) {
               minValue = entry.getValue();
               key = entry.getKey();
           }
        }
        return key;
    }

}
