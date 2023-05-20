package com.example.demo.service.impl;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.models.Sentence;
import com.example.demo.repository.SentencesRepository;
import com.example.demo.service.CSVService;

@Service
public class CSVServiceImpl implements CSVService {

	private SentencesRepository sentencesRepository;

	private JobLauncher jobLauncher;

	private Job job;

	@Autowired
	public CSVServiceImpl(SentencesRepository sentencesRepository, JobLauncher jobLauncher, Job job) {
		this.sentencesRepository = sentencesRepository;
		this.job = job;
		this.jobLauncher = jobLauncher;
	}

	@Override
	public void generateCSV() {

	}

	@Override
	public List<Sentence> getSentences(Integer page) {
		Pageable paging = PageRequest.of(page, 10);
		Page<Sentence> results = sentencesRepository.findAll(paging);
		return results.getContent();
	}

	@Override
	public void createData(List<Sentence> sentences) {
		sentencesRepository.saveAll(sentences);
	}

	@Override
	@Async
	public void importData() {
		JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
		try {
			jobLauncher.run(job, jobParameters);
		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}

	}

}
