package com.homepage.mail.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homepage.mail.entity.Mail;
import com.homepage.mail.service.MailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {
	private final MailService mailService;
	
	@PostMapping("/authenticate")
	public void authenticateMail(Mail mailDto) {
		mailService.mailSend(mailDto);
	}
}
