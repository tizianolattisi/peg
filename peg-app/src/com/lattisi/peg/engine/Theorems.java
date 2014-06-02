package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.*;
import java.util.stream.Collectors;

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
        THEOREMS_MAP.put("SAS", "congruentTriangleTwoSegmentsOneAngle");        // Teorema 3  (SAS)
        THEOREMS_MAP.put("SSS", "congruentTriangleSameSides");                  // Teorema 5  (SAS)
        THEOREMS_MAP.put("ETOA", "equalsTrianglesOppositeAngles");              // Teorema 6  (ETOA)
        THEOREMS_MAP.put("NAA", "notAdjacentAngles");                           // Teorema 8  (NAA)
        THEOREMS_MAP.put("TICA", "triangleIsoscelesCorrespondingAngles");       // Teorema 10 (TICA)
        THEOREMS_MAP.put("SEA", "sumOfEqualsAngles");                           //            (SEA)
        THEOREMS_MAP.put("DEA", "diffOfEqualsAngles");                          //            (DEA)
    }

    /*
     * Teorema 1
     * Dati due punti distinti A e B, esiste una ed una sola retta che li contiene entrambi.
     *
     */


    /*
     * Teorema 2
     * Due rette distinte non possono avere più di un punto in comune.
     *
     */


    /*
     * Teorema 3 (SAS)
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
     * Teorema 4 (ASA)
     * Secondo criterio di uguaglianza dei triangoli:
     * due triangoli che hanno rispettivamente uguali un lato e gli angoli ad esso adiacenti, sono uguali.
     *
     */
    public static Boolean congruentTriangleTwoAnglesOneSegment(Triangle triangle1, Triangle triangle2){
        return false; // TODO
    }


    /*
     * Teorema 5 (SSS)
     * Terzo criterio di uguaglianza dei triangoli:
     * due triangoli che hanno i tre lati rispettivamente uguali, sono uguali.
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
     * Teorema 6 (ETOA)
     * In due triangoli uguali a lati uguali sono opposti angoli uguali.
     *
     */
    public static Boolean equalsTrianglesOppositeAngles(Angle angle1, Angle angle2) {
        Problem problem = ProblemsTree.getProblem();
        Collection<Container> parents1 = problem.getParents(angle1);
        Collection<Triangle> triangles1 = parents1.stream().filter(c -> c instanceof Triangle).map(c -> (Triangle) c)
                .collect(Collectors.toCollection(ArrayList::new));
        Collection<Container> parents2 = problem.getParents(angle2);
        Collection<Triangle> triangles2 = parents2.stream().filter(c -> c instanceof Triangle).map(c -> (Triangle) c)
                .collect(Collectors.toCollection(ArrayList::new));
        for( Triangle triangle1: triangles1 ){
            for( Triangle triangle2: triangles2 ){
                if( triangle1.getMeasure() != null && triangle2.getMeasure() != null ){
                    if( triangle1.getMeasure().equals(triangle2.getMeasure()) ){
                        Segment segment1 = triangle1.getOppositeSegment(angle1);
                        Segment segment2 = triangle2.getOppositeSegment(angle2);
                        if( segment1.getMeasure() != null && segment2.getMeasure() != null ){
                            if( segment1.getMeasure().equals(segment2.getMeasure()) ){
                                equalizeItem(angle1, angle2);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    /*
     * Teorema 7
     * In due triangoli uguali ad angoli uguali sono opposti lati uguali.
     *
     */


    /*
     * Teorema 8 (NAA)
     * Angoli opposti al vertice sono uguali.
     *
     */
    public static Boolean notAdjacentAngles(Angle angle1, Angle angle2){
        Problem problem = ProblemsTree.getProblem();
        Set<Point> points1 = angle1.getPoints();
        Set<Point> points2 = angle2.getPoints();
        Set<Point> intersection = angle2.getPoints();
        intersection.retainAll(points1);
        Point central = intersection.iterator().next();
        if( intersection.size() != 1 ){
            return Boolean.FALSE;
        }
        List<Direction> directions = new ArrayList<>();
        for( Point point: points1 ){
            if( !point.equals(central) ){
                Direction direction = problem.findDirection(point, central);
                if( direction != null ) {
                    directions.add(direction);
                }
            }
        }
        for( Point point: points2 ){
            if( !directions.get(0).contains(point) && !directions.get(1).contains(point) ){
                return Boolean.FALSE;
            }
        }
        equalizeItem(angle1, angle2);
        return Boolean.TRUE;
    }

    /*
     * Teorema 9
     * Angoli supplementari di angoli uguali sono uguali
     *
     */


    /*
     * Teorema 10 (TICA)
     * In un triangolo isoscele gli angoli alla base sono uguali
     *
     */
    public static Boolean triangleIsoscelesCorrespondingAngles(Angle angle1, Angle angle2){
        Problem problem = ProblemsTree.getProblem();
        Collection<Container> parents1 = problem.getParents(angle1);
        Collection<Triangle> triangles1 = parents1.stream().filter(c -> c instanceof Triangle).map(c -> (Triangle) c)
                .collect(Collectors.toCollection(ArrayList::new));
        Collection<Container> parents2 = problem.getParents(angle2);
        Collection<Triangle> triangles = parents2.stream().filter(c -> c instanceof Triangle).map(c -> (Triangle) c)
                .collect(Collectors.toCollection(ArrayList::new));
        triangles.retainAll(triangles1);
        for( Triangle triangle: triangles ){
            List<Segment> segments = triangle.getSegments().stream().filter(s -> s.getMeasure() != null).collect(Collectors.toList());
            for( int i=0; i<segments.size(); i++ ){
                Segment s1 = segments.get(i);
                for( int j=1; j<segments.size(); j++ ){
                    Segment s2 = segments.get(j);
                    if( s1.getMeasure().equals(s2.getMeasure())){
                        if( (angle1.equals(triangle.getOppositeAngle(s1)) && angle2.equals(triangle.getOppositeAngle(s2))) ||
                                (angle2.equals(triangle.getOppositeAngle(s1)) && angle1.equals(triangle.getOppositeAngle(s2))) ){
                            equalizeItem(angle1, angle2);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /*
     * Angoli uguali per somma (SEA) o differenza (DEA) di angoli uguali
     */
    public static Boolean diffOfEqualsAngles(Angle angle1, Angle angle2){
        return sumOfEqualsAngles(angle1, angle2);
    }
    public static Boolean sumOfEqualsAngles(Angle angle1, Angle angle2){
        Problem problem = ProblemsTree.getProblem();
        Point center1 = angle1.getCentralPoint();
        Point center2 = angle2.getCentralPoint();
        Collection<Angle> angles = problem.getItems(ItemType.angle).stream().map(i -> (Angle) i).collect(Collectors.toCollection(ArrayList::new));
        List<Angle> anglesInCenter1 = new ArrayList<>();
        List<Angle> anglesInCenter2 = new ArrayList<>();
        for( Angle angle: angles ){
            if( !angle.equals(angle1) && angle.getMeasure() != null && center1.equals(angle.getCentralPoint()) ){
                anglesInCenter1.add(angle);
            }
            if( !angle.equals(angle2) && angle.getMeasure() != null && center2.equals(angle.getCentralPoint()) ){
                anglesInCenter2.add(angle);
            }
        }
        Set<String> measures1 = new HashSet<>();
        Set<String> measures2 = new HashSet<>();
        for( int i=0; i<anglesInCenter1.size(); i++ ){
            for( int j=1; j<anglesInCenter1.size(); j++ ){
                Angle res = anglesInCenter1.get(i).add(anglesInCenter1.get(j));
                if( res != null && res.equals(angle1)){
                    measures1.add(anglesInCenter1.get(i).getMeasure());
                    measures1.add(anglesInCenter1.get(j).getMeasure());
                }
            }
        }
        for( int i=0; i<anglesInCenter2.size(); i++ ){
            for( int j=1; j<anglesInCenter2.size(); j++ ){
                Angle res = anglesInCenter2.get(i).add(anglesInCenter2.get(j));
                if( res != null && res.equals(angle2) ){
                    measures2.add(anglesInCenter2.get(i).getMeasure());
                    measures2.add(anglesInCenter2.get(j).getMeasure());
                }
            }
        }
        if( measures1.size()>0 && measures2.size()>0 ){
            if( measures1.containsAll(measures2) ){
                equalizeItem(angle1, angle2);
                return true;
            }
        }
        return false;
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
