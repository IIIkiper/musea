package com.musea.model.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "vk_song")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "source_system_cd_id", discriminatorType = DiscriminatorType.INTEGER)
@AttributeOverride(name = "id", column = @Column(name = "vk_song_id"))
public abstract class Audio extends TimeDomain {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "song_id")
	private Song song;
	
	@Transient
	protected Set<? extends User> users;
	
	@Column(name = "source_system_cd_id", insertable = false, updatable = false)
	private int sourceId;
	
	@Column(name = "aid", nullable = false)
	@JsonProperty(value = "aid", required = true)
	private int systemId;
	
	@Column(name = "artist")
	private String artist;
	
	@Column(name = "album")
	private String album;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "duration")
	private Integer duration;
	
	@Override
	public boolean equals(Object obj) {
		try {
			return this == obj || (obj != null && systemId == ((Audio) obj).systemId && sourceId == ((Audio) obj).sourceId);
		} catch (Exception ex) {
			return false;
		}
	}

	// --- Getters / Setters ---
	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}
	public int getSystemId() {
		return systemId;
	}
	public void setSystemId(int systemId) {
		this.systemId = systemId;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Set<? extends User> getUsers() {
		return users;
	}
}