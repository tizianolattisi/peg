package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.Angle;
import com.lattisi.peg.engine.entities.Segment;
import com.lattisi.peg.engine.entities.Triangle;

import java.util.*;

/**
 * User: tiziano
 * Date: 03/01/14
 * Time: 15:15
 */
public class Theorems {

    /*
     * Teorema 10.1
     * Dati due punti distinti A e B, esiste una ed una sola retta che li contiene entrambi.
     *
     */


    /*
     * Teorema 10.2
     * Due rette distinte non possono avere più di un punto in comune.
     *
     */


    /*
     * Teorema 10.3
     * Primo criterio di uguaglianza dei triangoli:
     * due triangoli che hanno rispettivamente uguali due lati e l'angolo fra di essi compreso, sono uguali.
     *
     */
    public static Boolean congruentTriangleTwoSegmentsOneAngle(Triangle triangle1, Triangle triangle2){
        Boolean nullFound=false;
        Collection<Segment> segments1 = triangle1.getSegments(true);
        Collection<Segment> segments2 = triangle2.getSegments(true);
        if( segments1.size()<2 || segments2.size()<2 ){
            return false;
        }
        return false; // TODO: complete
    }

    /*
     * Teorema 10.4
     * Secondo criterio di uguaglianza dei triangoli:
     * due triangoli che hanno rispettivamente uguali un lato e gli angoli ad esso adiacenti, sono uguali.
     *
     */
    public static Boolean congruentTriangleTwoAnglesOneSegment(Triangle triangle1, Triangle triangle2){
        return false; // TODO
    }


    /*
     * Teorema 10.5
     * Terzo criterio di uguaglianza dei triangoli:
     * due triangoli che hanno i tre lati rispettivament uguali, sono uguali.
     *
     */
    public static Boolean congruentTriangleSameSides(Triangle triangle1, Triangle triangle2){
        List<String> metrics1 = getSegmentMetrics(triangle1);
        if( metrics1.size()<3 ){
            return false;
        }
        List<String> metrics2 = getSegmentMetrics(triangle2);
        if( metrics2.size()<3 ){
            return false;
        }
        Collections.sort(metrics1);
        Collections.sort(metrics2);
        return metrics1.equals(metrics2);
    }

    /*
     * Teorema 10.6
     * In due triangoli uguali a lati uguali sono opposti ancoli uguali.
     *
     */





    /*
     * Metodi di supporto
     */

    private static List<String> getSegmentMetrics(Triangle triangle){
        List<String> metrics = new ArrayList<String>();
        for( Segment segment: triangle.getSegments() ){
            if( segment.getMetric() != null ){
                metrics.add(segment.getMetric());
            }
        }
        return metrics;
    }

    private static List<String> getAngleMetrics(Triangle triangle){
        List<String> metrics = new ArrayList<String>();
        for( Angle angle: triangle.getAngles() ){
            if( angle.getMetric() != null ){
                metrics.add(angle.getMetric());
            }
        }
        return metrics;
    }

    public static void equalizeTriangles(Triangle triangle1, Triangle triangle2){

    }

}
