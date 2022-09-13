package com.fstop.eachadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ScheduledConfig {
	@Bean
	public String CronValue()
	{
	    return  "*/5 * * * * *";
	}
}
