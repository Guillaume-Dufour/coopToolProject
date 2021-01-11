package cooptool.utils;

import java.util.*;

/**
 * MapResourceBundle class
 */
public class MapResourceBundle extends ResourceBundle {

    /**
     * Map that stores the resources
     */
    private final Map<String, Object> map;

    /**
     * Constructor
     * @param objects Resources
     */
    public MapResourceBundle(Object[] objects) {

        map = new HashMap<>();

        for (int i = 0; i < objects.length; i++) {
            map.put(String.valueOf(i+1), objects[i]);
        }
    }

    @Override
    protected Object handleGetObject(String key) {
        return map.get(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(map.keySet());
    }
}
