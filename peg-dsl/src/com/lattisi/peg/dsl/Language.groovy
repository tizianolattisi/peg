package com.lattisi.peg.dsl

import com.lattisi.peg.engine.Problem
import com.lattisi.peg.engine.entities.Segment
import com.lattisi.peg.engine.entities.Triangle

/**
 * User: tiziano
 * Date: 02/10/13
 * Time: 17:58
 */
class Language {

    Problem problem = new Problem()

    void make(Map map, ItemType type) {

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



}
