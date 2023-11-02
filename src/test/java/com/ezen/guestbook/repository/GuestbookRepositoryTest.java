package com.ezen.guestbook.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ezen.guestbook.entity.GuestBook;
import com.ezen.guestbook.entity.QGuestBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@SpringBootTest
public class GuestbookRepositoryTest {

   @Autowired
   private GuestbookRepository guestbookRepository;
   
   @Disabled
   @Test
   public void insertData() {
      IntStream.rangeClosed(1, 300).forEach(i -> {
         GuestBook guestbook = GuestBook.builder()
               .title("title..." + i)
               .content("content..." +i)
               .writer("user" + (i % 10))
               .build();
         System.out.println(guestbookRepository.save(guestbook));
      });
   }
   @Disabled
   @Test
   public void updateTest() {
      Optional<GuestBook> result = guestbookRepository.findById(300L);
      
      if(result.isPresent()) {
         GuestBook guestbook = result.get();
         
         guestbook.changeTitle("수정된 제목.....");
         guestbook.changeContent("수정된 내용.....");
         
         guestbookRepository.save(guestbook);
      }
   }
   @Disabled
   @Test
   public void testQuerydsl() {
      // (1) Q도메일 클래스의 객체 생성
      QGuestBook qGuestbook = QGuestBook.guestBook;
      
      // where절에 들어가는 조건을 만들기 위해 BooleanBuilder 객체 생성
      BooleanBuilder builder = new BooleanBuilder();
      
      //추가할 조건식 생성
      String keyword = "1";
      BooleanExpression expression = qGuestbook.title.contains(keyword);
      
      //만들어진 조건을 and 나 or 조건을 사용하여 결합
      builder.and(expression);
      
      Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
      
      //findAll() - QuerydslPredicateExecutor 인터페이스의 메소드
      Page<GuestBook> result = guestbookRepository.findAll(builder, pageable);
      
      //결과 출력
      System.out.println(">>>>> 방명록 목록 조회 <<<<<");
      result.stream().forEach(guestbook -> {
         System.out.println(guestbook);
      });
   }
   /*
    * 여러 조건을 결합한 테스트
    */
   @Test
   public void testQuerydsl2() {
      QGuestBook qGuestbook = QGuestBook.guestBook;
      
      String keyword = "1";
      BooleanBuilder builder = new BooleanBuilder();
      BooleanExpression exTitle = qGuestbook.title.contains(keyword);      
      BooleanExpression exContent = qGuestbook.content.contains(keyword);
      
      BooleanExpression exAll = exTitle.or(exContent);
      
      builder.and(exAll);
      
      builder.and(qGuestbook.gno.gt(0L));
      Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
      Page<GuestBook> result = 
            guestbookRepository.findAll(builder, pageable);
      
      result.stream().forEach(guestbook -> {
         System.out.println(guestbook);
      });
   }
}