package com.company;

public class BubbleSort {
    public static void ReverseSort(DoubleIntString[] tab) {
        int n = tab.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if(tab[j].getD() < tab[j+1].getD()){
                    DoubleIntString temp = tab[j];
                    tab[j] = tab[j+1];
                    tab[j+1] = temp;
                }
            }
        }
    }

    public static void ReverseSortI(DoubleIntString[] tab) {
        int n = tab.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if(tab[j].getI() < tab[j+1].getI()){
                    DoubleIntString temp = tab[j];
                    tab[j] = tab[j+1];
                    tab[j+1] = temp;
                }
            }
        }
    }
}
