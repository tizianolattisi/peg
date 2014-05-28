package com.lattisi.peg.dsl
/**
 * User: tiziano
 * Date: 04/10/13
 * Time: 22:24
 */
abstract class LanguageBaseScriptClass extends Script {

    /*
     *  ITEMS
     */
    def triangle(String name){
        this.binding.language.triangle(name)
    }
    def segment(String name){
        this.binding.language.triangle(name)
    }

    /*
     *  CREATE
     */
    def create(Constraint theConstraint){
        this.binding.language.create()
    }
    def create(String name){
        this.binding.language.create(name)
    }

    /*
     * EXTEND
     */
    def extend(Constraint theConstraint){
        this.binding.language.extend()
    }
    def extend(String name){
        this.binding.language.extend(name)
    }

    /*
     *  declare
     */
    def declare(String name){
        this.binding.language.declare(name)
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
