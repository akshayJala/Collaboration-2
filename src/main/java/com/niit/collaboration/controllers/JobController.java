package com.niit.collaboration.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		String loggedInUserId = (String) httpSession.getAttribute("loggedInUserId");
		
		/*jobApplication = jobDAO.getJobApplication(jobId);*/
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
		String loggedInUserId =  (String) httpSession.getAttribute("loggedInUserId");
		
		//List<Job> job = jobDAO.getMyAppliedJobs(loggedInUserId);
		List<Job> job = (List<Job>) jobDAO.getMyAppliedJobs(loggedInUserId);
		return new ResponseEntity<List<Job>>(job , HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getJobDetails/{jobId}", method = RequestMethod.GET)
	public ResponseEntity<Job> getJobDetails(@PathVariable("jobId") int jobId) {
		logger.debug("Starting of the method getJobDetails");
		Job job= jobDAO.getJob(jobId);
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	@RequestMapping(value="/getAllJobDetails/{jobId}", method  = RequestMethod.PUT)
	public ResponseEntity<Job> getAllJobDetails(@RequestParam("jobId")int jobId, HttpSession httpSession){
		logger.debug("Starting of the method getAllJobDetails");
		String loggedInUserId =  (String) httpSession.getAttribute("loggedInUserId");
		
		jobApplication = jobDAO.getJobApplication(jobId);
		jobApplication.setUserId(loggedInUserId);
		jobApplication.setStatus('N');
		if(jobDAO.save(jobApplication)) {
			job.setErrorCode("404");
			job.setErrorMessage("cant apply");
			logger.debug("can't apply");
		}
		return new ResponseEntity<Job>(job , HttpStatus.OK);
	}
	@RequestMapping(value="/selectUser/{userId}/{jobId}", method = RequestMethod.PUT)
	public ResponseEntity<Job> selectUser(@RequestParam("userId")int userId,@RequestParam("jobId")int jobId){
		logger.debug("Starting of the method selectUser");
		jobApplication.setStatus('S');
		if(jobDAO.save(jobApplication)) {
			job.setErrorCode("404");
			job.setErrorMessage("Not able to change the application status ");
			logger.debug("Not able to change the application status ");
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	
	@RequestMapping(value="/canCallForInterview/{userId}/{jobId}",method = RequestMethod.PUT)
	public ResponseEntity<Job> callForInterview(@PathVariable("userId")String userId, @PathVariable("jobId")Long jobId){
		logger.debug("Starting of the method canCallForInterview");
		jobApplication.setStatus('C');
		if(jobDAO.save(jobApplication)){
			job.setErrorCode("404");
			job.setErrorMessage("Not able to change the job status ");
			logger.debug("Not able to change the status ");
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectJobApplcation/{userId}/{jobId}",method= RequestMethod.PUT)
	public ResponseEntity<Job> rejectJobApplication(@PathVariable("userId")int userId , @PathVariable("jobId")int jobId){
		logger.debug("Starting of the method rejectJobApplication");
		
		
		jobApplication.setStatus('R');
		if(jobDAO.save(jobApplication) ==false) {
			job.setErrorCode("404");
			job.setErrorMessage("Not able to reject the application");
			logger.debug("Not able to reject the application");
		}
		else
		{
			job.setErrorCode("200");
			job.setErrorMessage("Successfully updated the status as Rejected");
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	
	@RequestMapping(value="/postJob/", method = RequestMethod.POST)
	public ResponseEntity<Job> postAJob(@RequestBody Job job, HttpSession httpsession) {
		logger.debug("Starting of the method postJob");
		
		String loggedInUserId =  (String) httpsession.getAttribute("loggedInUserId");
		
		job.setUserId(loggedInUserId);
		job.setStatus('N');
		
		jobDAO.saveJob(job);
		
		return new ResponseEntity<Job>(job , HttpStatus.OK);
	}	

}

