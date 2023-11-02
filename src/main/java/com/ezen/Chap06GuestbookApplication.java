package com.ezen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing	// BaseEntity에서 정의한 AuditingEntityListener를 활성화
public class Chap06GuestbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chap06GuestbookApplication.class, args);
	}

}
