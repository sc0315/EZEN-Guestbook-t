package com.ezen.guestbook.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ezen.guestbook.dto.GuestbookDTO;
import com.ezen.guestbook.dto.PageRequestDTO;
import com.ezen.guestbook.dto.PageResultDTO;
import com.ezen.guestbook.entity.GuestBook;

@SpringBootTest
public class GuestbookServiceTest {
	
	@Autowired
	private GuestbookService service;
	
	// 등록
	@Disabled
	@Test
	public void testRegister() {
		GuestbookDTO guestbookDTO = GuestbookDTO.builder()
				.title("Sample Title...")
				.content("Sample Content...")
				.writer("user0")
				.build();
		
		System.out.println(service.register(guestbookDTO));
	}
	
	// 방명록 목록조회 서비스 테스트
	@Disabled
	@Test
	public void testList() {
		// 요청하는 데이터의 페이지 번호, 항목수 설정
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
				.page(1).size(10).build();
		
		PageResultDTO<GuestbookDTO, GuestBook> resultDTO = service.getList(pageRequestDTO);
		
		System.out.println("PREV : "+resultDTO.isPrev());
		System.out.println("NEXT : "+ resultDTO.isNext());
		System.out.println("TOTAL PAGE : "+resultDTO.getTotalPage());
		System.out.println("=========================================");
		for(GuestbookDTO dto : resultDTO.getDtoList()) {
			System.out.println(dto);
		}
		System.out.println("=========================================");
		resultDTO.getPageList().forEach(i-> System.out.println(i));
	}
	@Test
	public void testSearch() {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
				.page(1)
				.size(10)
				.condition("tc")
				.keyword("2")
				.build();
		
		PageResultDTO<GuestbookDTO, GuestBook> resultDTO = service.getList(pageRequestDTO);
		
		System.out.println("이전 버든:" + resultDTO.isPrev());
		System.out.println("다음 버튼:" + resultDTO.isNext());
		System.out.println("총 페이지 수" + resultDTO.getTotalPage());
		System.out.println("---------------------------------------------");
		for(GuestbookDTO dto : resultDTO.getDtoList()) {
			System.out.println(dto);
		}
		
		System.out.println("===============================================");
		resultDTO.getPageList().forEach(i -> System.out.println(i + " "));
	}
	
}
