package com.example.demo.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.demo.models.Sentence;

@Component
public class SentenceProcessor implements ItemProcessor<String, Sentence> {

	private static final String TAB = "\t";

	@Override
	public Sentence process(String item) throws Exception {
		String[] split = item.split(TAB);
		if (split.length == 0) {
			return null;
		}
		Sentence sentence = new Sentence();
		sentence.setId(split[0]);
		if (split.length >= 2) {
			sentence.setEngText(split[1]);
		}
		if (split.length >= 3) {
			sentence.setAudioUrl(split[2]);
		}
		if (split.length >= 4) {
			sentence.setVieId(split[3]);
		}
		if (split.length >= 5) {
			sentence.setVieText(split[4]);
		}
		return sentence;
	}

}
