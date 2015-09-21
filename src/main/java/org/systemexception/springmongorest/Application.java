package org.systemexception.springmongorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

/**
 * @author leo
 * @date 19/09/15 11:56
 */
@ComponentScan("org.systemexception.springmongorest.*")
@EnableAutoConfiguration
@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

		System.out.println("Initializing beans");

		String[] beans = applicationContext.getBeanDefinitionNames();
		Arrays.sort(beans);

		for (String bean: beans) {
			System.out.println("Found bean: " + bean);
		}
	}

	@Bean
	public Docket restfulApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("restful-api")
				.select()
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {

		ApiInfo apiInfo = new ApiInfo(
				"SpringBoot MongoDb Rest API",
				"An example REST API with SpringBoot and MongoDb",
				"0.2",
				null,
				"leo@systemexception.org",
				null,
				"https://github.com/lcappuccio/SpringMongoRest/blob/master/LICENSE"
		);
		return apiInfo;
	}

}
