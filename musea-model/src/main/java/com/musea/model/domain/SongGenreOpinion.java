package com.musea.model.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(SongGenreOpinionID.class)
@Table(name = "song_genre_opinion")
public class SongGenreOpinion extends WeightLink {
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "song_id")
	private Song song;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "genre_id")
	private Genre genre;
	
	@Column(name = "genre_id", insertable = false, updatable = false, nullable = false)
	private int genreId;
	
	@Column(name = "song_id", insertable = false, updatable = false, nullable = false)
	private int songId;
	
	@Column(name = "user_id", insertable = false, updatable = false, nullable = false)
	private int userId;
	
	@Column(name = "vote_for")
	private int voteFor;
	
	@Column(name = "vote_against")
	private int voteAgainst;
		
	public boolean isVoteFor() {
		return voteFor == 1;
	}
	public void setVoteFor(boolean voteFor) {
		this.voteFor = voteFor ? 1 : 0;
	}
	public boolean isVoteAgainst() {
		return voteAgainst == 1;
	}
	public void setVoteAgainst(boolean voteAgainst) {
		this.voteAgainst = voteAgainst ? 1 : 0;
	}

	// --- Getters / Setters ---
	public int getGenreId() {
		return genreId;
	}
	public int getSongId() {
		return songId;
	}
	public int getUserId() {
		return userId;
	}
	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
}

class SongGenreOpinionID implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long song;
	private long genre;
	private long user;
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj != null && obj instanceof SongGenreOpinionID) {
			SongGenreOpinionID sgoi = (SongGenreOpinionID) obj;
			return sgoi.song == song && sgoi.genre == genre && sgoi.user == user;
		}
		return false;
	}
	
	public int hashCode() {
		return super.hashCode();
	}
}