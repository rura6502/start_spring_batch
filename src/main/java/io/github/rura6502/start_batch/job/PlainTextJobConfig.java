package io.github.rura6502.start_batch.job;

import java.util.Collections;
import java.util.List;

import io.github.rura6502.start_batch.core.domain.PlainText;
import io.github.rura6502.start_batch.core.repository.PlainTextRepository;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PlainTextJobConfig {

  private final JobBuilderFactory jobFactory;
  private final StepBuilderFactory stepFactory;
  private final PlainTextRepository repository;

  @Bean("plainTextJob")
  public Job plainTextJob(Step plainTextStep) {
    return jobFactory
      .get("plainTextJob")
      .incrementer(new RunIdIncrementer())
      .start(plainTextStep)
      .build();
  }

  @JobScope
  @Bean("plainTextStep")
  public Step plainTextStep(
    ItemReader plainTextReader, ItemProcessor plainTextProcessor, ItemWriter plainTedxtWriter) {
    return stepFactory.get("helloStep")
      .<PlainText, String>chunk(5)
      .reader(plainTextReader)
      .processor(plainTextProcessor)
      .writer(plainTedxtWriter)
      .build();
  }

  @StepScope
  @Bean
  public RepositoryItemReader<PlainText> plainTextReader() {
    return new RepositoryItemReaderBuilder<PlainText>()
      .name("plainTextReader")
      .repository(repository)
      .methodName("findBy")
      .pageSize(5)
      .arguments(List.of())
      .sorts(Collections.singletonMap("id", Sort.Direction.DESC))
      .build();
  }

  @StepScope
  @Bean
  public ItemProcessor<PlainText, String> plainTextProcessor() {
    return item -> "processed " + item.getText();
  }

  @StepScope
  @Bean
  public ItemWriter<String> plainTextWriter() {
    return items -> {
      items.forEach(System.out::println);
      System.out.println("=== check is finished");
    };
  }

}
