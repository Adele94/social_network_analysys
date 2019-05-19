package ru.icmit.vk;

/**
 * Created by Adele on 16/05/2019.
 */

import Jama.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;


public class EmpericalDistribution {


    private static int coefSize = 3;

    public static double[] z = new double[coefSize];

    public static void main(String[] args) throws IOException {

         /* write x intervals */
        int studSize = 100;

        double[] x = new double[studSize];
        double[] y = new double[studSize];
        double[][] f = new double[coefSize][studSize];

        BufferedReader readerX = new BufferedReader(new FileReader("fileXOverlap.txt"));
        String line;
        List<String> lines = new ArrayList<String>();
        while ((line = readerX.readLine()) != null) {
            lines.add(line);
        }
        readerX.close();
        String[] linesAsArray = lines.toArray(new String[lines.size()]);
        for (int i = 0; i < linesAsArray.length; i++)
            x[i] = Double.valueOf(linesAsArray[i]);

        /* write y distribution */
        BufferedReader readerY = new BufferedReader(new FileReader("fileYOverlap.txt"));
        String line2;
        List<String> lines2 = new ArrayList<String>();
        while ((line2 = readerY.readLine()) != null) {
            lines2.add(line2);
        }
        readerY.close();
        String[] linesAsArray2 = lines2.toArray(new String[lines2.size()]);
        for (int i = 0; i < linesAsArray2.length; i++)
            y[i] = Double.valueOf(linesAsArray2[i]);


        for (int i = 0; i < studSize; i++) {
            f[0][i] = 1;
        }
        for (int k = 0; k < studSize; k++) {
            f[1][k] = Math.log(x[k]);
            f[2][k] = Math.sqrt(Math.log(x[k] + 1));
        }

        EmpericalDistribution H = new EmpericalDistribution();
        boolean b = H.test(f, y, studSize);
        //if (b) {
        double R = H.R(f, y, studSize);
        FileWriter writerR = new FileWriter("ROverlap.txt");
        try {
            writerR.write(String.valueOf(R));
            writerR.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //}

        /* Find Probability distribution density. */
        double[] P = H.P(z, x, studSize);
        FileWriter writerP = new FileWriter("POverlap.txt");
        try {
            for (int i = 0; i < x.length; i++) {
                writerP.write(String.valueOf(P[i]));
                writerP.append('\n');
                writerP.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public double Y(double x[][], int k) {
        double y = z[0];
        for (int i = 1; i < coefSize; i++) {
            y += z[i] * x[i][k];
        }
        return y;
    }

    public boolean test(double[][] f, double[] y, int studSize) {
        Matrix A1 = new Matrix(f);
        Matrix B1 = A1.transpose();
        Matrix F1 = A1.times(B1);
        if (F1.det() != 0) {
            Matrix F4 = F1.inverse();
            Matrix F2 = F4.times(A1);
            Matrix C = new Matrix(y, studSize);
            Matrix F3 = F2.times(C);
            z = F3.getColumnPackedCopy();
            return true;
        } else {
            out.println("Not suit for us!");
            return false;
        }
    }

    public double R(double[][] f, double[] yy, int studSize) {
        double r = 0, S1 = 0, S2 = 0, S3 = 0;
        double[] u1 = new double[studSize];
        double[] u2 = new double[studSize];
        for (int i = 0; i < studSize; i++) {
            S3 += yy[i];
            u1[i] = 0;
        }

        S3 = S3 / studSize;

        for (int m = 0; m < studSize; m++) {
            double y = Y(f, m);
            u1[m] = (y - yy[m]) * (y - yy[m]);
            S1 += u1[m];
            u2[m] = (S3 - yy[m]) * (S3 - yy[m]);
            S2 += u2[m];
        }
        r = 1 - S1 / S2;
        return r;
    }

    public double[] P(double[] z, double[] x, int studSize) {
        double[] P = new double[studSize];
        for (int i = 0; i < x.length; i++)
            P[i] = (z[1] / x[i]) + (z[2] / (2 * (x[i] + 1) * Math.sqrt(Math.log(x[i] + 1))));
        return P;
    }

}
