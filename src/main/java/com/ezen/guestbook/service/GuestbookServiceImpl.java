package com.ezen.guestbook.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezen.guestbook.dto.GuestbookDTO;
import com.ezen.guestbook.dto.PageRequestDTO;
import com.ezen.guestbook.dto.PageResultDTO;
import com.ezen.guestbook.entity.GuestBook;
import com.ezen.guestbook.entity.QGuestBook;
import com.ezen.guestbook.repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class GuestbookServiceImpl implements GuestbookService {

	@Autowired
	private GuestbookRepository guestbookRepository;
	
	@Override
	public Long register(GuestbookDTO dto) {
		
		log.info("register.....");
		log.info(dto);
		
		GuestBook entity = dtoToEntity(dto);
		
		log.info(entity);
		
		guestbookRepository.save(entity);
		
		return entity.getGno();
	}

	@Override
	public PageResultDTO<GuestbookDTO, GuestBook> getList(PageRequestDTO requestDTO) {
		
		// 정렬
		Pageable pageable = requestDTO.getPageable(Sort.by("gno"));
		
		BooleanBuilder builder = getSearch(requestDTO);
		//전체 불러오는거		
		Page<GuestBook> result = guestbookRepository.findAll(builder, pageable);
		
		// GuestBook을 GuestbookDTO타입으로 변환해서 리턴
		// entity : 입력값 , entityToDto :로 변경
		Function<GuestBook, GuestbookDTO> fn = (entity -> entityToDto(entity));
		
		return new PageResultDTO<>(result, fn);
		
	}

	@Override
	public GuestbookDTO read(Long gno) {

		Optional<GuestBook> result = guestbookRepository.findById(gno);
		
		return result.isPresent() ? entityToDto(result.get()) : null;
	}
	/*
	 * 방명록 수정 처리
	 * 입력 매개변수: GuestbookDTO dto
	 * 			-화면에 입력한 데이터
	 */
	@Override
	public void modify(GuestbookDTO dto) {
		log.info("modify.....");
		log.info("dto:" + dto);
		
		Optional<GuestBook> result = guestbookRepository.findById(dto.getGno());
		
		if(result.isPresent()) {
			GuestBook entity = result.get();
			
			entity.changeTitle(dto.getTitle());
			entity.changeContent(dto.getContent());
			
			guestbookRepository.save(entity);
		}
		
	}
	
	/*
	 * 방명록 삭제 처리
	 */
	@Override
	public void remove(Long gno) {
		log.info("remove.....");
		log.info("gno:" + gno);
		
		guestbookRepository.deleteById(gno);
		
	}
	
	private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
		String condition = requestDTO.getCondition();
		String keyword = requestDTO.getKeyword();
		
		BooleanBuilder builder = new BooleanBuilder();
		
		QGuestBook qGuestbook = QGuestBook.guestBook;
		
		BooleanExpression expression = qGuestbook.gno.gt(0L);
		
		builder.and(expression);
		
		if (condition == null || condition.trim().length() == 0) {
			return builder;
		}
		
		BooleanBuilder builder2 = new BooleanBuilder();
		
		if(condition.contains("t")) { // 제목에 의한 검색
			builder2.or(qGuestbook.title.contains(keyword));
		}
		if (condition.contains("c")) { //내용에 의한 검색
			builder2.or(qGuestbook.content.contains(keyword));
		}
		if (condition.contains("w")) { //작성자에 의한 검색
			builder2.or(qGuestbook.writer.contains(keyword));
		}
		
		builder.and(builder2);
		
		return builder;
	}
}
