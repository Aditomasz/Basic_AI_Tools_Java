package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CUI {

    public static ArrayList<Iris> training = new ArrayList<>();
    public static Perceptron perceptron;

    public static void Open() {
        Scanner read = new Scanner(System.in);
        System.out.println("Type L to load training data, type T to test data, type N to type in and test new data, type P for perceptron, type K for Kmeans");
        String temp = read.nextLine();
        int i;
        double d;

        switch (temp) {
            case "L":
                CUI.Load();
                break;
            case "T":
                CUI.Test();
                break;
            case "N":
                CUI.New();
                break;
            case "P":
                System.out.println("Please input amount of arguments");
                i = read.nextInt();
                System.out.println("Please input testing constant");
                d = read.nextDouble();
                perceptron = new Perceptron(i, d);
                CUI.PerceptronMenu();
                break;
            case "K":
                System.out.println("Please input k");
                i = read.nextInt();
                Kmeans kmeans = new Kmeans(i, training);
                Kmeans.setup();
                kmeans.newCentroids();
                boolean flag = true;
                while(flag){
                    flag = Kmeans.loop();
                    Kmeans.print();
                    kmeans.newCentroids();
                }
                Kmeans.print();
                break;
            default:
                System.out.println("Wrong Input");
                CUI.Open();
        }
    }

    public static void Load() {
        String line;
        String[] data;
        ArrayList<String> properties;
        ArrayList<Iris> intro = new ArrayList<>();
        Iris iris;

        try {
            File input = new File("train-set.csv");
            Scanner read = new Scanner(input);
            while (read.hasNextLine()) {
                line = read.nextLine();
                data = line.split(",");
                properties = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    properties.add(data[i]);
                }
                iris = new Iris(properties);
                intro.add(iris);
            }
            read.close();

            CUI.training = intro;
            System.out.println("Data loaded");
        } catch (Exception e) {
            System.out.println("ERROR");
        }
        CUI.Open();
    }

    public static void Test() {
        String line;
        String[] data;
        ArrayList<String> properties;
        ArrayList<Iris> intro = new ArrayList<>();
        Iris iris;

        try {
            File input = new File("test-set.csv");
            Scanner read = new Scanner(input);
            while (read.hasNextLine()) {
                line = read.nextLine();
                data = line.split(",");
                properties = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    properties.add(data[i]);
                }
                iris = new Iris(properties);
                intro.add(iris);
            }
            read.close();

            intro = Iris.assign(CUI.training, intro);

            for (int i = 0; i < intro.size(); i++) {
                System.out.println(intro.get(i));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CUI.Open();
    }

    public static void New() {
        Scanner read = new Scanner(System.in);
        String[] data;
        ArrayList<String> properties;
        ArrayList<Iris> intro = new ArrayList<>();
        System.out.println("Please input parameters as double separated by comma (same amount as training data, default 4) and type as String(optional)");
        String line = read.nextLine();
        data = line.split(",");
        properties = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            properties.add(data[i]);
        }
        Iris iris = new Iris(properties);
        intro.add(iris);
        intro = Iris.assign(CUI.training, intro);

        for (int i = 0; i < intro.size(); i++) {
            System.out.println(intro.get(i));
        }
        CUI.Open();
    }

    public static void PerceptronMenu() {
        Scanner read = new Scanner(System.in);
        System.out.println("Type T to train perceptron, type TD to test data, type N to type in and test new data");
        String temp = read.nextLine();

        switch (temp) {
            case "T":
                CUI.PerceptronTrain();
                break;
            case "TD":
                CUI.PerceptronTest();
                break;
            case "N":
                CUI.SinglePerceptron();
                break;
            default:
                System.out.println("Wrong Input");
                CUI.PerceptronMenu();
        }

    }

    public static void PerceptronTrain(){
        String line;
        String[] data;
        ArrayList<String> properties;
        Iris iris;
        ArrayList<Iris> intro = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type how many training cycles you wish to perform");
        int temp = scanner.nextInt();

        try {
            File input = new File("train-set.csv");
            Scanner read = new Scanner(input);
            while (read.hasNextLine()) {
                line = read.nextLine();
                data = line.split(",");
                properties = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    properties.add(data[i]);
                }
                iris = new Iris(properties);
                intro.add(iris);
            }
            read.close();
            for (int i = 0; i < temp; i++){
                for (int j = 0; j < intro.size(); j++){
                    perceptron.train(intro.get(j));
                }
            }
            System.out.println("Perceptron trained");
        } catch (Exception e) {
            System.out.println("ERROR");
        }
        CUI.PerceptronMenu();
    }

    public static void PerceptronTest() {
        String line;
        String[] data;
        ArrayList<String> properties;
        ArrayList<Iris> intro = new ArrayList<>();
        Iris iris;

        try {
            File input = new File("test-set.csv");
            Scanner read = new Scanner(input);
            while (read.hasNextLine()) {
                line = read.nextLine();
                data = line.split(",");
                properties = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    properties.add(data[i]);
                }
                iris = new Iris(properties);
                intro.add(iris);
            }
            read.close();

            perceptron.test(intro);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CUI.PerceptronMenu();
    }

    public static void SinglePerceptron(){
        Scanner read = new Scanner(System.in);
        String[] data;
        ArrayList<String> properties;
        System.out.println("Please input parameters as double separated by comma (same amount as training data, default 4) and type as String(optional)");
        String line = read.nextLine();
        data = line.split(",");
        properties = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            properties.add(data[i]);
        }
        Iris iris = new Iris(properties);
        perceptron.singleTest(iris);
        CUI.PerceptronMenu();
    }
}