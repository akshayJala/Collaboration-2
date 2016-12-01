package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.Job;

public interface JobDAO {
	public boolean saveJob(Job job);
	public boolean removeJob(int jobId);
	public Job getJob(int jobId);
	public boolean updateJob(Job job);
	public List<Job> jobList();

}
