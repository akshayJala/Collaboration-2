package com.niit.collaboration.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;

@Repository("jobDetailsDAO")
public class JobDAOImpl implements JobDAO{


	@Autowired
	SessionFactory sessionFactory;
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	public JobDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	


	public JobDAOImpl() {
		
	}

	@Transactional
	public boolean saveJob(Job job) {
		try {
			sessionFactory.getCurrentSession().save(job);
			return true ;
		} catch (HibernateException e) {
			
			e.printStackTrace();
			return false ;
		}
		
		
	}
	@Transactional
	public boolean save(JobApplication jobApplication){	
		
		try{
		  sessionFactory.getCurrentSession().save(jobApplication);
		}catch (HibernateException e ){
			e.printStackTrace();
			return false;
		}
		return true;
	}	
	@Transactional
	public JobApplication getMyAppliedJobs(String userID) {
		String hql = "from Job where id in (select id from JobApplication where userID = '" + userID + "')";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return (JobApplication) query.list();
	}
	@Transactional
	public boolean removeJob(int jobId) {
		try {
			sessionFactory.getCurrentSession().delete(getJob(jobId));
			return true ;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false ;
		}
		
	}
	@Transactional
	public Job getJob(int jobId) {
		return (Job)sessionFactory.getCurrentSession().get(Job.class, jobId);
		
	}
	@Transactional
	public boolean updateJob(Job job) {
		sessionFactory.getCurrentSession().update(job);
		return true ;
		
	}
	@Transactional
	public List<Job> jobList() {
		String hql = "from Job";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
		
	}

}
