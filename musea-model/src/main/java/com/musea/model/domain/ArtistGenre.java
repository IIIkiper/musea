package com.musea.model.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "artist_genre_rel")
@IdClass(ArtistGenreID.class)
public class ArtistGenre extends WeightLink {
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "artist_id")
	private Artist artist;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "genre_id")
	private Genre genre;

	// --- Geters / Setters ---
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
}

class ArtistGenreID implements Serializable {	
	private static final long serialVersionUID = 2439454950941551L;
	
	private long artist;
	private long genre;
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj != null && obj instanceof SongGenreID) {
			ArtistGenreID agi = (ArtistGenreID) obj;
			return agi.artist == artist && agi.genre == genre;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) (super.hashCode() + artist + genre);
	}	
}