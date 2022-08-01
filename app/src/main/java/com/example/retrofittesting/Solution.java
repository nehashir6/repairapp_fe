package com.example.retrofittesting;

import com.google.gson.annotations.SerializedName;

public class Solution {
    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @SerializedName("solution")
    String solution;

    public String toString(){
        return solution;
    }

}
