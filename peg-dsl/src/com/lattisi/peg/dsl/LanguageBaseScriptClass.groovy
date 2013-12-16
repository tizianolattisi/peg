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
     * make
     */
    def make(ItemType type){
        this.binding.language.make(type)
    }

    /*
     *  declare
     */
    def declare(String item1){
        this.binding.language.declare(item1)
    }

}
