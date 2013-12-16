package com.lattisi.peg.dsl

import com.lattisi.peg.engine.entities.IItem
import com.lattisi.peg.engine.entities.ItemType

/**
 * User: tiziano
 * Date: 04/10/13
 * Time: 22:24
 */
abstract class LanguageBaseScriptClass extends Script {

    /*
     *  make ItemType itemName with prop1:value1, prop2:value2
     */
    def make(ItemType type){
        this.binding.language.make(type)
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

}
