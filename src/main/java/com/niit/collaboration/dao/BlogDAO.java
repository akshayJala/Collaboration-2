package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.Blog;

public interface BlogDAO {
	public boolean saveBlog(Blog blog);
	public boolean deleteBlog(int blogId);
	public void updateBlog(Blog blog);
	public Blog getBlog(int blogId);
	public List<Blog> blogList();
}
