package com.musea.service.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGenreCollection {
	
	@JsonProperty(value = "user", required = true)
	private long systemId;
	
	@JsonProperty(value = "genres")
	private Collection<Object[]> genres = new ArrayList<>();
	
	// --- PUBLIC METHODS ---
	public void addGenre(Object[] genre) {
		if (genre != null) {
			genres.add(genre);
		}
	}

	// --- GETTERS / SETTERS ---
	public long getSystemId() {
		return systemId;
	}
	public void setSystemId(long systemId) {
		this.systemId = systemId;
	}
	public Collection<Object[]> getGenres() {
		return genres;
	}
	public void setGenres(Collection<Object[]> genres) {
		this.genres = genres;
	}
}
