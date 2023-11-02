package com.ezen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.guestbook.dto.GuestbookDTO;
import com.ezen.guestbook.dto.PageRequestDTO;
import com.ezen.guestbook.service.GuestbookService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/guestbook")
@Log4j2
public class GuestbookController {
	
	@Autowired
	private GuestbookService service;
	
	@GetMapping("/")
	public String index() {
		return "redirect:/guestbook/list";
	}
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		log.info("list....." + pageRequestDTO);
		
		model.addAttribute("result", service.getList(pageRequestDTO));		
	}
	/*
	 * 방명록 등록 화면 표시
	 */
	@GetMapping("/register")
	public void register() {
		log.info("register.....");
	}
	
	/*
	 * 방명록 내용 등록
	 */
	@PostMapping("/register")
	public String registerAction(GuestbookDTO dto, RedirectAttributes rattr) {
		log.info("register.....dto=" +dto);
		
		Long gno = service.register(dto);
		
		rattr.addFlashAttribute("msg", gno);
		
		return "redirect:/guestbook/list";
	}
	
	@GetMapping({"/read", "/modify"})
	public void read(GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
		log.info("read.....gno=" + dto.getGno());
		
		GuestbookDTO guestbook = service.read(dto.getGno());
		
		model.addAttribute("dto", guestbook);
	
	}
	@PostMapping("/remove")
	public String remove(Long gno, RedirectAttributes rattr) {
		log.info("read....gno=" + gno);
		
		service.remove(gno);
		
		rattr.addFlashAttribute("msg", gno);
		
		return "redirect:/guestbook/list";
	}
	@PostMapping("/modify")
	public String modify(GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
			RedirectAttributes rattr) {
		log.info("modify.....dto=" + dto);
		
		service.modify(dto);
		rattr.addAttribute("page", requestDTO.getPage());
		rattr.addAttribute("condition", requestDTO.getCondition());
		rattr.addAttribute("keyword", requestDTO.getKeyword());
		rattr.addAttribute("gno", dto.getGno());
		
		return "redirect:/guestbook/read";
	}
}
