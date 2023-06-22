package com.itec1.FinalProjectWeb;

import com.itec1.FinalProjectWeb.config.SpringConfig;
import com.itec1.FinalProjectWeb.util.ScopeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FinalProjectWebApplication {

	public static void main(String[] args) {
		ScopeUtils.calculateScopeSuffix();
		new SpringApplicationBuilder(SpringConfig.class)
				.registerShutdownHook(true)
				.run(args);
	}

}
