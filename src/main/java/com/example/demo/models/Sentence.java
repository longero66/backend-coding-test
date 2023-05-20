package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sentence")
public class Sentence {

	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "eng_text", length = 1024)
	private String engText;
	@Column(name = "audio_url")
	private String audioUrl;
	@Column(name = "vie_id")
	private String vieId;
	@Column(name = "vie_text", length = 1024)
	private String vieText;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEngText() {
		return engText;
	}

	public void setEngText(String engText) {
		this.engText = engText;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	@Override
	public String toString() {
		return "Sentence [id=" + id + ", engText=" + engText + ", audioUrl=" + audioUrl + ", vieId=" + vieId
				+ ", vieText=" + vieText + "]";
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public String getVieId() {
		return vieId;
	}

	public void setVieId(String vieId) {
		this.vieId = vieId;
	}

	public String getVieText() {
		return vieText;
	}

	public void setVieText(String vieText) {
		this.vieText = vieText;
	}

}
