import java.io.*;
import java.lang.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;

public class sd {

    public static void main (String[] args) throws Exception {

        PrintStream stdout = System.out;

        double[] mean = new double[18278];
        double[] sd = new double[18278];


        for (int i =0; i<18278; i++) {

            mean[i] = 0.0;
            sd[i] = 0.0;

        }

        File fileName = new File ("master_Count.txt");

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        Integer line_number = 1;
        String line = null;

        while (line_number <= 18278) {

            line = br.readLine();
            String[] splited = line.split(" ");

            mean[line_number -1] = Double.valueOf(splited[0]);

            line_number++;           
        }

        Integer file = 1;

        while (file <= 531836) {

            File file_name = new File (file + ".txt");
            BufferedReader b = new BufferedReader(new FileReader(file_name));

            System.out.println(file);

            line_number = 1;

            while (line_number <= 18278) {

                line = b.readLine();
                String[] s = line.split(" ");

                sd[line_number -1] += (Double.valueOf(s[0]) - mean[line_number -1])*(Double.valueOf(s[0]) - mean[line_number -1]) ;

                line_number++;           
            }

            file ++;           
        }

        PrintStream d = new PrintStream(new File("master_SD.txt"));
        System.setOut(d);

        for (int i =0; i<18278; i++) {

            System.out.println( java.lang.Math.sqrt(sd[i]/(531835.0)));

        }
    }
}