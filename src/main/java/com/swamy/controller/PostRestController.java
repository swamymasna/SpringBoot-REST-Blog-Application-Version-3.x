package com.swamy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.PostDTO;
import com.swamy.dto.PostResponseDTO;
import com.swamy.service.IPostService;
import com.swamy.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class PostRestController {

	@Autowired
	private IPostService postService;

	@PostMapping("/post/save")
	public ResponseEntity<PostDTO> savePost(@Valid @RequestBody PostDTO postDTO) {
		return new ResponseEntity<PostDTO>(postService.savePost(postDTO), HttpStatus.CREATED);
	}

	@GetMapping("/post/list")
	public ResponseEntity<PostResponseDTO> getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEF_PAGE_NO, required = true) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEF_PAGE_SIZE, required = true) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEF_SORT_BY, required = true) String sortBy) {
		return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy), HttpStatus.OK);
	}

	@GetMapping("/post/one/{post-id}")
	public ResponseEntity<PostDTO> getOnePostById(@PathVariable(value = "post-id") Integer postId) {
		return new ResponseEntity<>(postService.getOnePostById(postId), HttpStatus.OK);
	}

	@PutMapping("/post/update/{post-id}")
	public ResponseEntity<PostDTO> updatePostById(@PathVariable(value = "post-id") Integer postId,
			@Valid @RequestBody PostDTO postDTO) {
		return new ResponseEntity<>(postService.updatePostById(postId, postDTO), HttpStatus.OK);
	}

	@DeleteMapping("/post/delete/{post-id}")
	public ResponseEntity<String> deletePostById(@PathVariable(value = "post-id") Integer postId) {
		return new ResponseEntity<>(postService.deletePostById(postId), HttpStatus.OK);
	}
}
