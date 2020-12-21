package cooptool.utils;

import java.util.*;

public class MapResourceBundle extends ResourceBundle {

    private final Map<String, Object> map;

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
