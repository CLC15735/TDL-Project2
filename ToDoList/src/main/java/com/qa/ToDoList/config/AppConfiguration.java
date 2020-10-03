package com.qa.ToDoList.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfiguration {
	
	@Bean
	@Scope("prototype")
	public ModelMapper mapper() {
		return new ModelMapper();
	}

}
