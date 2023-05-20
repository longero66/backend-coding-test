package com.example.demo.service;

import java.util.List;

import com.example.demo.models.Sentence;

public interface CSVService {

	void generateCSV();

	List<Sentence> getSentences(Integer page);

	void createData(List<Sentence> sentences);

	void importData();

}
