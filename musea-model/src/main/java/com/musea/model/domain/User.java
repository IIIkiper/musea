package com.musea.model.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "source_system_cd_id", discriminatorType = DiscriminatorType.INTEGER)
@AttributeOverrides({
	@AttributeOverride(name = "updateDate", column = @Column(name = "song_last_upd_dt")),
	@AttributeOverride(name = "id", column = @Column(name = "user_id"))
})
@Table(name = "user")
public class User extends TimeDomain {

	@Column(name = "source_system_user_id", nullable = false)
	private long systemId;
	
	@Column(name = "user_nm")
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "friendship",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "friend_id")
	)
	private Set<User> friends;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "vk_user_vk_song_rel",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "vk_song_id")
	)
	private Set<Audio> audios;
	
	// --- Getters / Setters ---
	public long getSystemId() {
		return systemId;
	}
	public void setSystemId(long systemId) {
		this.systemId = systemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<User> getFriends() {
		return friends;
	}
	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}
	public Set<Audio> getAudios() {
		return audios;
	}
	public void setAudios(Set<Audio> audios) {
		this.audios = audios;
	}
}