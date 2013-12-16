package com.lattisi.peg.dsl

import com.lattisi.peg.engine.Problem
import com.lattisi.peg.engine.entities.Item
import com.lattisi.peg.engine.entities.IContainer
import com.lattisi.peg.engine.entities.ItemType

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
        problem.refresh()

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

    void testFind() {
        Shell shell = Shell.build()
        shell.evaluate('make triangle name "ABC"')
        def problem = shell.getLanguage().getProblem()
        def item1 = problem.find("AB")
        def item2 = problem.find("BA")
        assert item1 == item2
    }

    void testLanguage() {
        /*
        def language = new Language()
        language.make(ItemType.triangle).with().name("ABC");
        def problem = language.getProblem()
        println "Test Language"
        println problem.getItems().values()
        */
    }

}
