package com.swamy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swamy.dto.CommentDTO;
import com.swamy.entity.Comment;
import com.swamy.entity.Post;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.repository.CommentRepository;
import com.swamy.repository.PostRepository;
import com.swamy.service.ICommentService;
import com.swamy.utils.AppConstants;

@Service
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDTO saveCommentByPostId(Integer postId, CommentDTO commentDTO) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND_EXCEPTION + postId));

		Comment comment = modelMapper.map(commentDTO, Comment.class);
		comment.setPost(post);

		Comment savedComment = commentRepository.save(comment);
		CommentDTO commentResponse = modelMapper.map(savedComment, commentDTO.getClass());
		return commentResponse;
	}

	@Override
	public CommentDTO getOneCommentByPostId(Integer postId, Integer commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND_EXCEPTION + postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.COMMENT_NOT_FOUND_EXCEPTION + commentId));

		return modelMapper.map(comment, CommentDTO.class);
	}

	@Override
	public List<CommentDTO> getAllCommentsByPostId(Integer postId) {
		List<Comment> commentsList = commentRepository.findByPostId(postId);
		return commentsList.stream().map(comment -> modelMapper.map(comment, CommentDTO.class))
				.collect(Collectors.toList());
	}

}
