package com.example.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

	@Autowired
	public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			SentenceReader sentenceReader, SentenceWriter sentenceWriter) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.sentenceReader = sentenceReader;
		this.sentenceWriter = sentenceWriter;
	}

	@Bean
	public Job createJob() {
		return jobBuilderFactory.get("MyJob").incrementer(new RunIdIncrementer()).flow(createStep()).end().build();
	}

	@Bean
	public Step createStep() {
		return stepBuilderFactory.get("MyStep").<Sentence, Sentence>chunk(10).reader(sentenceReader)
				.writer(sentenceWriter).build();
	}

}
