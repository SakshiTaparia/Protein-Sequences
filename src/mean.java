import java.io.*;
import java.lang.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;

public class mean {

    public static void main (String[] args) throws Exception {

        Integer[] Singlet_Count = new Integer[26];
        Integer[][] Doublet_Count = new Integer[26][26];
        Integer[][][] Triplet_Count = new Integer[26][26][26];

        Integer[] Master_N = new Integer[18278];
        Integer[] Master_C = new Integer[18278];
        double[] Master_mean = new double[18278];

        for (int i =0; i<18278; i++) {

            Master_N[i] = 0;
            Master_C[i] = 0;
            Master_mean[i] = 0.0;

        }

        File fileName = new File ("sequences.txt");

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        Integer line_number = 1;
        String line = null;

        while (line_number <= 531836) {

            PrintStream o = new PrintStream(new File(line_number + ".txt"));
            System.setOut(o);

            for (int i=0; i<26; i++) {

                for (int j=0; j<26; j++){

                    for (int l=0; l<26; l++) {

                            Singlet_Count[i] = 0;
                            Doublet_Count[i][j] = 0;
                            Triplet_Count[i][j][l] = 0;
                    }
                }
            }

            line = br.readLine();
            line = line.trim();

            Integer length = line.length();

            for(int i = 0; i < length; i++)
                Singlet_Count[line.charAt(i) - 'A'] ++;

            Master_N[line.charAt(0) - 'A'] ++;
            Master_C[line.charAt(length-1) - 'A'] ++;

            for(int i = 0; i < length-1; i++)
                Doublet_Count[line.charAt(i) - 'A'][line.charAt(i+1) - 'A'] ++;

            Master_N[26 + (line.charAt(0) - 'A')*26 + (line.charAt(1) - 'A')] ++;
            Master_C[26 + (line.charAt(length-2) - 'A')*26 + (line.charAt(length-1) - 'A')] ++;

            
            for(int i = 0; i < length-2; i++)
                Triplet_Count[line.charAt(i) - 'A'][line.charAt(i+1) - 'A'][line.charAt(i+2) - 'A'] ++;

            Master_N[702 + (line.charAt(0) - 'A')*676 + (line.charAt(1) - 'A')*26 +(line.charAt(0) - 'A')] ++;
            Master_C[702 + (line.charAt(length-3) - 'A')*676 + (line.charAt(length-2) - 'A')*26 +(line.charAt(length-1) - 'A')] ++;

            Integer counter = 0;

            for (int i=0; i<26; i++) {

                System.out.println((Singlet_Count[i]*100.0)/length);

                Master_mean[counter] += (Singlet_Count[i]*100.0)/length;
                counter ++;
            }

            for (int i=0; i<26; i++) {

                for (int j=0; j<26; j++) {

                    System.out.println((Doublet_Count[i][j]*100.0)/(length-1));
                    Master_mean[counter] += (Doublet_Count[i][j]*100.0)/(length-1);
                    counter ++;
                }
            }

            for (int i=0; i<26; i++) {

                for (int j=0; j<26; j++) {

                    for (int k=0; k<26; k++) {

                        System.out.println((Triplet_Count[i][j][k]*100.0)/(length-2));
                        Master_mean[counter] += (Triplet_Count[i][j][k]*100.0)/(length-2);
                        counter ++;
                    }
                }
            } 

            line_number++;           
        }

        PrintStream d = new PrintStream(new File("master_Count.txt"));
        System.setOut(d);

        for (int i =0; i<18278; i++) {

            System.out.println( Master_mean[i]/(531836.0) + " " + Master_N[i] + " " + Master_C[i]);

        }
    }
}