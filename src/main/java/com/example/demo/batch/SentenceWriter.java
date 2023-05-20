package com.example.demo.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.models.Sentence;
import com.example.demo.repository.SentencesRepository;

@Component
public class SentenceWriter implements ItemWriter<Sentence> {

	private SentencesRepository sentencesRepository;

	@Autowired
	public SentenceWriter(SentencesRepository sentencesRepository) {
		this.sentencesRepository = sentencesRepository;
	}

	@Override
	public void write(List<? extends Sentence> items) throws Exception {
		sentencesRepository.saveAll(items);
	}

}
