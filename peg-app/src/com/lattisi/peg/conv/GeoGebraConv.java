package com.lattisi.peg.conv;

import com.lattisi.peg.engine.Problem;
import com.lattisi.peg.engine.entities.Item;
import com.lattisi.peg.engine.entities.ItemType;
import com.lattisi.peg.engine.entities.Point;
import com.lattisi.peg.engine.entities.Segment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * User: tiziano
 * Date: 28/04/14
 * Time: 12:24
 */
public class GeoGebraConv {

    public static void export(Problem problem, String fileName){

        Integer X = 6;
        Integer Y = 0;
        String src="";
        for( Segment segment: problem.getSegments() ){
            Object[] points = segment.getPoints().toArray();
            Point first = (Point) points[0];
            Point second = (Point)points[1];
            src += MessageFormat.format("{0} = ({1}, {2})\n", first.getName(), 0, 0);
            src += MessageFormat.format("{0} = ({1}, {2})\n", second.getName(), 6, 0);
            src += MessageFormat.format("{0}{1} = Segment[{0},{1}]\n", first.getName(), second.getName());
        }
        System.out.println(src);

        String js = "function ggbOnInit() {\n";

        List<String> commands = new ArrayList<>();
        commands.add("A = (0, 0)");
        commands.add("B = (6, 0)");
        commands.add("C = (2, 4)");
        commands.add("AB = Segment[A, B]");
        commands.add("BC = Segment[B, C]");
        commands.add("CA = Segment[C, A]");

        for( String command: commands ){
            js += "ggbApplet.evalCommand(\"" + command + "\");\n";
        }

        js += "}";

        final StringBuilder sb = new StringBuilder();
        sb.append(js);


        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ZipOutputStream zos = new ZipOutputStream(fos);

            ZipEntry xmlZip = new ZipEntry("geogebra.xml");
            zos.putNextEntry(xmlZip);

            FileInputStream XmlFis = new FileInputStream("geogebra.xml");
            int len;
            byte[] buffer = new byte[1024];
            while( (len = XmlFis.read(buffer)) > 0 ){
                zos.write(buffer, 0, len);
            }
            XmlFis.close();

            ZipEntry jsZip = new ZipEntry("geogebra_javascript.js");
            zos.putNextEntry(jsZip);
            byte[] jsData = sb.toString().getBytes();
            zos.write(jsData, 0, jsData.length);

            zos.closeEntry();
            zos.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
