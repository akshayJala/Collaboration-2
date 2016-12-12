package com.niit.collaboration.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.model.User;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	
	

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	User user ;
	
	@Autowired
	UserDAO userDAO ;
	
	public UserDAOImpl() { }
	 

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	public UserDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean saveUser(User user) {
		try {
			sessionFactory.getCurrentSession().save(user);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false ;
		}
	}

	@Transactional
	public boolean updateUser(User user) {
		try {
			sessionFactory.getCurrentSession().update(user);
			return true ;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false ;
		}
		
	}

	@Transactional
	public boolean removeUser(String userId) {
		User userDetail = new User();
		userDetail.setUserId(userId);
		sessionFactory.getCurrentSession().delete(userDetail);
		return true;
	}

	@Transactional
	public User getUser(String userId) {
		/*Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User where userId=?");
		query.setString(0, userId);
		User user = (User) query.uniqueResult();*/
		return (User) sessionFactory.getCurrentSession().get(User.class, userId);
	}

	@Transactional
	public List<User> getUserList() {
		/*List<User> listUser = sessionFactory.getCurrentSession().createCriteria(User.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return listUser;*/
		String hql = "from User";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		return query.list();
	}

	@Transactional
	public void setOnline(String userId) {
		User user = getUser(userId);
		user.setIsOnline('Y');
		sessionFactory.getCurrentSession().update(user);
	}

	@Transactional
	public void setOffline(String userId) {
		User user = getUser(userId);
		user.setIsOnline('N');
		sessionFactory.getCurrentSession().update(user);

	}

	@Transactional
	public User authenticate(String userId, String password) {
		String hql = "from User where userId='" + userId + "' and password='" + password + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		/*List listUser = query.list();
		if (listUser != null && !listUser.isEmpty()) {
			return getUser(userId);
		} else {
			return null;
		}*/
		
		return (User)query.uniqueResult();
	}

	@Transactional
	public boolean isUser(String userId) {
		List<User> userList = userDAO.getUserList();
		int count = 0 ;
		for(int i=0;i<userList.size()-1;i++){
			
		boolean b=userList.get(i).getUserId().equals(userId);
		if(b==true)
			count++;
		}
		if(count>0){
			return true;
		}
		else 
		{
			return false;
		}
	}
	@Transactional
	public void reject(String userId) {
		User user = getUser(userId);
		user.setStatus('N');
		user.setReason("Insert correct info");
		sessionFactory.getCurrentSession().update(user);

	}
	@Transactional
	public void approve(String userId) {
		User user = getUser(userId);
		user.setStatus('Y');
		user.setReason("Insert correct info");
		sessionFactory.getCurrentSession().update(user);

	}

}
