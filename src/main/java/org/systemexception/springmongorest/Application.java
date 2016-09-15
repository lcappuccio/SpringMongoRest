package org.systemexception.springmongorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author leo
 * @date 19/09/15 11:56
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
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
		return new ApiInfo(
				"SpringBoot MongoDb Rest API",
				"An example REST API with SpringBoot and MongoDb",
				"0.2",
				null,
				"leo@systemexception.org",
				null,
				"https://github.com/lcappuccio/SpringMongoRest/blob/master/LICENSE"
		);
	}

}
