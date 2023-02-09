package com.orangeandbronze.enlistment;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class Assessment {
    private final double UNIT_COST=2000;
    private final double LAB_FEE=1000;
    private final double MISC_FEES=3000;
    private final double VAT =0.12;

    private final Collection<Section> sections = new HashSet<>();
    private double totalAssessment;

    Assessment(Collection<Section> sections){
        if(sections == null){
            throw new NullPointerException();
        }
        this.sections.addAll(sections);
        this.sections.removeIf(Objects::isNull);

        for (Section s:this.sections){
            this.totalAssessment += s.getSubject().getUnits() * UNIT_COST;
            if(s.getSubject().isLab())
                this.totalAssessment += LAB_FEE;
            this.totalAssessment += MISC_FEES;
            this.totalAssessment += this.totalAssessment * VAT;
        }
    }

    Double getAssessment(){return this.totalAssessment;}
}
