package com.example.retrofittesting;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {
    private String problemDescription;

    @SerializedName("solutionList")
    private List<Solution> solutionList;

    public String getProblemDescription() {
        return problemDescription;
    }

    public List<Solution> getSolutionList() {
        return solutionList;
    }
}
