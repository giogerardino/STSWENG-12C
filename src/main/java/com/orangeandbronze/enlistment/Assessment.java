package com.orangeandbronze.enlistment;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

class Assessment {
    private final BigDecimal UNIT_COST= BigDecimal.valueOf(2000);
    private final BigDecimal LAB_FEE = BigDecimal.valueOf(1000);
    private final BigDecimal MISC_FEES = BigDecimal.valueOf(3000);
    private final BigDecimal VAT = new BigDecimal("0.12");
    // TODO: Research how to tell bigdecimal no. of decimal places; double check if valueOf() is best way to do it

    private final Collection<Section> sections = new HashSet<>();
    private BigDecimal totalAssessment = new BigDecimal(0);

    Assessment(Collection<Section> sections){
        if(sections == null){
            throw new NullPointerException();
        }
        this.sections.addAll(sections);
        this.sections.removeIf(Objects::isNull);

        for (Section s:this.sections){
            this.totalAssessment = this.totalAssessment.add(UNIT_COST.multiply(BigDecimal.valueOf(s.getSubject().getUnits())));
            if(s.getSubject().isLab())
                this.totalAssessment = this.totalAssessment.add(LAB_FEE);
        }

        this.totalAssessment = this.totalAssessment.add(MISC_FEES);
        this.totalAssessment = this.totalAssessment.add(this.totalAssessment.multiply(VAT));
    }

    BigDecimal getAssessment(){return this.totalAssessment;}
}
