package com.ezen.guestbook.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

/*
 *  다른 엔티티 객체의 등록시간과 최중 수정시간을 담당하는 클래스
 */
@MappedSuperclass	// 지정된 클래스가 테이블로 생성되지 않도록 함.
@EntityListeners(value= {AuditingEntityListener.class})	// 다른 엔티티의 변화를 감지해서 테이블의 데이터를 조작하는 일
@Getter
public abstract class BaseEntity {
	
	@CreatedDate // 등록 일시를 저장하는 컬럼으로 지정
	@Column(name="regdate", updatable=false)	// updatable=false => 데이터 수정시에 값을 저장하지 않음
	private LocalDateTime regDate;	// 등록시간
	
	@LastModifiedDate	// 수정일시를 저장하는 컬럼으로 지정
	@Column(name="moddate")
	private LocalDateTime modDate;	// 수정시간

}
