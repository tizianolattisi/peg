package com.lattisi.peg.dsl

import com.lattisi.peg.engine.Problem
import com.lattisi.peg.engine.entities.IItem
import com.lattisi.peg.engine.entities.ItemType
import com.lattisi.peg.engine.entities.Metrics
import com.lattisi.peg.engine.entities.Segment
import com.lattisi.peg.engine.entities.Triangle

/**
 * User: tiziano
 * Date: 02/10/13
 * Time: 17:58
 */
class Language {

    Problem problem = new Problem()
    IItem item
    ItemType type

    def make(ItemType type){
        this.type = type
        this
    }

    // XXX: does not work
    def with(){
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

    }

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
