package com.niit.collaboration.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.model.Chat;

@EnableTransactionManagement
@Repository("chatDAO")

public class ChatDAOImpl implements ChatDAO 
{
		@Autowired
		private SessionFactory sessionFactory;
		public ChatDAOImpl(SessionFactory sessionFactory)
		{
			this.sessionFactory=sessionFactory;
		}
		
		@Transactional
		public boolean save(Chat chat){	
			
			try{
			  sessionFactory.getCurrentSession().save(chat);
		return true;
			}catch (Exception e ){
				e.printStackTrace();
				return false;
			}
		}	
		@Transactional
		public boolean update(Chat chat){
			
			try{
				sessionFactory.getCurrentSession().update(chat);
		return true;
			} catch (Exception e){
		       e.printStackTrace();
		       return false;
			}
		}
		@Transactional
		public boolean delete(Chat chat){
			try{
		       sessionFactory.getCurrentSession().delete(chat);
		return true;
			} catch (Exception e){
		       e.printStackTrace();
		       return false;
			}
		}
		
		@Transactional
		public List<Chat> list(){
	
			String hql = "from Chat";
		Query query =sessionFactory.getCurrentSession().createQuery(hql);
		
		
		List<Chat> listChat = query.list();
		if(listChat == null  || listChat.isEmpty())
		{
			 return null;
			 
		}
		return query.list();
		}
}
