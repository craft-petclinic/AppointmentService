package com.demo.petclinic.appointment.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The SwaggerConfig class maintains all the swagger configs and denotes the
 * base package of controller class to build api doc.
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * Method to handle api informations.
	 * 
	 * @param none.
	 * @return Docket
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.demo.petclinic.appointment.controller"))
				.paths(PathSelectors.regex("/.*")).build().apiInfo(apiEndPointsInfo());
	}

	/**
	 * Method to handle and show api information.
	 * 
	 * @param none.
	 * @return ApiInfo
	 */
	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("PetClinic Appointment Platform")
				.description("Apis Reference for Developers").termsOfServiceUrl("TOC")
				.contact(new Contact("Sarvesh Kumar", "", "kumar.sarvesh1309@gmail.com")).license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version("1.0.0").build();
	}

}