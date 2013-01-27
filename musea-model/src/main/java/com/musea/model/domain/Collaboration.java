package com.musea.model.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "collaboration")
@AttributeOverride(name = "id", column = @Column(name = "collaboration_id"))
public class Collaboration extends Domain { 
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "collaborations")
	private Set<Song> songs;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "collaborations")
	private Set<Artist> artists;

	// --- Getters / Setters ---
	public Set<Song> getSongs() {
		return songs;
	}
	public void setSongs(Set<Song> songs) {
		this.songs = songs;
	}
	public Set<Artist> getArtists() {
		return artists;
	}
	public void setArtists(Set<Artist> artists) {
		this.artists = artists;
	}
}