package com.joonki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joonki.domain.Pager;
import com.joonki.domain.Post;
import com.joonki.service.PostService;

@RestController
public class PostController {
	@Autowired
	private PostService postService;
	
	@RequestMapping(value="/posts", method = RequestMethod.GET)
	public Pager<Post> getPosts(@RequestParam int limit, @RequestParam int offset) {
		return postService.getPosts(limit, offset);
	}
	
	@RequestMapping(value="/posts", method = RequestMethod.POST)
	public void addPost(@RequestBody Post post) {
		postService.addPost(post);
	}
}
