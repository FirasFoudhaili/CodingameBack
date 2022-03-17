package com.byblos.evaluation.evaluationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@Configuration
@EnableJpaRepositories
@ComponentScan(basePackages = {"com.byblos.evaluation.evaluationservice.dtos", "com.byblos.evaluation.evaluationservice.mappers","com.byblos.evaluation.evaluationservice.models", "com.byblos.evaluation.evaluationservice.repositories", "com.byblos.evaluation.evaluationservice.controllers", "com.byblos.evaluation.evaluationservice.services","com.byblos.evaluation.evaluationservice.security"})
@EntityScan(basePackages = "com.byblos.evaluation.EvaluationService.Models")
@EnableSwagger2
@EnableScheduling
public class EvaluationServiceApplication {

	public static void main(String[] argv)
	{
		SpringApplication.run(EvaluationServiceApplication.class);
	}

}
