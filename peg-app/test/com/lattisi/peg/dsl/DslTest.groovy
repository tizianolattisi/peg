package com.lattisi.peg.dsl

import com.lattisi.peg.engine.Problem
import com.lattisi.peg.engine.entities.AbstractItem
import com.lattisi.peg.engine.entities.Container
import com.lattisi.peg.engine.entities.Measurable

/**
 * User: tiziano
 * Date: 02/10/13
 * Time: 18:15
 */
class DslTest extends GroovyTestCase {

    def scan

    void test() {

        Shell shell = Shell.build()
        Problem problem = shell.getLanguage().getProblem()
        //shell.evaluate(new File("test/com/lattisi/peg/dsl/command.groovy"))
        String text = (new File("test/com/lattisi/peg/dsl/command2.groovy")).text

        for( String line: text.split("\\n") ){
            shell.evaluate(line);
            problem.refresh();
        }

        problem.refresh()

        printProblem(problem)

    }

    private void printProblem(Problem problem) {
        scan = { node, i ->
            def label = "  " * i + node
            if (node instanceof Measurable && node.measure != null) {
                label += " - " + node.measure
            }
            println label
            if (node instanceof Container) {
                i++
                for (def child : node.getChildren()) {
                    scan child, i
                }
            }
        }

        for (AbstractItem item : problem.getItemsMap().values()) {
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
        def language = new LanguageGroovy()
        language.create(ItemType.triangle).with().name("ABC");
        def problem = language.getProblem()
        println "Test LanguageGroovy"
        println problem.getItems().values()
        */
    }

    /*
     *  Dato un triangolo ABC si prolunghino i lati AC, BC, dalla parte di C, rispettivamente dei segmenti CD = BC e CE = AC.
     *  Sia H l'intersezione delle rette DE ed AB.
     *  Dimostrare  che il triangolo AEH è isoscele.
     */
    void testProblem() {
        String problemCode = "create triangle name \"ABC\"\n" +
                "extend \"AC\" to \"D\" with measure:\"BC\"\n" +
                "extend \"BC\" to \"E\" with measure:\"AC\"\n" +
                "create segment name \"ED\"\n" +
                "extend \"ED\" to \"H\"\n" +
                "extend \"AB\" to \"H\""

        Shell shell = Shell.build()
        shell.evaluate(problemCode)
        Problem problem = shell.getLanguage().getProblem()

        problem.refresh()

        // angoli opposti al vertice
        shell.evaluate("apply \"T8\" on \"ad\", \"bc\"")

        // uguaglianza triangoli (due lati e un angolo)
        shell.evaluate("apply \"T3\" on \"CED\", \"ABC\"")

        // angoli opposti a segmenti uguali
        shell.evaluate("apply \"T6\" on \"ABC\", \"cba\", \"CED\", \"edc\"")

        // costruisco il segmento BD (troverà da solo il triangolo BCD)
        shell.evaluate("create segment name \"BD\"")
        problem.refresh()

        // il triangolo BCD è isoscele, quindi gli angoli cbd e cdb sono uguali
        shell.evaluate("apply \"T10\" on \"BCD\", \"BC\", \"CD\"")

        // l'angolo edb è somma di edc e cdb
        shell.evaluate("sum \"edc\" and \"cdb\"")

        // l'angolo abd è somma di cba e dbc
        shell.evaluate("sum \"cba\" and \"dbc\"")

        // l'angolo hdb è la differenza tra l'angolo piano edh e l'angolo edb
        shell.evaluate("difference \"edh\" and \"edb\"")

        // l'angolo dbh è la differenza tra l'angolo piano abh e l'angolo abd
        shell.evaluate("difference \"abh\" and \"abd\"")

        printProblem(problem)

    }

}
