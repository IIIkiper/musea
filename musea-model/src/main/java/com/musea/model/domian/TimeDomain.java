package com.musea.model.domian;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class TimeDomain extends Domain {
	
	@JsonIgnore
	@Column(name = "create_dt", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate = new Date();
	
	@JsonIgnore
	@Column(name = "last_upd_dt")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate = new Date();
	
	@PreUpdate
	public void preUpdate() {
		setUpdateDate(new Date());
	}
	
	// --- Getters / Setters ---
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}