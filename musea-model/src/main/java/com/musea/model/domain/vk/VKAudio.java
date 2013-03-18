package com.musea.model.domain.vk;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.musea.model.domain.Audio;

@Entity
@DiscriminatorValue("1")
public class VKAudio extends Audio { }