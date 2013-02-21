package com.musea.model.domain.vk;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.musea.model.domain.Audio;

@Entity
@DiscriminatorValue("1")
@SuppressWarnings("unchecked")
public class VKAudio extends Audio {
	
	@Access(AccessType.PROPERTY)
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "audios")
	public Set<VKUser> getUsers() {
		return (Set<VKUser>) super.getUsers();
	}
	public void setUsers(Set<VKUser> users) {
		this.users = users;
	}
}