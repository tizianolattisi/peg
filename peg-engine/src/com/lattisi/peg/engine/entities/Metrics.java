package com.lattisi.peg.engine.entities;

/**
 * User: tiziano
 * Date: 08/12/13
 * Time: 19:03
 */
public class Metrics {

    private static Integer triangleMetricIndex = 0;
    private static final String[] triangleMetrics = {"t", "t1", "t2", "t3"};

    private static Integer angleMetricIndex = 0;
    private static final String[] angleMetrics = {"a", "a1", "a2", "a3"};

    private static Integer segmentMetricIndex = 0;
    private static final String[] segmentMetrics = {"s", "s1", "s2", "s3"};

    private static Integer directionMetricIndex = 0;
    private static final String[] directionMetrics = {"r", "r1", "r2", "r3"};

    public static String nextMetric(ItemType itemType){
        if( itemType.equals(ItemType.triangle) ){
            return triangleMetrics[triangleMetricIndex++];
        } else if( itemType.equals(ItemType.segment) ){
            return segmentMetrics[segmentMetricIndex++];
        } else if( itemType.equals(ItemType.angle) ){
            return angleMetrics[angleMetricIndex++];
        } else if( itemType.equals(ItemType.direction) ){
            return directionMetrics[directionMetricIndex++];
        }
        return null;
    }

}
