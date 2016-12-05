package com.niit.collaboration.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.JobDAO;
import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;

@RestController
public class JobController {

	private static final Logger logger = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	JobDAO jobDAO;
	
	@Autowired
	Job job;
	
	@Autowired
	JobApplication jobApplication;
	
	
	@RequestMapping(value = "/getAllJobs/" , method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getjobs()
	{
		List<Job> jobs = jobDAO.jobList();
		if(jobs == null)
		{
			job = new Job();
			 job.setErrorCode("404");
       	  job.setErrorMessage("No blogs are available");
       	  return new ResponseEntity<List<Job>>(HttpStatus.NO_CONTENT);
		}
		 else
         {
       	  return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
         }
	}
	
	@RequestMapping(value = "/applyForJob/{jobId}", method = RequestMethod.POST)
	public ResponseEntity<Job> applyforJob(@PathVariable("jobId") int jobId, HttpSession httpSession)
	{
		logger.debug("Starting of the method getMyAppliedJobs");
		String loggedInUserId = (String) httpSession.getAttribute("loggedInUserID");
		
		/*jobApplication = jobDAO.getJobApplication(jobID);*/
		jobApplication.setUserId(loggedInUserId);
		jobApplication.setStatus('N');
		if (jobDAO.save(jobApplication))
		{
			job.setErrorCode("404");
			job.setErrorMessage("Not able to apply for the job");
			
		}
		 return new ResponseEntity<Job> (job , HttpStatus.OK);
	}
	
	

	@RequestMapping(value="/getMyAppliedJobs/" , method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getMyAppliedJobs(HttpSession httpSession) {
		logger.debug("Starting of the method getMyAppliedJobs");
		String loggedInUserID =  (String) httpSession.getAttribute("loggedInUserID");
		
		//List<Job> job = jobDAO.getMyAppliedJobs(loggedInUserID);
		List<Job> job = (List<Job>) jobDAO.getMyAppliedJobs(loggedInUserID);
		return new ResponseEntity<List<Job>>(job , HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getJobDetails/{jobId}", method = RequestMethod.GET)
	public ResponseEntity<Job> getJobDetails(@PathVariable("jobId") int jobId) {
		logger.debug("Starting of the method getJobDetails");
		Job job= jobDAO.getJob(jobId);
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
}
