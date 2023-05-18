package com.homepage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class HomepageApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomepageApplication.class, args);
	}
	
//	@Bean
//	public CommandLineRunner commandLineRunner(
//			AuthenticationService authenticationService
//	){
//		return args->{
//			var admin= RegisterRequest.builder()
//					.uid("mhy5413")
//					.pwd("qwer1234")
//					.nick("admin")
//					.email("mhy5413@gmail.com")
//					.role(Role.ADMIN)
//					.build();
//			if(authenticationService.register(admin)) {
//				System.out.println("Admin token: ");
//			}
//		};
//	}
}