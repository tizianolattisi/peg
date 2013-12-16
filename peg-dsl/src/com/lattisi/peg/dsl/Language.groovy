package com.lattisi.peg.dsl

import com.lattisi.peg.engine.Problem
import com.lattisi.peg.engine.entities.IItem
import com.lattisi.peg.engine.entities.ItemType
import com.lattisi.peg.engine.entities.Metrics
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
    IItem item
    ItemType type

    /*
     *  make ItemType itemName with prop1:value1, prop2:value2
     */
    def make(ItemType type){
        this.type = type
        this
    }
    def name(String itemName) {
        switch( type ){
            case ItemType.triangle:
                item = Triangle.build(itemName)
                break
            case ItemType.segment:
                item = Segment.build(itemName)
                break
            default:
                break

        }
        this
    }

    /*
     *  extend segmentName to pointName
     */
    def extend(String segmentName){
        item = Problem.find(segmentName[-1])
        this
    }
    def to(String pointName){
        def segmentName = item.name[-1]+pointName
        item = Segment.build(segmentName)
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
        if( !TriangleType.scalene.equals(type) ){
            def metric = Metrics.nextMetric(ItemType.segment)
            item.segments.get(0).setMetric(metric)
            item.segments.get(1).setMetric(metric)
            if( TriangleType.equilateral ){
                item.segments.get(2).setMetric(metric)
            }
        }
    }
    def metric(String itemName){
        def metricItem = Problem.find(itemName)
        def metric
        if( metricItem.metric != null ){
            metric = metricItem.metric
        } else {
            metric = Metrics.nextMetric(ItemType.segment)
            metricItem.setMetric(metric)
        }
        item.setMetric(metricItem.metric)
    }


    /*
     * declare
     */

    def declare(String itemName){
        this.item = Problem.find(itemName, null)
        this
    }

    def equals(String itemName){
        IItem item2 = Problem.find(itemName, null)
        def metric
        if( item.getMetric() != null && item2.getMetric() == null ){
            item2.setMetric(item.getMetric())
        } else if( item2.getMetric() != null && item.getMetric() == null ){
            item.setMetric(item2.getMetric())
        } else {
            metric = Metrics.nextMetric(ItemType.segment)
            item.setMetric(metric)
            item2.setMetric(metric)
        }
        item=null
    }

}