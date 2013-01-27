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
@Table(name = "song_genre_rel")
@IdClass(SongGenreID.class)
public class SongGenre extends WeightLink {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "song_id")
	private Song song;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "genre_id")
	private Genre genre;

	// --- Getters / Setters ---
	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
}

class SongGenreID implements Serializable {	
	private static final long serialVersionUID = 2439454950941551L;
	
	private long song;
	private long genre;
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj != null && obj instanceof SongGenreID) {
			SongGenreID sgi = (SongGenreID) obj;
			return sgi.song == song && sgi.genre == genre;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) (super.hashCode() + song + genre);
	}	
}