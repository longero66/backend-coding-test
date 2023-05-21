package com.example.demo.config;

import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.batch.SentenceProcessor;
import com.example.demo.batch.SentenceReader;
import com.example.demo.batch.SentenceWriter;
import com.example.demo.models.Sentence;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	public JobBuilderFactory jobBuilderFactory;

	public StepBuilderFactory stepBuilderFactory;

	private SentenceReader sentenceReader;

	private SentenceWriter sentenceWriter;

	private SentenceProcessor sentenceProcessor;

	@Autowired
	public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			SentenceReader sentenceReader, SentenceProcessor sentenceProcessor, SentenceWriter sentenceWriter) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.sentenceReader = sentenceReader;
		this.sentenceWriter = sentenceWriter;
		this.sentenceProcessor = sentenceProcessor;
	}

	@Bean
	public BatchConfigurer batchConfigurer(EntityManagerFactory entityManagerFactory, DataSource dataSource) {
		return new DefaultBatchConfigurer(dataSource) {
			@Override
			public PlatformTransactionManager getTransactionManager() {
				return new JpaTransactionManager(entityManagerFactory);
			}

			@Override
			public JobRepository getJobRepository() {
				JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
				jobRepositoryFactoryBean.setDataSource(dataSource);
				jobRepositoryFactoryBean.setTransactionManager(getTransactionManager());
				// set other properties
				try {
					return jobRepositoryFactoryBean.getObject();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		};
	}

	@Bean
	public Job createJob() {
		return jobBuilderFactory.get(UUID.randomUUID().toString()).incrementer(new RunIdIncrementer())
				.flow(createStep()).end().build();
	}

	@Bean
	public Step createStep() {
		return stepBuilderFactory.get(UUID.randomUUID().toString()).<String, Sentence>chunk(100).reader(sentenceReader)
				.processor(sentenceProcessor).writer(sentenceWriter).build();
	}

}
