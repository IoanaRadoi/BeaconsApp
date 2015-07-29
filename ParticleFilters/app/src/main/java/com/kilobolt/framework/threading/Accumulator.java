package com.kilobolt.framework.threading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ioana.Radoi on 6/12/2015.
 *
 */
public class Accumulator {
    private final Map<String, List<Double>> map = new HashMap<String, List<Double>>();

    public void add(String id, double value) {
        synchronized (map) {
            getForId(id).add(value);
        }
    }

    public Map<String, List<Double>> get() {
        Map<String, List<Double>> clone;
        synchronized (map) {
            clone = new HashMap<String, List<Double>>(map);
            map.clear();
        }
        return clone;
    }

    public List<Double> getForId(String id) {
        if (map.containsKey(id)) {
            return map.get(id);
        } else {
            List<Double> list = new ArrayList<Double>();
            map.put(id, list);
            return list;
        }
    }


}
