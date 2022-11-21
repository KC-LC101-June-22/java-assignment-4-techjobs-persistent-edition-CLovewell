package org.launchcode.techjobs.persistent.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer extends AbstractEntity {

    @NotBlank(message = "A location is required")
    @Size(max = 60, message = "That location name is too long")
    private String location;

    @OneToMany//(mappedBy = "employer")
    @JoinColumn(name = "employer_id")
    private final List<Job> jobs = new ArrayList<>();

    public Employer(@NotBlank @Size(max = 60) String location) {
        this.location = location;
    }

    public Employer() {}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Job> getJobs() {
        return this.jobs;
    }

    public boolean addJob(Job aJob) {
        return jobs.add(aJob);
    }

    public boolean removeJob(Job aJob) {
        return jobs.remove(aJob);
    }
}
