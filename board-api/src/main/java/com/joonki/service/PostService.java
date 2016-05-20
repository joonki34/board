package com.joonki.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.joonki.domain.Pager;
import com.joonki.domain.Post;
import com.joonki.repository.PostRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	public Pager<Post> getPosts(int limit, int offset) {
		List<Post> postList = postRepository.findAll(limit, offset);
		long totalCount = postRepository.getTotalCount();
		return Pager.newPager(postList, totalCount, limit);
	}
	
	public void addPost(Post post) {
		if(Strings.isNullOrEmpty(post.getContent())) { 
			throw new IllegalArgumentException("Emtpy content");
		}
		
		postRepository.save(post);
	}
	
	public void updatePost(Post post) {
		if(Strings.isNullOrEmpty(post.getContent())) { 
			throw new IllegalArgumentException("Emtpy content");
		}
		
		
	}
}
