package com.niit.collaboration.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.BlogDAO;
import com.niit.collaboration.model.Blog;

@RestController

public class BlogController {

	@Autowired
	private BlogDAO blogDAO;
	
	@Autowired
	private Blog blog;
	
	@RequestMapping(value = "/blogs" , method = RequestMethod.GET)
	public ResponseEntity<List<Blog>> getblogs()
	{
		List<Blog> blogs = blogDAO.blogList();
		if(blogs == null)
		{
			blog = new Blog();
			 blog.setErrorCode("404");
       	  blog.setErrorMessage("No blogs for now");
       	  return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
		}
		 else
         {
       	  return new ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);
         }
	}
	
	@RequestMapping(value = "/blog{blogId}" , method = RequestMethod.GET)
	public Blog getBlog(@PathVariable("blogId")int blogId){
		
		Blog blog = blogDAO.getBlog(blogId);
		if(blog == null)
		{
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("No blog with"+ blogId);
		}
		
		return blog;
	}
	
	@RequestMapping(value = "/blog/" , method = RequestMethod.POST)
	public ResponseEntity<Blog> createBlog(@RequestBody Blog blog, HttpSession httpsession) {
		
   		String loggedInuserId = (String) httpsession.getAttribute("loggedInUserId");
	
		blog.setUserId(loggedInuserId);
		blog.setStatus('N');
		
		blogDAO.saveBlog(blog);
		
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	@RequestMapping(value="/blog/{blogId}", method = RequestMethod.DELETE)
	public ResponseEntity<Blog> deleteBlog(@PathVariable int blogId) {
		
		
		if(blogDAO.getBlog(blogId)!=null) {
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
		blogDAO.deleteBlog(blogId);
		return new ResponseEntity<Blog>(HttpStatus.OK);
	}

	
}
