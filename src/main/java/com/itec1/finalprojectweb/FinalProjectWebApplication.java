package com.itec1.finalprojectweb;

import com.itec1.finalprojectweb.config.SpringConfig;
import com.itec1.finalprojectweb.util.ScopeUtils;
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
