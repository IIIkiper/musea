package com.musea.model.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "album")
@AttributeOverride(name = "id", column = @Column(name = "album_id"))
public class Album extends TimeDomain {
	
	@Column(name = "album_nm")
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "albums")
	private Set<Song> songs;
	
	/*
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
		name = "song_album_rel",
		schema = Domain.SCHEMA,
		joinColumns = @JoinColumn(name = "album_id")
	)
	@MapKeyJoinColumn(name = "song_id")
	private Map<Song, ETimestamp> songs;
	*/
	
	// --- Getters / Setters ---
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Song> getSongs() {
		return songs;
	}
	public void setSongs(Set<Song> songs) {
		this.songs = songs;
	}
}