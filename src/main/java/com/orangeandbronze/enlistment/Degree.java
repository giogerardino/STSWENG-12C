package com.orangeandbronze.enlistment;
import org.apache.commons.lang3.Validate;

import java.util.*;

/** [ ] A student belongs to a degree program.
*   [ ] A degree program is a collection of subjects that a student may take. */ 

public class Degree {
    private final String degreeProg;
    private final Collection<String> Degrees = new HashSet<>();

    Degree (String degreeProg){
        Validate.notNull(degreeProg);
        Validate.isTrue(Degrees.contains(degreeProg), "The student's degree program is " + degreeProg);

        this.degreeProg = degreeProg;
        
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Degree degree = (Degree) o;

        return Objects.equals(degreeProg, degree.degreeProg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(degreeProg);
    }
}
