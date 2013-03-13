package com.laurinka.skga.server.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.laurinka.skga.server.model.Result.Type;

@Entity
@Table
public class LastSync {
	@Id
	@GeneratedValue
	private Long id;
	private Type type;
	@Basic(optional = false)
	private int  nr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	public int getNr() {
		return nr;
	}
	public void setNr(int nr) {
		this.nr = nr;
	}

}
