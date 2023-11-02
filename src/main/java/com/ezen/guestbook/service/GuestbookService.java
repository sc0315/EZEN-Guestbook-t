package com.ezen.guestbook.service;

import com.ezen.guestbook.dto.GuestbookDTO;
import com.ezen.guestbook.dto.PageRequestDTO;
import com.ezen.guestbook.dto.PageResultDTO;
import com.ezen.guestbook.entity.GuestBook;

public interface GuestbookService {
	
	Long register(GuestbookDTO dto) ;
	PageResultDTO<GuestbookDTO, GuestBook> getList(PageRequestDTO requestDTO);
	GuestbookDTO read(Long gno);
	void modify(GuestbookDTO dto);
	void remove(Long gno);
	
	// 화면에서 수신한 DTO 객체를 Entity 객체로 변환하는 메소드
	default GuestBook dtoToEntity(GuestbookDTO dto) {
		GuestBook entity = GuestBook.builder()
				.gno(dto.getGno())
				.title(dto.getTitle())
				.content(dto.getContent())
				.writer(dto.getWriter())
				.build();
		
		return entity;		
	}
	
	default GuestbookDTO entityToDto(GuestBook entity) {
		GuestbookDTO dto = GuestbookDTO.builder()
				.gno(entity.getGno())
				.title(entity.getTitle())
				.content(entity.getContent())
				.writer(entity.getWriter())
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.build();
		
		return dto;
	}

}
