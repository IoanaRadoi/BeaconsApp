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
    private final Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();

    public void add(String id, Integer value) {
        synchronized (map) {
            getForId(id).add(value);
        }
    }

    public Map<String, List<Integer>> get() {
        Map<String, List<Integer>> clone;
        synchronized (map) {
            clone = new HashMap<String, List<Integer>>(map);
            map.clear();
        }
        return clone;
    }

    public List<Integer> getForId(String id) {
        if (map.containsKey(id)) {
            return map.get(id);
        } else {
            List<Integer> list = new ArrayList<Integer>();
            map.put(id, list);
            return list;
        }
    }


}
