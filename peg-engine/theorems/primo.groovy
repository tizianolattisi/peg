package com.lattisi.peg.engine.theorems

import com.lattisi.peg.engine.entities.Segment

{ triangle1, triangle2 ->

    def metrics1 = [] as Set
    for( child in triangle1.children ){
        if( child instanceof Segment && child.metric ) metrics1.add(child.metric)
    }
    def metrics2 = [] as Set
    for( child in triangle2.children ){
        if( child instanceof Segment && child.metric ) metrics2.add(child.metric)
    }

    if( metrics1.intersect(metrics2).size()==2 ){
        return true
    }

    return false

}