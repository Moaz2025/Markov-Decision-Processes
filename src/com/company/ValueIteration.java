package com.company;

import java.util.Arrays;

class ValueIteration {

    private final int ROWS = 3;
    private final int COLS = 3;
    private final int[][] ACTIONS = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    private final double TRANSITION_PROB = 0.8;
    private final double OTHER_PROB = 0.1;
    private final double DISCOUNT_FACTOR = 0.99;
    private final double[][] REWARDS = new double[ROWS][COLS];
    private int[][] policy = new int[3][3];

    public void initializeRewards(double rewardValue) {
        for (double[] row : REWARDS) {
            Arrays.fill(row, -1);
            REWARDS[0][0] = rewardValue;
            REWARDS[0][2] = 10;
        }
    }

    public double[][] valueIteration() {
        double[][] V = REWARDS;
        int numIterations = 100;
        for (int iteration = 0; iteration < numIterations; iteration++) {
            double[][] newV = new double[ROWS][COLS];
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    if(i == 0 && j == 0){
                        newV[i][j] = REWARDS[i][j];
                        continue;
                    }
                    if(i == 0 && j == 2){
                        newV[i][j] = REWARDS[i][j];
                        continue;
                    }
                    double maxQValue = Double.NEGATIVE_INFINITY;
                    for (int k = 0; k < ACTIONS.length; k++) {
                        int[] action = ACTIONS[k];
                        int nextRow = i + action[0];
                        int nextCol = j + action[1];

                        if (! isValidState(nextRow, nextCol)) {
                            nextRow = i;
                            nextCol = j;
                        }
                        double qValue = TRANSITION_PROB * (DISCOUNT_FACTOR * V[nextRow][nextCol]);
                        for (int l = 0; l < ACTIONS.length; l++) {
                            if(k == 0 && l == 1) continue;
                            if(k == 1 && l == 0) continue;
                            if(k == 2 && l == 3) continue;
                            if(k == 3 && l == 2) continue;
                            if (l != k) {
                                int[] otherAction = ACTIONS[l];
                                int otherRow = i + otherAction[0];
                                int otherCol = j + otherAction[1];

                                if (! isValidState(otherRow, otherCol)) {
                                    otherRow = i;
                                    otherCol = j;
                                }
                                qValue += OTHER_PROB * DISCOUNT_FACTOR * V[otherRow][otherCol];
                            }
                        }
                        if(qValue > maxQValue){
                            maxQValue = qValue;
                            policy[i][j] = k;
                        }
                    }
                    newV[i][j] = maxQValue;
                }
            }
            V = newV;
        }
        return V;
    }

    public boolean isValidState(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public void printGrid(double[][] grid) {
        for (double[] row : grid) {
            for (double value : row) {
                System.out.printf("%.2f\t", value);
            }
            System.out.println();
        }
    }

    public void printPolicy() {
        String[][] result = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 0 && j == 0){
                    result[i][j] = "Terminal";
                    continue;
                }
                if (i == 0 && j == 2){
                    result[i][j] = "Terminal";
                    continue;
                }
                if(policy[i][j] == 0)
                    result[i][j] = "Up";
                if(policy[i][j] == 1)
                    result[i][j] = "Down";
                if(policy[i][j] == 2)
                    result[i][j] = "Right";
                if(policy[i][j] == 3)
                    result[i][j] = "Left";
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(result[i][j] + getIndentation(result[i][j]));
            }
            System.out.println();
        }
    }
    public String getIndentation(String value){
        String result = "";
        for (int i = value.length(); i < 10; i++) {
            result += " ";
        }
        return result;
    }
}

