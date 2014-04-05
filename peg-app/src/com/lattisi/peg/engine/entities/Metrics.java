package com.lattisi.peg.engine.entities;

/**
 * User: tiziano
 * Date: 08/12/13
 * Time: 19:03
 */
public class Metrics {

    private static Integer triangleMeasuresIndex = 0;
    private static final String[] triangleMeasures = {"t", "t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8", "t9"};

    private static Integer angleMeasuresIndex = 0;
    private static final String[] angleMeasures = {"a", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10",
            "a11", "a12", "a13", "a14", "a15", "a16", "a17", "a17", "a18", "a19"};

    private static Integer segmentMeasuresIndex = 0;
    private static final String[] segmentMeasures = {"s", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9"};

    private static Integer directionMeasuresIndex = 0;
    private static final String[] directionMeasures = {"r", "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r8", "r9"};

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
