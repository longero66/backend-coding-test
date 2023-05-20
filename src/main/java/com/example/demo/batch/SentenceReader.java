package com.example.demo.batch;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.demo.models.Sentence;

@Component
public class SentenceReader extends FlatFileItemReader<Sentence> {

	private static final String FILE_PATH = "output.csv";

	public SentenceReader() {
		setResource(new ClassPathResource(FILE_PATH));
		setLineMapper(getDefaultLineMapper());
	}

	private DefaultLineMapper<Sentence> getDefaultLineMapper() {
		DefaultLineMapper<Sentence> defaultLineMapper = new DefaultLineMapper<Sentence>();

		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);
		delimitedLineTokenizer.setNames(new String[] { "id", "engText", "audioUrl", "vieId", "vieText" });
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

		BeanWrapperFieldSetMapper<Sentence> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<Sentence>();
		beanWrapperFieldSetMapper.setTargetType(Sentence.class);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		return defaultLineMapper;
	}

}
