package com.fis.app.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Shedlock {

	@Id
	private String name;
	
	private Timestamp lockUntil;
	
	private Timestamp lockedAt;
	
	private String lockedBy;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getLockUntil() {
		return lockUntil;
	}

	public void setLockUntil(Timestamp lockUntil) {
		this.lockUntil = lockUntil;
	}

	public Timestamp getLockedAt() {
		return lockedAt;
	}

	public void setLockedAt(Timestamp lockedAt) {
		this.lockedAt = lockedAt;
	}

	public String getLockedBy() {
		return lockedBy;
	}

	public void setLockedBy(String lockedBy) {
		this.lockedBy = lockedBy;
	}
	
	
}
