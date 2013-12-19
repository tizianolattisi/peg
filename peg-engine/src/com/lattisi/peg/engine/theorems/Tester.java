package com.lattisi.peg.engine.theorems;

import com.lattisi.peg.engine.entities.Triangle;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.*;
import java.net.URL;

/**
 * User: tiziano
 * Date: 19/12/13
 * Time: 08:38
 */
public class Tester {

    public static Boolean applica(String theoremName, Triangle triangle1, Triangle triangle2){

        URL url = Tester.class.getResource("/");
        String theoremFile = url.getPath().concat("../../../theorems/").concat(theoremName).concat(".groovy");

        String closure = null;
        /*
        URL resource = Tester.class.getResource("/com/lattisi/peg/engine/theorems/README");
        String theoremFile = resource.getPath().substring(0, resource.getPath().length()-6)
                .concat(theoremName).concat(".txt");
                */
        File file = new File(theoremFile);
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            closure = new String(chars);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if( closure == null ){
            return null;
        }

        Binding binding = new Binding();
        binding.setVariable("triangle1", triangle1);
        binding.setVariable("triangle2", triangle2);

        GroovyShell shell = new GroovyShell(binding);
        String code = closure + "(triangle1, triangle2)";
        Boolean res = (Boolean) shell.evaluate(code);
        return res;
    }

}
