package com.musea.model.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "genre")
@JsonIgnoreProperties(ignoreUnknown  = true)
@AttributeOverride(name = "id", column = @Column(name = "genre_id"))
public class Genre extends TimeDomain {
		
	@JsonProperty("nm")
	@Column(name = "genre_display_nm")
	private String displayName;
	
	@JsonIgnore
	@Column(name = "genre_nm")
	private String name;
	
	@JsonIgnore
	@Column(name = "manually_checked")
	private int manuallyChecked;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "song_genre_rel",
		joinColumns = @JoinColumn(name = "genre_id"),
		inverseJoinColumns = @JoinColumn(name = "song_id")
	)
	private Set<Song> songs;
	
	/*
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "genre")
	private Set<SongGenre> songsGenres;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "genre")
	private Set<ArtistGenre> artistGenres;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
		name = "song_genre_rel",
		schema = Domain.SCHEMA,
		joinColumns = @JoinColumn(name = "genre_id")
	)
	@MapKeyJoinColumn(name = "song_id")
	private Map<Song, EWeight> songsWeights;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
		name = "artist_genre_rel",
		schema = Domain.SCHEMA,
		joinColumns = @JoinColumn(name = "genre_id")
	)
	@MapKeyJoinColumn(name = "artist_id")
	private Map<Artist, EWeight> artistsWeights;
	*/
	
	// --- Getters / Setters ---
	public Set<Song> getSongs() {
		return songs;
	}
	public void setSongs(Set<Song> songs) {
		this.songs = songs;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = (displayName == null) ? null : displayName.trim();
		this.name = (displayName == null) ? null : this.displayName.toUpperCase();
	}
	public int getManuallyChecked() {
		return manuallyChecked;
	}
	public void setManuallyChecked(int manuallyChecked) {
		this.manuallyChecked = manuallyChecked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}