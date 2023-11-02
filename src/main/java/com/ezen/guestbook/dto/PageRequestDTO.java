package com.ezen.guestbook.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

// 페이지 번호와 페이지당 항목수를 가지고 있는 데이터를 요청하는 클래스

@Data
@Builder
@AllArgsConstructor
public class PageRequestDTO {
	
	private int page;	// 현재 페이지
	private int size;	// 페이지 당 항목수
	private String condition; //검색 조건
	private String keyword; //검색어
	
	public PageRequestDTO() {
		this.page = 1;
		this.size = 10;
	}
	
	public Pageable getPageable(Sort sort) {
		
		return PageRequest.of(page - 1, size, sort);
		
	}
	
	

}
