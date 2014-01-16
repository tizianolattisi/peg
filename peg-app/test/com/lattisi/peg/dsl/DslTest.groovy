package com.lattisi.peg.dsl

import com.lattisi.peg.engine.Problem
import com.lattisi.peg.engine.entities.AbstractItem
import com.lattisi.peg.engine.entities.Container

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
            if( node instanceof Container ){
                i++
                for(def child: node.getChildren() ){
                    scan child, i
                }
            }
        }

        for(AbstractItem item: problem.getItems().values()){
            println ""
            scan item, 0
        }

    }

    void testFind() {
        Shell shell = Shell.build()
        shell.evaluate('create triangle name "ABC"')
        def problem = shell.getLanguage().getProblem()
        def item1 = problem.find("AB")
        def item2 = problem.find("BA")
        assert item1 == item2
    }

    void testLanguage() {
        /*
        def language = new Language()
        language.create(ItemType.triangle).with().name("ABC");
        def problem = language.getProblem()
        println "Test Language"
        println problem.getItems().values()
        */
    }

}
