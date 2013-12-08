package com.lattisi.peg.dsl

import com.lattisi.peg.engine.Problem
import com.lattisi.peg.engine.entities.IItem
import com.lattisi.peg.engine.entities.ItemType
import com.lattisi.peg.engine.entities.Segment
import com.lattisi.peg.engine.entities.Triangle

/**
 * User: tiziano
 * Date: 02/10/13
 * Time: 17:58
 */
class Language {

    Problem problem = new Problem()

    String item1Name =null;

    def make(Map map, ItemType type) {

        def item
        def name = map["name"]
        switch( type ){
            case ItemType.triangle:
                item = Triangle.build(name)
                break
            case ItemType.segment:
                item = Segment.build(name)
                break
            default:
                break

        }

    }

    def declare(String itemName){
        this.item1Name = itemName
    }

    def equals(String itemName){
        IItem item1 = Problem.find(this.item1Name, null)
        IItem item2 = Problem.find(itemName, null)
        def metric = "s"
        item1.setMetric(metric)
        item2.setMetric(metric)
        item1Name=null
    }

}
