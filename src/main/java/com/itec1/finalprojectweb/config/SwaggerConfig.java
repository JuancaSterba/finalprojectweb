package com.itec1.finalprojectweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableSwagger2
//@Configuration
public class SwaggerConfig {

	public static final Contact DEFAULT_CONTACT = new Contact("Betancur Damián Ariel", "",
			"betancurdamian@gmail.com");
	public static final ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder()
			.title("Aplicación Agencia de Turismo")
			.description("Aplicacion completa con seguridad")
			.license("Apache 2.0")
			.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
			.termsOfServiceUrl("")
			.version("1.0.0")
			.contact(new Contact("", "", "betancurdamian@gmail.com"))
			.build();

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_API_INFO)
				.securitySchemes(Arrays.asList(apiKey()))
				.securityContexts(Collections.singletonList(securityContext()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.directModelSubstitute(LocalDate.class, String.class)
				.directModelSubstitute(LocalTime.class, String.class)
				.directModelSubstitute(LocalDateTime.class, String.class);
	}

	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder()
				.deepLinking(true)
				.displayOperationId(false)
				.defaultModelsExpandDepth(1)
				.defaultModelExpandDepth(1)
				.defaultModelRendering(ModelRendering.EXAMPLE)
				.displayRequestDuration(false)
				.docExpansion(DocExpansion.NONE)
				.filter(false)
				.maxDisplayedTags(null)
				.operationsSorter(OperationsSorter.ALPHA)
				.showExtensions(false)
				.tagsSorter(TagsSorter.ALPHA)
				.validatorUrl(null)
				.build();
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*")).build();
	}

	private List<SecurityReference> defaultAuth() {
		final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
		return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
	}

	private ApiKey apiKey() {
		return new ApiKey("Bearer", "Authorization", "header");
	}



}
