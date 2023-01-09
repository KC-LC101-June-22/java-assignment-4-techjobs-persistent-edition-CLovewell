package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {
    private boolean newJobSuccess = false;
    private final ArrayList<Job> jobs = new ArrayList<>();
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("title", "My Jobs");
        if (newJobSuccess) {
            model.addAttribute("success", "Your job was successfully added");
            newJobSuccess = false;
        }
        if (!jobs.isEmpty()) {
            ArrayList<Job> modelJobs = new ArrayList<>(jobs);
            model.addAttribute("jobs", modelJobs);
            jobs.removeAll(modelJobs);
        }
        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model,
                                    @RequestParam int employerId,
                                    @RequestParam(required = false) List<Integer> skills) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            model.addAttribute("job", newJob);
            model.addAttribute("employers", employerRepository.findAll());
            model.addAttribute("skills", skillRepository.findAll());
            return "add";
        }

        Optional<Employer> optionalEmployer = employerRepository.findById(employerId);
        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        if (optionalEmployer.isPresent()) {
            Employer employer = optionalEmployer.get();
            newJob.setEmployer(employer);
        }
        newJob.setSkills(skillObjs);
        jobRepository.save(newJob);
        newJobSuccess = true;
        jobs.add(jobRepository.findById(newJob.getId()).get());
        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        optionalJob.ifPresent(job -> model.addAttribute("job", job));
        model.addAttribute("jobId", jobId);

        return "view";
    }
}
