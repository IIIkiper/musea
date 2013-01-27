package com.musea.model.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "artist")
@AttributeOverride(name = "id", column = @Column(name = "artist_id"))
public class Artist extends TimeDomain {
	
	@Column(name = "artist_nm")
	private String name;
	
	@Column(name = "artist_display_nm")
	private String displayName;
		
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "artist")
	private Set<ArtistGenre> artistsGenres;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "collaboration_artist_rel",
		joinColumns = @JoinColumn(name = "artist_id"),
		inverseJoinColumns = @JoinColumn(name = "collaboration_id")
	)
	private Set<Collaboration> collaborations;
	
	/*
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "artists")
	private Set<Song> songs;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
		name = "song_artist_rel",
		schema = Domain.SCHEMA,
		joinColumns = @JoinColumn(name = "artist_id")
	)
	@MapKeyJoinColumn(name = "song_id")
	private Map<Song, ETimestamp> songs;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
		name = "artist_genre_rel",
		schema = Domain.SCHEMA,
		joinColumns = @JoinColumn(name = "artist_id")
	)
	@MapKeyJoinColumn(name = "genre_id")
	private Map<Genre, EWeight> genresWeights;
	*/
	
	// --- Getters / Setters ---
	public Set<Collaboration> getCollaborations() {
		return collaborations;
	}
	public void setCollaborations(Set<Collaboration> collaborations) {
		this.collaborations = collaborations;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<ArtistGenre> getArtistsGenres() {
		return artistsGenres;
	}
	public void setArtistsGenres(Set<ArtistGenre> artistsGenres) {
		this.artistsGenres = artistsGenres;
	}
}