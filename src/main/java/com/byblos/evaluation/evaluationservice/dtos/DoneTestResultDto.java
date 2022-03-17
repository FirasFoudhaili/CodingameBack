package com.byblos.evaluation.evaluationservice.dtos;

import com.byblos.evaluation.evaluationservice.models.ScoreParCategory;
import com.byblos.evaluation.evaluationservice.models.Test;

import java.util.ArrayList;
import java.util.List;

public class DoneTestResultDto {
    private Test test;
    private List<ScoreParCategory> scoreParCategoryList;

    public DoneTestResultDto() {
    }

    public DoneTestResultDto(Test test, ScoreParCategory sc) {
        this.test = test;
        if(this.scoreParCategoryList == null) {
            this.scoreParCategoryList=new ArrayList<>();
        }
        this.scoreParCategoryList.add(sc);
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public List<ScoreParCategory> getScoreParCategoryList() {
        return scoreParCategoryList;
    }

    public void setScoreParCategoryList(List<ScoreParCategory> scoreParCategoryList) {
        this.scoreParCategoryList = scoreParCategoryList;
    }
}
