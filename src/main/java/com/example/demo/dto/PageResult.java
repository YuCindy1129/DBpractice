package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.GroupFunc;

import lombok.Data;

@Data
public class PageResult {

	private Long total;
	private Integer totalpage;
	private List<GroupFunc> result;
}


