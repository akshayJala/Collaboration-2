package com.niit.collaboration.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="c_forum")
@Component

public class Forum {


	
		@Id
		private int forumId;
		private String forumName;
		private String topic;
		private Date dateOfCreation;
		private int userId;
		public int getForumId() {
			return forumId;
		}
		public void setForumId(int forumId) {
			this.forumId = forumId;
		}
		public String getForumName() {
			return forumName;
		}
		public void setForumName(String forumName) {
			this.forumName = forumName;
		}
		public String getTopic() {
			return topic;
		}
		public void setTopic(String topic) {
			this.topic = topic;
		}
		public Date getDateOfCreation() {
			return dateOfCreation;
		}
		public void setDateOfCreation(Date dateOfCreation) {
			this.dateOfCreation = dateOfCreation;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
}
