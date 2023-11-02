package com.ezen.guestbook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestBook extends BaseEntity {
	@Id
	@GeneratedValue
	private Long gno;
	
	@Column(length=100, nullable=false)
	private String title;
	
	@Column(length=1500, nullable=false)
	private String content;
	
	@Column(length=50, nullable=false)
	private String writer;
	
	// 최소한의 수정만 가능하도록 change...() 메소드 추가
	public void changeTitle(String title) {
		this.title= title;
	}
	
	public void changeContent(String content) {
		this.content = content;
	}

}
