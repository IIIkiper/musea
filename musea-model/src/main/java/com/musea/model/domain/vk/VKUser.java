package com.musea.model.domain.vk;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

import com.musea.model.domain.User;

@Entity
@DiscriminatorValue("1")
@SuppressWarnings("unchecked")
public class VKUser extends User {
			
	@Access(AccessType.PROPERTY)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "friendship",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "friend_id")
	)
	@Override
	public Set<VKUser> getFriends() {
		return (Set<VKUser>) getFriends();
	}	
	public void setFriends(Set<VKUser> friends) {
		this.friends = friends;
	}
	
	@Access(AccessType.PROPERTY)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "vk_user_vk_song_rel",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "vk_song_id")
	)
	@Override
	public Set<VKAudio> getAudios() {
		return (Set<VKAudio>) super.getAudios();
	}
	public void setAudios(Set<VKAudio> audios) {
		this.audios = audios;
	}
}