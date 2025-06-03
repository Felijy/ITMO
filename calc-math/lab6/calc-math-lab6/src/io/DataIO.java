package io;

import java.io.*;
import java.util.*;

public class DataIO {
    public static double[][] readData(String path) throws IOException {
        List<Double> xs=new ArrayList<>(), ys=new ArrayList<>();
        BufferedReader br=new BufferedReader(new FileReader(path));
        String line;
        while((line=br.readLine())!=null){
            String[] parts=line.split(",");
            xs.add(Double.parseDouble(parts[0])); ys.add(Double.parseDouble(parts[1]));
        }
        br.close();
        int n=xs.size();
        double[] x=new double[n], y=new double[n];
        for(int i=0;i<n;i++){x[i]=xs.get(i);y[i]=ys.get(i);}
        return new double[][]{x,y};
    }

}