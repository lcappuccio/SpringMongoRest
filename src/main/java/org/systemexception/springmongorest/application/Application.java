package org.systemexception.springmongorest.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

/**
 * @author leo
 * @date 19/09/15 11:56
 */
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

		System.out.println("Initializing beans");

		String[] beans = applicationContext.getBeanDefinitionNames();
		Arrays.sort(beans);

		for (String bean: beans) {
			System.out.println("Found bean: " + bean);
		}
	}

}
