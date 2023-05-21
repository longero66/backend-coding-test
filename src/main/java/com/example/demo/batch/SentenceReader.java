package com.example.demo.batch;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class SentenceReader extends FlatFileItemReader<String> {

	private static final String FILE_PATH = "output.csv";

	public SentenceReader() {
		setResource(new ClassPathResource(FILE_PATH));
		setLineMapper(getDefaultLineMapper());
	}

	private PassThroughLineMapper getDefaultLineMapper() {
		return new PassThroughLineMapper();
	}
}
