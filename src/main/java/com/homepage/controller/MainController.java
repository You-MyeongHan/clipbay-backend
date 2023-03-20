package com.homepage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;

import org.springframework.stereotype.Controller;

@Controller
public class MainController {
	@RequestMapping("/main/main.do")
	public ModelAndView blank() throws Exception{
		ModelAndView mv = new ModelAndView("main/main");
		
		mv.addObject("timestamp", new Timestamp(System.currentTimeMillis()));
		mv.addObject("pageTitle", "homepage");
		return mv;
	}
}