package com.lattisi.peg.dsl
/**
 * User: tiziano
 * Date: 04/10/13
 * Time: 22:24
 */
abstract class LanguageBaseScriptClass extends Script {

    /*
     *  create ItemType itemName with prop1:value1, prop2:value2
     */
    def create(Constraint theConstraint){
        this.binding.language.create()
    }
    def triangle(String name){
        this.binding.language.triangle(name)
    }

    /*
     *  declare
     */
    /*
    def declare(String itemName){
        this.binding.language.declare(itemName)
    }*/

    /*
     * extend
     */
    def extend(String segmentName){
        this.binding.language.extend(segmentName)
    }

    /*
     * apply
     */
    /*
    def apply(String theoremName){
        this.binding.language.apply(theoremName)
    }*/

    /*
     * sum
     */
    /*
    def sum(String measurableName){
        this.binding.language.sum(measurableName)
    }*/

    /*
     * difference
     */
    /*
    def difference(String measurableName){
        this.binding.language.difference(measurableName)
    }*/

    /*
     * check
     */
    /*
    def check(String assertion){
        this.binding.language.check(assertion)
    }*/

}
