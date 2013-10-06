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

    void make(Map map, EntityType type) {

        def element
        def name = map["name"]
        switch( type ){
            case EntityType.triangle:
                element = Triangle.build(name, problem)
                break
            case EntityType.segment:
                element = Segment.build(name, problem)
                break
            default:
                break

        }
        problem.addElement(element)

    }



}
