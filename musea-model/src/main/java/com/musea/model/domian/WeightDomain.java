package com.musea.model.domian;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class WeightDomain extends TimeDomain {
	
	@Column(name = "weight_amt")
	private Float weight;
	
	// --- Getters / Setters ---
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
}