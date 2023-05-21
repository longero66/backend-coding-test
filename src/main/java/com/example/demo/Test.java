package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test {

	private static final String TAB = "\t";
	private static final String VIE_LANG = "vie";
	private static final String ENG_LANG = "eng";
	private static final String LINK = "src/main/resources/links.csv";
	private static final String SENTENCES = "src/main/resources/sentences.csv";
	private static final String AUDIO = "src/main/resources/sentences_with_audio.csv";
	private static final String OUTPUT = "src/main/resources/output.csv";
	private static final String OUTPUTENG = "src/main/resources/outputeng.csv";
	private static Set<String> ENG_ID = new HashSet<>();
	private static Map<String, String> VIE_MAP = new HashMap<>();
	private static Map<String, String> LINK_MAP = new HashMap<>();
	private static Map<String, String> AUDIO_MAP = new HashMap<>();

	public static void generateCSV() {
		generateTempCSV();
		generateTempLink();
		generateTempAudio();
		try {
			BufferedReader outputEng = new BufferedReader(new FileReader(OUTPUTENG));
			BufferedReader audio = new BufferedReader(new FileReader(AUDIO));
			File output = new File(OUTPUT);
			FileOutputStream fos = new FileOutputStream(output);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
			while (outputEng.ready()) {

				String outputEngLine = outputEng.readLine();
				String[] outputEngLineSplit = outputEngLine.split(TAB);
				String vieIdTrans = LINK_MAP.get(outputEngLineSplit[0]);
				writer.write(outputEngLineSplit[0] + TAB + outputEngLineSplit[1] + TAB
						+ AUDIO_MAP.get(outputEngLineSplit[0]) + TAB + vieIdTrans + TAB + VIE_MAP.get(vieIdTrans));
				writer.newLine();
				writer.flush();
			}
			writer.close();
			outputEng.close();
			audio.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void generateTempAudio() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(AUDIO));
			while (reader.ready()) {
				String audioLine = reader.readLine();
				String[] audioLineSplit = audioLine.split(TAB);
				if (ENG_ID.contains(audioLineSplit[0])) {
					if (audioLineSplit.length < 4) {
						AUDIO_MAP.put(audioLineSplit[0], "\\N");
					} else {
						AUDIO_MAP.put(audioLineSplit[0], audioLineSplit[3]);
					}

				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void generateTempLink() {
		try {
			BufferedReader link = new BufferedReader(new FileReader(LINK));
			while (link.ready()) {
				String lineLink = link.readLine();
				String[] lineLinkSplit = lineLink.split(TAB);
				if (ENG_ID.contains(lineLinkSplit[0]) && VIE_MAP.containsKey(lineLinkSplit[1])) {
					LINK_MAP.put(lineLinkSplit[0], lineLinkSplit[1]);
				}
			}
			link.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generateTempCSV() {
		try {
			BufferedReader sentences = new BufferedReader(new FileReader(SENTENCES));
			File output = new File(OUTPUTENG);
			FileOutputStream fos = new FileOutputStream(output);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
			while (sentences.ready()) {
				String lineSentences = sentences.readLine();
				String[] lineSentencesSplit = lineSentences.split(TAB);
				if (lineSentencesSplit[1].equals(VIE_LANG)) {
					VIE_MAP.put(lineSentencesSplit[0], lineSentencesSplit[2]);
				} else if (lineSentencesSplit[1].equals(ENG_LANG)) {
					writer.write(lineSentencesSplit[0] + TAB + lineSentencesSplit[2]);
					writer.newLine();
					writer.flush();
					ENG_ID.add(lineSentencesSplit[0]);
				}
			}
			sentences.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		generateCSV();
	}

}
