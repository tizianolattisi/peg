package com.lattisi.peg.dsl

/**
 * User: tiziano
 * Date: 04/10/13
 * Time: 22:24
 */
abstract class LanguageBaseScriptClass extends Script {

    void make(Map map, ItemType type){
        this.binding.language.make(map, type)
    }

}
