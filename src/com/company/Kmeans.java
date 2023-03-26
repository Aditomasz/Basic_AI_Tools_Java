package com.company;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Kmeans {

    private static ArrayList<double[]> centroids = new ArrayList<>();
    private static ArrayList<Iris> irises;
    private static int k;
    private static int errors = 0;

    public Kmeans(int k, ArrayList<Iris> irises){
        Kmeans.irises = irises;
        Kmeans.k = k;
        Kmeans.randomCentroids();
    }

    public static void setup() {
        double temp;
        double a;
        double resultValue;
        int result;
        boolean ultimateFlag = true;
        boolean flag;
        int counter;
        double round;

        while(ultimateFlag) {
            for (int i = 0; i < irises.size(); i++) {
                temp = 0;

                for (int z = 0; z < irises.get(i).getProperties().size(); z++) {
                    a = irises.get(i).getProperties().get(z) - centroids.get(0)[z];
                    temp += a*a;
                }

                resultValue = temp;
                result = 0;

                for (int j = 1; j < centroids.size(); j++) {
                    temp = 0;
                    for (int z = 0; z < irises.get(i).getProperties().size(); z++) {
                        a = irises.get(i).getProperties().get(z) - centroids.get(j)[z];
                        temp += a * a;
                    }

                    if (temp < resultValue) {
                        resultValue = temp;
                        result = j;
                    }
                }

                irises.get(i).setType(String.valueOf(result));
                irises.get(i).setDistance(resultValue);

            }

            counter = 0;

            for (int i = 0; i < centroids.size(); i++) {
                flag = false;
                for (int j = 0; j < irises.size(); j++) {
                    if (Integer.valueOf(irises.get(j).getType()) == i) {
                        flag = true;
                    }
                }
                if (flag){
                    counter++;
                }
            }
            if (counter == centroids.size()) {
                ultimateFlag = false;
            } else{
                Kmeans.randomCentroids();
            }
        }

        Kmeans.print();

    }

    public static void newCentroids(){

        double temp;
        int amount;
        double round;

        for (int i = 0; i < centroids.size(); i++) {
            for (int j = 0; j < centroids.get(i).length; j++) {
                temp = 0;
                amount = 0;
                for (int z = 0; z < irises.size(); z++){
                    if (irises.get(z).getType().equals(String.valueOf(i))){
                        temp += irises.get(z).getProperties().get(j);
                        amount++;
                    }
                }
                round = Math.round((temp/amount)*10);
                round = round/10;
                centroids.get(i)[j] = round;
            }
        
        }

        System.out.println("New Centroids:");
        for (int i = 0; i < centroids.size(); i++) {
            System.out.println(Arrays.toString(centroids.get(i)));
        }
        System.out.println();

    }

    public static void randomCentroids(){
        centroids.clear();
        ArrayList<Double> max = new ArrayList<>();
        ArrayList<Double> min = new ArrayList<>();
        for (int i = 0; i < irises.get(0).getProperties().size(); i++) {
            max.add(irises.get(0).getProperties().get(i));
            min.add(irises.get(0).getProperties().get(i));
        }
        for (int i = 1; i < irises.size(); i++) {
            for (int j = 0; j < irises.get(i).getProperties().size(); j++) {
                if (irises.get(i).getProperties().get(j) > max.get(j)) {
                    max.set(j, irises.get(i).getProperties().get(j));
                }
                if (irises.get(i).getProperties().get(j) < min.get(j)) {
                    min.set(j, irises.get(i).getProperties().get(j));
                }
            }
        }

        double[] temp;
        double low;
        double high;
        double x;
        double random;

        for (int i = 0; i < k; i++) {
            temp = new double[max.size()];
            for (int j = 0; j < max.size(); j++) {
                low = min.get(j);
                high = max.get(j);
                random = new Random().nextDouble();
                x = low + (random * (high-low));
                x = Math.round(x*10);
                x = x/10;
                temp[j] = x;
            }
            centroids.add(temp);
        }
    }

    public static void notSoRandomCentroids(){
        ArrayList<Iris> newOne = new ArrayList<>(irises);
        int random;

        for (int i = 0; i > k; i++) {
            random = (int) (Math.random() * (newOne.size()));

            for (int j = 0; j < newOne.get(random).getProperties().size(); i++){
                centroids.get(i)[j] = newOne.get(random).getProperties().get(j);
                newOne.remove(random);
            }
        }

    }

    public static boolean loop() {
        double temp;
        double a;
        double resultValue;
        int result;
        boolean flag = false;
        double round;

        for (int i = 0; i < irises.size(); i++) {
            result = Integer.valueOf(irises.get(i).getType());
            resultValue = irises.get(i).getDistance();
            for (int j = 0; j < centroids.size(); j++) {
                temp = 0;
                for (int z = 0; z < irises.get(i).getProperties().size(); z++) {
                    a = irises.get(i).getProperties().get(z) - centroids.get(j)[z];
                    temp += a*a;
                }

                if (temp < resultValue) {
                    resultValue = temp;
                    result = j;
                }
            }
            irises.get(i).setDistance(resultValue);
            if (!String.valueOf(result).equals(irises.get(i).getType())) {
                irises.get(i).setType(String.valueOf(result));
                flag = true;
            }
        }
        return flag;
    }

    public static void print(){

        for (int j = 0; j < centroids.size(); j++) {
            System.out.println(Arrays.toString(centroids.get(j)));
            for (int i = 0; i < irises.size(); i++) {
                if (irises.get(i).getType().equals(String.valueOf(j))) {
                    System.out.println(irises.get(i).toStringExpanded());
                }
            }

        }
    }
}
