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
     *  Italian
     */

    def triangolo(String name){
        triangle(name)
    }
    def segmento(String name){
        segment(name)
    }
    def crea(Constraint theConstraint){
        create(theConstraint)
    }
    def crea(String name){
        create(name)
    }
    def estendi(Constraint theConstraint){
        extend(theConstraint)
    }
    def estendi(String name){
        extend(name)
    }
    def dichiara(String name){
        declare(name)
    }



}
