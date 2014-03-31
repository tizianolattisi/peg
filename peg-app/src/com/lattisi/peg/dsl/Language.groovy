package com.lattisi.peg.dsl

import com.lattisi.peg.engine.ProblemsTree
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

    Problem problem = ProblemsTree.getProblem();
    String item1Name
    String item2Name
    String theoremName
    ItemType type

    /*
     *  create ItemType itemName with prop1:value1, prop2:value2
     */
    def create(ItemType type){
        // Language.create(ItemType.triangle).name("ABC").with(type:"scalene")
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
        Problem problem = ProblemsTree.getProblem();
        def item1 = problem.find(segmentName[0])                            // Point "A"
        item1Name = item1.name                                              // "A"
        def item2 = problem.find(segmentName[1])                            // Point "B"
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
    def on(String item1Name){
        on(item1Name, null, null, null)
    }
    def on(String item1Name, String item2Name){
        on(item1Name, item2Name, null, null)
    }
    def on(String item1Name, String item2Name, String item3Name){
        on(item1Name, item2Name, item3Name, null)
    }
    def on(String item1Name, String item2Name, String item3Name, String item4Name){
        Problem problem = ProblemsTree.getProblem();
        def methodName = Theorems.THEOREMS_MAP.get(theoremName)
        def item1 = problem.find(item1Name)
        def item2 = null
        def item3 = null
        def item4 = null
        if( item2Name!=null ){
            item2 = problem.find(item2Name)
        }
        if( item3Name!=null ){
            item3 = problem.find(item3Name)
        }
        if( item4Name!=null ){
            item4 = problem.find(item4Name)
        }
        if( item1 != null && item2 != null && item3 != null && item4 != null ){
            Theorems."$methodName"(item1, item2, item3, item4)
        } else if( item1 != null && item2 != null && item3 != null ){
            Theorems."$methodName"(item1, item2, item3)
        } else if( item1 != null && item2 != null ){
            Theorems."$methodName"(item1, item2)
        } else if( item1 != null ){
            Theorems."$methodName"(item1)
        }

        problem.refresh()
        this
    }

    /*
     * sum and difference
     */
    def sum(String measurable1Name){
        // sum "abc" and "cbd"
        Problem problem = ProblemsTree.getProblem();
        item1Name = measurable1Name
        this
    }
    def difference(String measurable1Name){
        // difference "abc" and "cbd"
        Problem problem = ProblemsTree.getProblem();
        item1Name = measurable1Name
        this
    }
    def and(String measurable2Name){
        Problem problem = ProblemsTree.getProblem();
        def item1 = problem.find(item1Name)            // Angle "abc"
        def item2 = problem.find(measurable2Name)      // Angle "cbd"
        problem.sum(item1, item2)
    }


    /*
     *  with properties
     */

    def type(TriangleType type){
        // create triangle name "ABC" with type:scalene
        if( !TriangleType.scalene.equals(type) ){
            Problem problem = ProblemsTree.getProblem();
            def metric = Metrics.nextMetric(ItemType.segment)
            def triangle = problem.find(item1Name, ItemType.triangle)       // Triangle "ABC"
            triangle.segments.get(0).setMeasure(metric)
            triangle.segments.get(1).setMeasure(metric)
            if( TriangleType.equilateral.equals(type) ){
                triangle.segments.get(2).setMeasure(metric)
            }
        }
    }
    def measure(String itemName){
        // extend "AB" to "D" with measure:"AC"
        Problem problem = ProblemsTree.getProblem();
        def measureItem = problem.find(itemName)                             // Segment "AC"
        def measure
        if( measureItem.measure != null ){
            measure = measureItem.measure
        } else {
            measure = Metrics.nextMetric(ItemType.segment)
            measureItem.setMeasure(measure)
        }
        def item1 = problem.find(item1Name)                                 // Segment "BD"
        item1.setMeasure(measure)
        println ""
    }


    /*
     * declare
     */

    def declare(String itemName){
        // declare "CD" equals "AC"
        item1Name = itemName                                                // "CD"
        item2Name = null
        type = null
        this
    }

    def equals(String itemName){
        Problem problem = ProblemsTree.getProblem();
        def item1 = problem.find(item1Name, null)                            // Segment "CD"
        def item2 = problem.find(itemName, null)
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