package com.niit.collaboration.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.model.Forum;

@SuppressWarnings("deprecation")
@EnableTransactionManagement
@Repository("forumDAO")

public class ForumDAOImpl implements ForumDAO {



		@Autowired
		private SessionFactory sessionFactory;
		public ForumDAOImpl(SessionFactory sessionFactory)
		{
			this.sessionFactory=sessionFactory;
		}
		
		@Transactional
		public boolean saveOrUpdate(Forum forum){	
			
			try{
			  sessionFactory.getCurrentSession().save(forum);
		return true;
			}catch (Exception e ){
				e.printStackTrace();
				return false;
			}
		}	
		@Transactional
		public boolean update(Forum forum){
			
			try{
				sessionFactory.getCurrentSession().update(forum);
		return true;
			} catch (Exception e){
		       e.printStackTrace();
		       return false;
			}
		}
		@Transactional
		public boolean delete(Forum forum){
			try{
		       sessionFactory.getCurrentSession().delete(forum);
		return true;
			} catch (Exception e){
		       e.printStackTrace();
		       return false;
			}
		}

				@Transactional
				public List<Forum> list(){
					
					String hql = "from Forum";
				@SuppressWarnings("rawtypes")
				Query query =sessionFactory.getCurrentSession().createQuery(hql);
				
				List<Forum> listForum = query.list();
				if(listForum == null  || listForum.isEmpty())
				{
					 return null;
					 
				}
				return query.list();
				}
	
}
