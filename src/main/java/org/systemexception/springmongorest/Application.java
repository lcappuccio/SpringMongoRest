package org.systemexception.springmongorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author leo
 * @date 19/09/15 11:56
 */
@ComponentScan("org.systemexception.springmongorest.*")
@EnableAutoConfiguration
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		application.showBanner(false);
		application.profiles("production");
		return application.sources(Application.class);
	}

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(Application.class);
		app.setAdditionalProfiles("development");
		app.setShowBanner(false);
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
