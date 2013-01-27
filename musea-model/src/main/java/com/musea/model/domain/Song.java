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
@Table(name = "song")
@AttributeOverride(name = "id", column = @Column(name = "song_id"))
public class Song extends Domain {
	
	@Column(name = "song_nm")
	private String name;
	
	@Column(name = "song_display_nm")
	private String displayName;
		
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "song_album_rel",
		joinColumns = @JoinColumn(name = "song_id"),
		inverseJoinColumns = @JoinColumn(name = "album_id")
	)
	private Set<Album> albums;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "song_collaboration_rel",
		joinColumns = @JoinColumn(name = "song_id"),
		inverseJoinColumns = @JoinColumn(name = "collaboration_id")
	)	
	private Set<Collaboration> collaborations;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "song")
	private Set<SongGenre> songsGenres;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "song")
	private Set<Audio> audios;
	
	/*
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "song_artist_rel",
		joinColumns = @JoinColumn(name = "song_id"),
		inverseJoinColumns = @JoinColumn(name = "artist_id")
	)
	private Set<Artist> artists;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
		name = "song_artist_rel",
		schema = Domain.SCHEMA,
		joinColumns = @JoinColumn(name = "song_id")
	)
	@MapKeyJoinColumn(name = "artist_id")
	private Map<Artist, ETimestamp> artists;
		
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
		name = "song_genre_rel",
		schema = Domain.SCHEMA,
		joinColumns = @JoinColumn(name = "song_id")
	)
	@MapKeyJoinColumn(name = "genre_id")
	private Map<Genre, EWeight> genresWeights;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
		name = "song_album_rel",
		schema = Domain.SCHEMA,
		joinColumns = @JoinColumn(name = "song_id")
	)
	@MapKeyJoinColumn(name = "album_id")
	private Map<Album, ETimestamp> albums;
	*/

	// --- Getters / Setters ---
	public Set<Album> getAlbums() {
		return albums;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Set<Audio> getAudios() {
		return audios;
	}
	public void setAudios(Set<Audio> audios) {
		this.audios = audios;
	}
	public void setAlbums(Set<Album> albums) {
		this.albums = albums;
	}
	public Set<SongGenre> getSongsGenres() {
		return songsGenres;
	}
	public void setSongsGenres(Set<SongGenre> songsGenres) {
		this.songsGenres = songsGenres;
	}
	public Set<Audio> getVkSongs() {
		return audios;
	}
	public void setVkSongs(Set<Audio> audios) {
		this.audios = audios;
	}
	public Set<Collaboration> getCollaborations() {
		return collaborations;
	}
	public void setCollaborations(Set<Collaboration> collaborations) {
		this.collaborations = collaborations;
	}
}