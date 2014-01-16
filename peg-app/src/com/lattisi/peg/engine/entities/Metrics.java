package com.lattisi.peg.engine.entities;

/**
 * User: tiziano
 * Date: 08/12/13
 * Time: 19:03
 */
public class Metrics {

    private static Integer triangleMeasuresIndex = 0;
    private static final String[] triangleMeasures = {"t", "t1", "t2", "t3"};

    private static Integer angleMeasuresIndex = 0;
    private static final String[] angleMeasures = {"a", "a1", "a2", "a3"};

    private static Integer segmentMeasuresIndex = 0;
    private static final String[] segmentMeasures = {"s", "s1", "s2", "s3"};

    private static Integer directionMeasuresIndex = 0;
    private static final String[] directionMeasures = {"r", "r1", "r2", "r3"};

    public static String nextMetric(ItemType itemType){
        if( itemType.equals(ItemType.triangle) ){
            return triangleMeasures[triangleMeasuresIndex++];
        } else if( itemType.equals(ItemType.segment) ){
            return segmentMeasures[segmentMeasuresIndex++];
        } else if( itemType.equals(ItemType.angle) ){
            return angleMeasures[angleMeasuresIndex++];
        } else if( itemType.equals(ItemType.direction) ){
            return directionMeasures[directionMeasuresIndex++];
        }
        return null;
    }

}
