package com.niit.collaboration.dao;

import com.niit.collaboration.model.BlogComment;

public interface BlogCommentDAO {
	public boolean saveComment(BlogComment comment);
	public boolean updateComment(BlogComment comment);
	public void deleteComment(int commentId);
	public void getComment(String userId);
	public void getComments(int blogId);
}
