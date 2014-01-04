package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.Segment;
import com.lattisi.peg.engine.entities.Triangle;

import java.util.*;

/**
 * User: tiziano
 * Date: 03/01/14
 * Time: 15:15
 */
public class Theorems {

    private static List<String> getMetrics(Triangle triangle){
        List<String> metrics = new ArrayList<String>();
        for( Segment segment: triangle.getSegments() ){
            if( segment.getMetric() != null ){
                metrics.add(segment.getMetric());
            }
        }
        return metrics;
    }

    public static Boolean congruentTriangleSameSides(Triangle triangle1, Triangle triangle2){
        List<String> metrics1 = getMetrics(triangle1);
        List<String> metrics2 = getMetrics(triangle2);
        Collections.sort(metrics1);
        Collections.sort(metrics2);
        return metrics1.equals(metrics2);
    }

}
