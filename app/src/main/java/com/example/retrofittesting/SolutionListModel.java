package com.example.retrofittesting;

import java.util.List;

public class SolutionListModel {
    public List<Solution> getSolutionList() {
        return solutionList;
    }

    public void setSolutionList(List<Solution> solnList) {
        solutionList = solnList;
    }

    private List<Solution> solutionList;

}
