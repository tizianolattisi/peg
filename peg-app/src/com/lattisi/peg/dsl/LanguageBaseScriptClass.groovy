package com.lattisi.peg.dsl

import com.lattisi.peg.engine.entities.ItemType

/**
 * User: tiziano
 * Date: 04/10/13
 * Time: 22:24
 */
abstract class LanguageBaseScriptClass extends Script {

    /*
     *  create ItemType itemName with prop1:value1, prop2:value2
     */
    def create(ItemType type){
        this.binding.language.create(type)
    }

    /*
     *  declare
     */
    def declare(String itemName){
        this.binding.language.declare(itemName)
    }

    /*
     * extend
     */
    def extend(String segmentName){
        this.binding.language.extend(segmentName)
    }

    /*
     * apply
     */
    def apply(String theoremName){
        this.binding.language.apply(theoremName)
    }

}
