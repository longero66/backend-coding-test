package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Sentence;
import com.example.demo.service.CSVService;

@RestController
public class TestController {

	private CSVService csvService;

	@Autowired
	public TestController(CSVService csvService) {
		this.csvService = csvService;
	}

	@PutMapping(value = "/generate")
	public ResponseEntity<Void> generateCSV() {
		csvService.generateCSV();
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping(value = "/sentence")
	public ResponseEntity<List<Sentence>> getSentences(@RequestParam Integer page) {
		List<Sentence> results = csvService.getSentences(page);
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@PostMapping(value = "/sentence")
	public ResponseEntity<Void> createSentences(@RequestBody List<Sentence> sentences) {
		csvService.createData(sentences);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping(value = "/sentence")
	public ResponseEntity<Void> updateSentencesToDB() {
		csvService.importData();
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
