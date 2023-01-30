package com.swamy.service;

import java.util.List;

import com.swamy.dto.CommentDTO;

public interface ICommentService {

	public CommentDTO saveCommentByPostId(Integer postId, CommentDTO commentDTO);

	public CommentDTO getOneCommentByPostId(Integer postId, Integer commentId);

	public List<CommentDTO> getAllCommentsByPostId(Integer postId);
}
