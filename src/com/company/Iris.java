package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Iris {
    public ArrayList<Double> properties;
    public String type = "none";
    private double distance;

    public Iris(ArrayList<Double> properties, String type){
        this.properties = properties;
        this.type = type;

    }

    public Iris(ArrayList<String> data){
        ArrayList<Double> properties = new ArrayList<Double>();
        Double temp;
        for (String str : data){
            try {
                temp = Double.parseDouble(str);
                properties.add(temp);
            } catch (NumberFormatException e){
                this.type = str;
            }
        }
        this.properties = properties;
    }

    public Double rootDistance(Iris second){
        double result = 0;
        double temp;
        for (int i = 0; i < properties.size(); i++){
            temp = properties.get(i) - second.getProperties().get(i);
            temp = temp*temp;
            result += temp;
        }
        return result;
    }

    public static ArrayList<Iris> assign(ArrayList<Iris> Train, ArrayList<Iris> Test){
        Scanner read = new Scanner(System.in);
        System.out.println("Insert parameter k: ");
        int k = read.nextInt();
        DoubleIntString[] tiny;
        DoubleIntString smol;
        int fulfil;
        ArrayList<DoubleIntString> result;
        boolean flag;
        int all = Test.size();
        int unchanged = 0;
        ArrayList<Iris> output = new ArrayList<>();

        for(int i = 0; i < Test.size(); i++) {
            tiny = new DoubleIntString[k];
            fulfil = k;
            result = new ArrayList<>();

            for (int j = 0; j < Train.size(); j++) {
                if (fulfil > 0) {
                    smol = new DoubleIntString(Test.get(i).rootDistance(Train.get(j)), Train.get(j).getType());
                    tiny[fulfil-1] = smol;
                    fulfil--;
                } else {
                    BubbleSort.ReverseSort(tiny);
                    if (tiny[0].getD() > Test.get(i).rootDistance(Train.get(j))) {
                        smol = new DoubleIntString(Test.get(i).rootDistance(Train.get(j)), Train.get(j).getType());
                        tiny[0] = smol;
                    }
                }
            }
            BubbleSort.ReverseSort(tiny);
            for (int j = tiny.length; j > 0; j--) {
                flag = true;
                for (int z = 0; z < result.size(); z++) {
                    if (result.get(z).getS().equals(tiny[j-1].getS())) {
                        result.get(z).increaseI();
                        flag = false;
                    }
                }
                if (flag) {
                    smol = new DoubleIntString(1, tiny[j-1].getS());
                    result.add(smol);
                }
            }
            tiny = result.toArray(new DoubleIntString[0]);
            BubbleSort.ReverseSortI(tiny);
            if (Test.get(i).getType().equals(tiny[0].getS())) {
                unchanged++;
            } else {
                Test.get(i).setType(tiny[0].getS());
            }
            output.add(Test.get(i));
        }
        System.out.println("Out of " + all +" Irises " + unchanged + " remained unchanged resulting in " + 100*unchanged/all + "% accuracy");
        return output;
    }

    public ArrayList<Double> getProperties() {
        return properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        String result = "";

        for(int i=0; i< properties.size();i++){
            result += properties.get(i);
            result += " ";
        }

        return "Iris{" +
                "properties=" + result +
                "type='" + type + '\'' +
                '}';
    }

    public String toStringExpanded() {
        String result = "";

        for(int i=0; i< properties.size();i++){
            result += properties.get(i);
            result += " ";
        }

        return "Iris{" +
                "properties=" + result +
                "type='" + type + '\'' +
                "distance=" + distance +
                '}';
    }
}
