package com.swamy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.CommentDTO;
import com.swamy.service.ICommentService;

@RestController
@RequestMapping("/api/v1")
public class CommentRestController {

	@Autowired
	private ICommentService commentService;

	@PostMapping("/post/{post-id}/comment/save")
	public ResponseEntity<CommentDTO> saveComment(@PathVariable(value = "post-id") Integer postId,
			@RequestBody CommentDTO commentDTO) {
		return new ResponseEntity<CommentDTO>(commentService.saveCommentByPostId(postId, commentDTO),
				HttpStatus.CREATED);
	}

	@GetMapping("/post/{post-id}/comment/{comment-id}")
	public ResponseEntity<CommentDTO> getOneCommentByPostId(@PathVariable(value = "post-id") Integer postId,
			@PathVariable(value = "comment-id") Integer commentId) {
		return new ResponseEntity<CommentDTO>(commentService.getOneCommentByPostId(postId, commentId), HttpStatus.OK);
	}

	@GetMapping("/post/{post-id}/comment/list")
	public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable(value = "post-id") Integer postId) {
		return new ResponseEntity<>(commentService.getAllCommentsByPostId(postId), HttpStatus.OK);
	}
}
