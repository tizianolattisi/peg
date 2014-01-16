package com.lattisi.peg.dsl

import com.lattisi.peg.engine.Problem
import com.lattisi.peg.engine.Theorems
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
    String theoremName
    ItemType type

    /*
     *  create ItemType itemName with prop1:value1, prop2:value2
     */
    def create(ItemType type){
        // create triangle name "ABC" with type:scalene
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
        // extend "AB" to "D" with measure:"AC"
        def item1 = Problem.find(segmentName[0])                            // Point "A"
        item1Name = item1.name                                              // "A"
        def item2 = Problem.find(segmentName[1])                            // Point "B"
        item2Name = item2.name                                              // "B"
        this
    }
    def to(String pointName){
        // extend "AB" to "D" with measure:"AC"
        def segmentName = item2Name+pointName                               // "BD"
        def segment = Segment.build(segmentName)                            // Segment "BD"
        def directionName = item1Name + item2Name                           // "AB"
        def direction = Direction.build(directionName)                      // Direction "AB"
        direction.addPoint(Point.build(pointName))                          // add Point "D" to Direction "AB"
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
     *  apply theorem to item1 and item2
     */
    def apply (String theoremName){
        // apply "10.8" on "ad", "be"
        this.theoremName = theoremName
        this
    }
    def on(String item1Name, String item2Name){
        def direction1 = Problem.findDirection(item1Name)
        def direction2 = Problem.findDirection(item2Name)
        def methodName = Theorems.THEOREMS_MAP.get(theoremName)
        Theorems."$methodName"(direction1, direction2)
        this
    }


    /*
     *  with properties
     */

    def type(TriangleType type){
        // create triangle name "ABC" with type:scalene
        if( !TriangleType.scalene.equals(type) ){
            def metric = Metrics.nextMetric(ItemType.segment)
            def triangle = Problem.find(item1Name, ItemType.triangle)       // Triangle "ABC"
            triangle.segments.get(0).setMeasure(metric)
            triangle.segments.get(1).setMeasure(metric)
            if( TriangleType.equilateral.equals(type) ){
                triangle.segments.get(2).setMeasure(metric)
            }
        }
    }
    def measure(String itemName){
        // extend "AB" to "D" with measure:"AC"
        def measureItem = Problem.find(itemName)                             // Segment "AC"
        def measure
        if( measureItem.measure != null ){
            measure = measureItem.measure
        } else {
            measure = Metrics.nextMetric(ItemType.segment)
            measureItem.setMeasure(measure)
        }
        def item1 = Problem.find(item1Name)                                 // Segment "BD"
        item1.setMeasure(measure)
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
        if( item1.getMeasure() != null && item2.getMeasure() == null ){
            item2.setMeasure(item1.getMeasure())
        } else if( item2.getMeasure() != null && item1.getMeasure() == null ){
            item1.setMeasure(item2.getMeasure())
        } else {
            metric = Metrics.nextMetric(ItemType.segment)
            item1.setMeasure(metric)
            item2.setMeasure(metric)
        }
        item1Name=null
        item2Name = null
    }

}