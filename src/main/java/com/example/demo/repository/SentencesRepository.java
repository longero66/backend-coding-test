package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Sentence;

public interface SentencesRepository extends JpaRepository<Sentence, String> {

}
