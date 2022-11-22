package org.launchcode.techjobs.persistent.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Job extends AbstractEntity {
    @ManyToOne
    @NotNull(message = "An employer is required")
    private Employer employer;
    @ManyToMany
    @NotEmpty(message = "At least one skill is required")
    private List<Skill> skills = new ArrayList<>();

    public Job() {
    }

    public Job(Employer anEmployer, List<Skill> someSkills) {
        this.employer = anEmployer;
        this.setSkills(someSkills);
    }

    // Getters and setters.

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public boolean addSkill(Skill aSkill) {
        return skills.add(aSkill);
    }
    public boolean removeSkill(Skill aSkill) {
        return skills.remove(aSkill);
    }

    public void setSkills(List<Skill> someSkills) {
        this.skills = someSkills;
    }
}
