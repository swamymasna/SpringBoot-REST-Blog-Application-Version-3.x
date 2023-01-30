package com.swamy.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.swamy.dto.PostDTO;
import com.swamy.dto.PostResponseDTO;
import com.swamy.entity.Post;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.repository.PostRepository;
import com.swamy.service.IPostService;
import com.swamy.utils.AppConstants;
import com.swamy.utils.EmailUtil;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class PostServiceImpl implements IPostService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmailUtil emailUtil;

	@Override
	public PostDTO savePost(PostDTO postDTO) {

		LOGGER.info("Entered into savePost() method");
		Post post = modelMapper.map(postDTO, Post.class);
		Post savedPost = postRepository.save(post);

		PostDTO postResponse = modelMapper.map(savedPost, PostDTO.class);

		/*
		// Sending Email Code
		String subject = AppConstants.SUBJECT;
		String receiver = AppConstants.SENDER;
		String body = readMailBody(savedPost);
		String email = emailUtil.sendEmail(subject, receiver, body);
		LOGGER.info(email);
		System.out.println(email);
		*/
		return postResponse;
	}

	public String readMailBody(Post post) {

		String mailBody = null;
		StringBuffer buffer = new StringBuffer();

		try {
			List<String> allLines = Files.readAllLines(Paths.get(AppConstants.POST_EMAIL_BODY_TEMPLATE));

			allLines.forEach(line -> {
				buffer.append(line);
				buffer.append("\n");
			});

			mailBody = buffer.toString();
			mailBody = mailBody.replace(AppConstants.USERNAME, AppConstants.RECEIVER);
			mailBody = mailBody.replace(AppConstants.POST_ID, post.getId().toString());
			mailBody = mailBody.replace(AppConstants.POST_TITLE, post.getTitle());
			mailBody = mailBody.replace(AppConstants.POST_CONTENT, post.getContent());
			mailBody = mailBody.replace(AppConstants.POST_DESCRIPTION, post.getDescription());
			mailBody = mailBody.replace(AppConstants.CREATED_DATE, post.getCreatedDate().toString());
			mailBody = mailBody.replace(AppConstants.UPDATED_DATE, post.getUpdatedDate().toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mailBody;
	}

	@Override
	public PostDTO getOnePostById(Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND_EXCEPTION + postId));
		return modelMapper.map(post, PostDTO.class);
	}

	@Override
	public PostResponseDTO getAllPosts(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Post> pages = postRepository.findAll(pageable);
		List<Post> postsList = pages.getContent();
		List<PostDTO> content = postsList.stream().map(post -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());

		PostResponseDTO postResponse = new PostResponseDTO();
		postResponse.setContent(content);
		postResponse.setPageNo(pageNo);
		postResponse.setPageSize(pageSize);
		postResponse.setSortBy(sortBy);
		postResponse.setTotalElements(pages.getTotalElements());
		postResponse.setTotalPageNumbers(pages.getTotalPages());
		postResponse.setLast(pages.isLast());

		return postResponse;
	}

	@Override
	public PostDTO updatePostById(Integer postId, PostDTO postDTO) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND_EXCEPTION + postId));
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setDescription(postDTO.getDescription());
		Post updatedPost = postRepository.save(post);
		PostDTO updatedPostResponse = modelMapper.map(updatedPost, postDTO.getClass());
		return updatedPostResponse;
	}

	@Override
	public String deletePostById(Integer postId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND_EXCEPTION + postId));
		postRepository.delete(post);
		return AppConstants.POST_DELETE_SUCCESS + postId;
	}

}
