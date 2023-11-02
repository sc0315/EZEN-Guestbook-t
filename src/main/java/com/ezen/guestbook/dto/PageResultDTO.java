package com.ezen.guestbook.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

// Repository에서 조회한 결과(Page<Entity)를 받아서 Controller에서 DTO 타입의 객체로 
// 활용하기 위해서 하용하는 클래스(화면에 필요한 페이지 정보 구성)
@Data
public class PageResultDTO<DTO, EN> {
	// DTO데이터 목록
	private List<DTO> dtoList;
	
	// 총 페이지수
	private int totalPage;
	
	// 현재 페이지 번호
	private int page;
	
	// 페이지 당 항목 수
	private int size;
	
	// 시작페이지 번호, 끝 페이지 번호
	private int start, end;
	
	//이전 다음 여부
	private boolean prev, next;
	
	// 페이지 번호 목록
	private List<Integer> pageList;	
	
	// Page<EN> result - Repository에서 조회한 결과를 담은 변수
	// Function<EN, DTO> - EN 타입의 객체를 DTO 타입으로 매핑(변환)
	// map(fn) - 스트림내의 요소들을 하나씩 특정값으로 변환(Entity ->DTO)
	// collect() - 매핑한 요소들을 수집해서 새로운 컬렉션
	public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
		dtoList =  result.stream().map(fn).collect(Collectors.toList());
		
		totalPage =result.getTotalPages();
		
		makePageList(result.getPageable());
		
	}
	
	private void makePageList(Pageable pageable) {
		this.page = pageable.getPageNumber() +1; // 페이지번호는 0부터 시작하므로 1을 더함
		
		this.size = pageable.getPageSize();
		
		// 임시 끝페이지 계산
		int tmpEnd = (int)(Math.ceil(page/10.0)) * 10;
		
		// 시작 페이지 계산
		start = tmpEnd - 9;
		end = totalPage > tmpEnd ? tmpEnd : totalPage; 
		
		prev = start == 1 ? false : true;
		next =totalPage >tmpEnd ? true : false;
		
		// boxed() ->int타입값 -> Integer값으로 변환
		pageList =IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}

}
