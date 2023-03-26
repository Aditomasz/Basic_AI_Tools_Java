package com.company;

public class DoubleIntString {
    private double d;
    private int i = 0;
    private String s;

    public DoubleIntString(double d, String s){
        this.d = d;
        this.s = s;
    }

    public DoubleIntString(int i, String s){
        this.i = i;
        this.s = s;
    }

    public double getD() {
        return d;
    }

    public int getI() {
        return i;
    }

    public void increaseI() {
        this.i++;
    }

    public String getS() {
        return s;
    }



    @Override
    public String toString() {
        return "DoubleIntString{" +
                "d=" + d +
                ", i=" + i +
                ", s='" + s + '\'' +
                '}';
    }
}
