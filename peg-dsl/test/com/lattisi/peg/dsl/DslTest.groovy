package com.lattisi.peg.dsl

import com.lattisi.peg.engine.entities.Base
import com.lattisi.peg.engine.entities.IContainer

/**
 * User: tiziano
 * Date: 02/10/13
 * Time: 18:15
 */
class DslTest extends GroovyTestCase {

    def scan

    void test() {

        Shell shell = Shell.build()
        shell.evaluate(new File("test/com/lattisi/peg/dsl/command.groovy"))

        scan = { node, i ->
            println "  "*i + node
            if( node instanceof IContainer ){
                i++
                for(def child: node.getChildren() ){
                    scan child, i
                }
            }
        }

        for(Base element: shell.getLanguage().getProblem().getElements().values()){
            println ""
            scan element, 0
        }

    }

}
