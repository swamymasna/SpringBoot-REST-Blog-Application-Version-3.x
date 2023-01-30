package com.swamy.dto;

import java.util.Set;

import com.swamy.entity.Comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

	private Integer id;

	@NotEmpty(message = "Title must not be Empty or Null")
	@Size(min = 2, message = "Title should have atleast 2 charecters")
	private String title;
	
	@NotEmpty(message = "Content must not be Empty or Null")
	@Size(min = 2, message = "Content should have atleast 2 charecters")
	private String content;
	
	@NotEmpty(message = "Description must not be Empty or Null")
	@Size(min = 2, message = "Description should have atleast 2 charecters")
	private String description;
	
	private Set<Comment>comments;
}
