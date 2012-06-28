package com.laurinka.skga.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table
public class SnapshotRun implements Serializable {
	
	@Id
	@GeneratedValue
	private Long id;

	private static final long serialVersionUID = 5974836977140765502L;
	@Basic(optional = false)
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@PrePersist
	public void init() {
		setDate(new Date());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
