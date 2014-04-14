package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.Triangle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tiziano on 05/04/14.
 */
public class Assertions {

    public static final Map<String, String> ASSERTIONS_MAP;
    static
    {
        ASSERTIONS_MAP = new HashMap<String, String>();
        ASSERTIONS_MAP.put("A1", "triangleIsIsosceles");
    }

    public static Boolean triangleIsIsosceles(Triangle triangle){
        // TODO
        return Boolean.TRUE;
    }

}
