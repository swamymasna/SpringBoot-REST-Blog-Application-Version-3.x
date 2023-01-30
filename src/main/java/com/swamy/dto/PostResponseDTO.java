package com.swamy.dto;

import java.util.List;

import lombok.Data;

@Data
public class PostResponseDTO {

	private List<PostDTO>content;
	private Integer pageNo;
	private Integer pageSize;
	private String sortBy;
	private Long totalElements;
	private Integer totalPageNumbers;
	private boolean isLast;
}
