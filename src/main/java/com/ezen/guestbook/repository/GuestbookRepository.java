package com.ezen.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.ezen.guestbook.entity.GuestBook;

public interface GuestbookRepository extends JpaRepository<GuestBook, Long>,
										QuerydslPredicateExecutor<GuestBook>{

}
