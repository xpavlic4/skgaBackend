package com.laurinka.skga.server.model;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table
public class Snapshot implements Serializable {
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private Result result;
	
	
	@ManyToOne
	private SnapshotRun run;
	
	public Snapshot() {
	}

	public Snapshot(Result aresult, SnapshotRun arun) {
		this.run = arun;
		this.result = aresult;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public SnapshotRun getRun() {
		return run;
	}

	public void setRun(SnapshotRun run) {
		this.run = run;
	}
}