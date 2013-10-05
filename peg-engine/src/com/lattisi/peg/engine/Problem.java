package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.Base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 10:45
 */
public class Problem {

    private Map<String, Base> elements = new HashMap();

    public void addElement(Base element){
        elements.put(element.getName(), element);
    }

    public Map<String, Base> getElements(){
        return elements;
    }

}
