package com.lattisi.peg.dsl

import com.lattisi.peg.engine.Problem
import com.lattisi.peg.engine.entities.Direction
import com.lattisi.peg.engine.entities.ItemType
import com.lattisi.peg.engine.entities.Metrics
import com.lattisi.peg.engine.entities.Point
import com.lattisi.peg.engine.entities.Segment
import com.lattisi.peg.engine.entities.Triangle
import com.lattisi.peg.engine.entities.TriangleType

/**
 * User: tiziano
 * Date: 02/10/13
 * Time: 17:58
 */
class Language {

    Problem problem = new Problem()
    String item1Name
    String item2Name
    ItemType type

    /*
     *  make ItemType itemName with prop1:value1, prop2:value2
     */
    def make(ItemType type){
        // make triangle name "ABC" with type:scalene
        item1Name = null
        item2Name = null
        this.type = type                                                    // ItemType.triangle
        this
    }
    def name(String itemName) {
        def item = null
        switch( type ){
            case ItemType.triangle:
                item = Triangle.build(itemName)                             // Triangle "ABC"
                break
            case ItemType.segment:
                item = Segment.build(itemName)
                break
            case ItemType.direction:
                item = Direction.build(itemName)
                break
            default:
                break

        }
        if( item != null ){
            item1Name = item.name                                           // "ABC"
        }
        this
    }

    /*
     *  extend segmentName to pointName
     */
    def extend(String segmentName){
        // extend "AB" to "D" with metric:"AC"
        def item1 = Problem.find(segmentName[0])                            // Point "A"
        item1Name = item1.name                                              // "A"
        def item2 = Problem.find(segmentName[1])                            // Point "B"
        item2Name = item2.name                                              // "B"
        this
    }
    def to(String pointName){
        // extend "AB" to "D" with metric:"AC"
        def segmentName = item2Name+pointName                               // "BD"
        def segment = Segment.build(segmentName)                            // Segment "BD"
        def direction = Direction.build(segmentName)                        // Direction "BD"
        direction.addPoint(Point.build(item1Name))                          // add Point "A" to Direction "BD"
        item1Name = segment.name                                            // "BD"
        item2Name = null
        this
    }


    def with(Map map){
        for( key in map.keySet() ){
            this."$key"(map.get(key))
        }
        this
    }


    /*
     *  with properties
     */

    def type(TriangleType type){
        // make triangle name "ABC" with type:scalene
        if( !TriangleType.scalene.equals(type) ){
            def metric = Metrics.nextMetric(ItemType.segment)
            def triangle = Problem.find(item1Name, ItemType.triangle)       // Triangle "ABC"
            triangle.segments.get(0).setMetric(metric)
            triangle.segments.get(1).setMetric(metric)
            if( TriangleType.equilateral.equals(type) ){
                triangle.segments.get(2).setMetric(metric)
            }
        }
    }
    def metric(String itemName){
        // extend "AB" to "D" with metric:"AC"
        def metricItem = Problem.find(itemName)                             // Segment "AC"
        def metric
        if( metricItem.metric != null ){
            metric = metricItem.metric
        } else {
            metric = Metrics.nextMetric(ItemType.segment)
            metricItem.setMetric(metric)
        }
        def item1 = Problem.find(item1Name)                                 // Segment "BD"
        item1.setMetric(metric)
        println ""
    }


    /*
     * declare
     */

    def declare(String itemName){
        // declare "CD" equal "AC"
        item1Name = itemName                                                // "CD"
        item2Name = null
        type = null
        this
    }

    def equals(String itemName){
        def item1 = Problem.find(item1Name, null)                            // Segment "CD"
        def item2 = Problem.find(itemName, null)
        item2Name = itemName
        def metric
        if( item1.getMetric() != null && item2.getMetric() == null ){
            item2.setMetric(item1.getMetric())
        } else if( item2.getMetric() != null && item1.getMetric() == null ){
            item1.setMetric(item2.getMetric())
        } else {
            metric = Metrics.nextMetric(ItemType.segment)
            item1.setMetric(metric)
            item2.setMetric(metric)
        }
        item1Name=null
        item2Name = null
    }

}