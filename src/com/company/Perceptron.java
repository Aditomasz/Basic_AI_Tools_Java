package com.company;

import java.util.ArrayList;

public class Perceptron {
    private ArrayList<Double> weight;
    private double threshold = 1;
    private boolean outcome;
    private String type = "Iris-setosa";
    private double constant;

    public Perceptron(int length, double constant){
        weight = new ArrayList<>();
        double temp;
        this.constant = constant;

        for (int i = 0; i < length; i++){
            temp = Math.random();
            weight.add(temp);
        }
    }

    public void train(Iris iris) {
        double result = 0;
        double temp;

        for (int i = 0; i < weight.size(); i++) {
            temp = weight.get(i) * iris.properties.get(i);
            result += temp;
        }

        if (result >= threshold){
            outcome = true;
        } else {
            outcome = false;
        }

        if (outcome && !iris.getType().equals(type)){
            delta(iris);
        } else if(!outcome && iris.getType().equals(type)){
            delta(iris);
        }
    }

    public void delta(Iris iris) {
        ArrayList<Double> alphaWeight = new ArrayList<>();
        double dya;
        double nTreshold;

        if (outcome) {
            dya = -1;
        } else {
            dya = 1;
        }

        dya = dya * constant;

        nTreshold = threshold*dya;

        for (int i = 0; i < iris.properties.size(); i++) {
            alphaWeight.add(iris.properties.get(i)*dya);
        }
        for (int i = 0; i < weight.size(); i++) {
            weight.set(i, weight.get(i)+alphaWeight.get(i));
        }
        threshold += nTreshold;
    }

    public void test(ArrayList<Iris> input) {
        double result;
        double temp;
        int setosa = 0;
        int virginica = 0;
        int sError = 0;
        int sUnchanged;
        int vUnchanged;
        int vError = 0;

        for (int i = 0; i < input.size(); i++) {
            result = 0;
            for (int j = 0; j < weight.size(); j++) {
                temp = weight.get(j) * input.get(i).properties.get(j);
                result += temp;
            }

            if (result >= threshold) {
                outcome = true;
            } else {
                outcome = false;
            }

            if (input.get(i).getType().equals(type)) {
                setosa++;
                if (!outcome) {
                    sError++;
                }
            } else {
                virginica++;
                if (outcome) {
                    vError++;
                }
            }
        }

        sUnchanged = setosa-sError;
        vUnchanged = virginica-vError;
        System.out.println("Out of " + setosa + " setosa irises " + sUnchanged + " remained unchanged resulting in " + 100*sUnchanged/setosa + " % accuracy");
        System.out.println("Out of " + virginica + " virginica irises " + vUnchanged + " remained unchanged resulting in " + 100*vUnchanged/virginica + " % accuracy");
    }

    public void singleTest(Iris iris){
        double result = 0;
        double temp;

        for (int j = 0; j < weight.size(); j++) {
            temp = weight.get(j) * iris.properties.get(j);
            result += temp;
        }

        if (result >= threshold) {
            System.out.println("Setosa");
        } else {
            System.out.println("Virginica");
        }
    }
}
