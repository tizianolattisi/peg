package com.lattisi.peg.dsl

import com.lattisi.peg.engine.entities.Base

/**
 * User: tiziano
 * Date: 02/10/13
 * Time: 18:15
 */
class DslTest extends GroovyTestCase {

    void test() {

        Shell shell = Shell.build()
        shell.evaluate(new File("test/com/lattisi/peg/dsl/command.groovy"))

        for(Base element: shell.getLanguage().getProblem().getElements().values()){
            println ""
            println element.getName() + " - " + element
            for(def child: element.getChildren() ){
                println child
            }
        }

    }

}
