package com.lattisi.peg.dsl

import com.lattisi.peg.engine.Problem
import com.lattisi.peg.engine.entities.Item
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

        Problem problem = shell.getLanguage().getProblem()

        scan = { node, i ->
            println "  "*i + node
            if( node instanceof IContainer ){
                i++
                for(def child: node.getChildren() ){
                    scan child, i
                }
            }
        }

        for(Item item: problem.getItems().values()){
            println ""
            scan item, 0
        }

    }

}
