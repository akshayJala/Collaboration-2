package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;

public interface JobDAO {
	public boolean saveJob(Job job);
	public boolean save(JobApplication jobApplication);
	public boolean removeJob(int jobId);
	public Job getJob(int jobId);
	public JobApplication getMyAppliedJobs(String userId);
	public boolean updateJob(Job job);
	public List<Job> jobList();
	public JobApplication getJobApplication(int jobId);

}
