package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.*;

import java.util.*;

/**
 * User: tiziano
 * Date: 03/01/14
 * Time: 15:15
 */
public class Theorems {

    public static final Map<String, String> THEOREMS_MAP;
    static
    {
        THEOREMS_MAP = new HashMap<String, String>();
        THEOREMS_MAP.put("10.3", "congruentTriangleTwoSegmentsOneAngle");
        THEOREMS_MAP.put("10.5", "congruentTriangleSameSides");
        THEOREMS_MAP.put("10.8", "equalOppositeAngles");
    }

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
        Collection<Angle> angles1 = triangle1.getAngles(true);
        Collection<Angle> angles2 = triangle2.getAngles(true);
        for( Angle angle1: angles1 ){
            for( Angle angle2: angles2 ){
                if( angle1.getMeasure().equals(angle2.getMeasure()) ){
                    List<Segment> segments1 = new ArrayList(triangle1.getSegmentsAround(angle1));
                    List<Segment> segments2 = new ArrayList(triangle2.getSegmentsAround(angle2));
                    if( segments1.get(0).getMeasure() != null
                            && segments1.get(1).getMeasure() != null
                            && segments2.get(0).getMeasure() != null
                            && segments2.get(1).getMeasure() != null ){
                        if( (segments1.get(0).getMeasure().equals(segments2.get(0).getMeasure())
                                && segments1.get(1).getMeasure().equals(segments2.get(1).getMeasure())) ||
                                (segments1.get(0).getMeasure().equals(segments2.get(1).getMeasure())
                                        && segments1.get(1).getMeasure().equals(segments2.get(0).getMeasure()))
                                ){
                            equalizeItem(triangle1, triangle2);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
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
        List<String> measures1 = getSegmentMeasures(triangle1);
        if( measures1.size()<3 ){
            return false;
        }
        List<String> measures2 = getSegmentMeasures(triangle2);
        if( measures2.size()<3 ){
            return false;
        }
        Collections.sort(measures1);
        Collections.sort(measures2);
        if( measures1.equals(measures2) ){
            equalizeItem(triangle1, triangle2);
            return true;
        }
        return false;
    }

    /*
     * Teorema 10.6
     * In due triangoli uguali a lati uguali sono opposti angoli uguali.
     *
     */
    public static Boolean equalsOppositeAngles(Triangle triangle1, Angle angle1, Triangle triangle2, Angle angle2) {
        if( !triangle1.equals(triangle2) ){
            return false;
        }
        return true; // TODO: completare
    }


    /*
     * Teorema 10.7
     * In due triangoli uguali ad angoli uguali sono opposti lati uguali.
     *
     */


    /*
     * Teorema 10.8
     * Angoli opposti al vertice sono uguali.
     *
     */
    public static Boolean equalOppositeAngles(Direction direction1, Direction direction2){
        Point point = direction1.intersecate(direction2);
        if( point != null ){
            Problem problem = ProblemsTree.getProblem();
            List<Point> points1 = direction1.getOrderedPoints();
            Integer i = points1.indexOf(point);
            List<Point> points2 = direction2.getOrderedPoints();
            Integer j = points2.indexOf(point);
            if( i>0 && i< points1.size()-1 &&
                    j>0 && j< points2.size()-1 ){
                Angle angle1 = problem.findAngle(points1.get(i - 1), point, points2.get(j - 1));
                if( angle1 == null ){
                    angle1 = Angle.build(points1.get(i - 1), point, points2.get(j - 1));
                    problem.addItem(angle1);
                }
                Angle angle2 = problem.findAngle(points1.get(i + 1), point, points2.get(j + 1));
                if( angle2 == null ){
                    angle2 = Angle.build(points1.get(i + 1), point, points2.get(j + 1));
                    problem.addItem(angle2);
                }
                Angle angle3 = problem.findAngle(points1.get(i - 1), point, points2.get(j + 1));
                if( angle3 == null ){
                    angle3 = Angle.build(points1.get(i - 1), point, points2.get(j + 1));
                    problem.addItem(angle3);
                }
                Angle angle4 = problem.findAngle(points1.get(i + 1), point, points2.get(j - 1));
                if( angle4 == null ){
                    angle4 = Angle.build(points1.get(i + 1), point, points2.get(j - 1));
                    problem.addItem(angle4);
                }
                equalizeItem(angle1, angle2);
                equalizeItem(angle3, angle4);
            }
        }
        return Boolean.FALSE;
    }



    /*
     * Metodi di supporto
     */

    private static List<String> getSegmentMeasures(Triangle triangle){
        List<String> metrics = new ArrayList<String>();
        for( Segment segment: triangle.getSegments() ){
            if( segment.getMeasure() != null ){
                metrics.add(segment.getMeasure());
            }
        }
        return metrics;
    }

    private static List<String> getAngleMeasures(Triangle triangle){
        List<String> measures = new ArrayList<String>();
        for( Angle angle: triangle.getAngles() ){
            if( angle.getMeasure() != null ){
                measures.add(angle.getMeasure());
            }
        }
        return measures;
    }

    /*
     * Item1 and item2 got the same measure
     */
    public static void equalizeItem(Measurable item1, Measurable item2){
        if( item1.getMeasure() != null ){
            item2.setMeasure(item1.getMeasure());
        } else if( item2.getMeasure() != null ){
            item1.setMeasure(item2.getMeasure());
        } else {
            String measure = Metrics.nextMetric(((Item) item1).getType());
            item1.setMeasure(measure);
            item2.setMeasure(measure);
        }

    }

}
