package com.musea.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RateItemDto {
	
	private long id;
	@JsonProperty("r")
	private long rating;
	@JsonProperty("nm")
	private String name;
	
	public RateItemDto() { }
	
	public RateItemDto(long id, long rating, String name) {
		this.id = id;
		this.rating = rating;
		this.name = name;
	}
	
	// --- Getters / Setters ---
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRating() {
		return rating;
	}
	public void setRating(long rating) {
		this.rating = rating;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}