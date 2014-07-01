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

    void test1() {

        Shell shell = Shell.build()
        Problem problem = shell.getLanguage().getProblem()
        String text = (new File("test/com/lattisi/peg/dsl/problem1.groovy")).text

        for( String line: text.split("\\n") ){
            shell.evaluate(line);
            problem.refresh();
        }

        problem.refresh()

        printProblem(problem)

    }

    void test2() {

        Shell shell = Shell.build()
        Problem problem = shell.getLanguage().getProblem()
        String text = (new File("test/com/lattisi/peg/dsl/problem2.groovy")).text

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
        shell.evaluate('create the triangle "ABC"')
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
        String problemCode = "create the triangle \"ABC\"    // or: create \"ABC\"\n" +
                "extend the segment \"AC\" to \"D\" with measure of \"BC\"\n" +
                "extend \"BC\" to \"E\" with measure of \"AC\"\n" +
                "create the segment \"ED\"\n" +
                "extend the segment \"ED\" to \"H\"\n" +
                "extend the segment \"AB\" to \"H\""

        Shell shell = Shell.build()
        shell.evaluate(problemCode)
        Problem problem = shell.getLanguage().getProblem()

        problem.refresh()

        // angoli opposti al vertice
        shell.evaluate("declare \"dce\" equals \"bca\" due \"NAA\"")

        // uguaglianza triangoli (due lati e un angolo)
        shell.evaluate("declare \"CED\" equals \"ABC\" due \"SAS\"")

        // angoli opposti a segmenti uguali
        shell.evaluate("declare \"cba\" equals \"edc\" due \"ETOA\"")

        // costruisco il segmento BD (troverà da solo il triangolo BCD)
        shell.evaluate("create the segment \"BD\"")
        problem.refresh()

        // il triangolo BCD è isoscele, quindi gli angoli cbd e cdb sono uguali
        shell.evaluate("declare \"cdb\" equals \"dbc\" due \"TICA\"")

        // edb e abd sono uguali per somma di angoli uguali
        shell.evaluate("declare \"edb\" equals \"abd\" due \"SEA\"")

        // hdb e hbd sono ugueli per differenza di angoli uguali
        shell.evaluate("declare \"hdb\" equals \"hbd\" due \"DEA\"")

        printProblem(problem)

    }

}
