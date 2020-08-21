package com.chen.app;
import android.util.Log;

import java.util.Arrays;

class Test {

    public static void main(String[] args) {

        int[][] a = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 14, 15}};

        boolean result = Find(14, a);

        boolean b = result;
    }

    public static boolean Find(int target, int[][] array) {
        if(array == null){
            return false;
        }
        int rows = array.length;
        int cols = array[0].length;
        if(rows<=0 || cols<= 0){
            return false;
        }
        int row = 0;
        int col = cols -1;
        while(row<rows && col>=0){
            if(array[row][col]== target){
                return true;
            }else if(array[row][col]>target){
                col--;
            }else{
                row++;
            }
        }
        return false;

    }
}
