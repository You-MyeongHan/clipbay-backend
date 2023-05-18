package com.homepage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class HomepageApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomepageApplication.class, args);
	}
}