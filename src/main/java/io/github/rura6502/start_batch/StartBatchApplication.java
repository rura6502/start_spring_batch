package io.github.rura6502.start_batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class StartBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartBatchApplication.class, args);
	}

}
