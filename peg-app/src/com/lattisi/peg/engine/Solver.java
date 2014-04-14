package com.lattisi.peg.engine;

/**
 *
 *
 * Definizione del problema
 * ------------------------
 *
 *  Dato un triangolo ABC si prolunghino i lati AC, BC, dalla parte di C,
 *  rispettivamente dei segmenti CD = BC e CE = AC.
 *  Sia H l'intersezione delle rette DE ed AB.
 *
 * create triangle name "ABC"
 * extend "AC" to "D" with measure:"BC"
 * extend "BC" to "E" with measure:"AC"
 * create segment name "ED"
 * extend "ED" to "H"
 * extend "AB" to "H"
 *
 *
 * Definizione della tesi
 * ----------------------
 *
 *  Dimostrare  che il triangolo AEH è isoscele.
 *
 * assert A1 on "BDH"
 *
 *
 * Strategie
 * ---------
 *
 * Esecuzione di un refresh del problema per far emergere le strutture implicite (triangoli e angoli, compresi
 * quelli piani).
 *
 *
 * Le segnature dei teoremi sono:
 *
 * - (Triangle, Triangle)
 * - (Triangle, Angle, Triangle, Angle) // gli angoli appartengono ai rispettivi triangoli
 * - (Direction, Direction)
 * - (Triangle, Segment, Segment) // i segmenti appartengono al triangolo
 *
 * Quindi possono essere ridotti (provando tutte le combinazioni interne) a:
 *
 * - (Triangle)
 * - (Triangle, Triangle)
 * - (Direction, Direction)
 *
 *
 * Le segnature delle operazioni sono
 *
 * - (Angle, Angle)
 *
 *
 * Estraggo al problema le tuple di item che corrispondono alle segnature possibili, e le applico, quindi verifico
 * l'asserzione della tesi.
 *
 * Come procederebbe uno studente?
 *
 *
 */

import com.lattisi.peg.dsl.Shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiziano on 05/04/14.
 */
public class Solver {

    private List<String> rows = new ArrayList<String>();

    public Solver(String definition, String thesis) {

        Shell shell = Shell.build();
        Problem problem = shell.getLanguage().getProblem();
        for( String line: definition.split("\\n") ){
            shell.evaluate(line);
            problem.refresh();
        }

        problem.refresh();
    }
}
