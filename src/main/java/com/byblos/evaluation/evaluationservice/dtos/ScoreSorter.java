package com.byblos.evaluation.evaluationservice.dtos;

import java.util.Comparator;

public class ScoreSorter implements Comparator<TopCandidateDto> {

    @Override
    public int compare(TopCandidateDto o1, TopCandidateDto o2) {
        return o2.getScore().compareTo(o1.getScore());

    }
    
}
