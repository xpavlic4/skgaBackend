package com.laurinka.skga.server.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@XmlRootElement
@Table
@NamedQueries({
		@NamedQuery(name = Snapshot.LAST20, query = "select s from Snapshot s order by s.id desc"),
		@NamedQuery(name = Snapshot.BY_NR, query = "select s from Snapshot s where s.result.skgaNr = :nr and createdAt >= :date and s.result.type=:system order by createdAt desc")}
		)
public class Snapshot implements Serializable {
	public static final String LAST20 = "snapshot.last20";
	public static final String BY_NR = "snapshot.bynr";
	/**
	 * Default value included to remove warning. Remove or modify at will. *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private Result result;

	@Basic(optional = false)
	private Date createdAt;

	public Snapshot() {
		setCreatedAt(new Date());
	}

	public Snapshot(Result aresult) {
		this();
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}