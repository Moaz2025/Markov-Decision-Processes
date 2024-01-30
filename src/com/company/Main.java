package com.company;

import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        ValueIteration valueIteration = new ValueIteration();
        Locale.setDefault(new Locale("en"));
        for (double rewardValue : new double[]{100, 3, 0, -3}) {
            valueIteration.initializeRewards(rewardValue);
            double[][] optimalValues = valueIteration.valueIteration();
            System.out.printf("Optimal Values for r = %.2f:\n", rewardValue);
            valueIteration.printGrid(optimalValues);
            System.out.println("Optimal Policy:");
            valueIteration.printPolicy();
            System.out.println();
        }
    }
}

