package com.laurinka.skga.server.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "nr"))
@NamedQueries({
        @NamedQuery(name = CgfNumber.BYNR, query = "select s from CgfNumber s where s.nr = :nr")}
)

public class CgfNumber implements Serializable {

    public static final String BYNR = "cgfnumber.bynr";
    @Id
    @GeneratedValue
    private Long id;

    private static final long serialVersionUID = 5974836977140765502L;
    @Basic(optional = true)
    private Date date;


    @Basic(optional = false)
    private String nr;


    @Basic(optional = true)
    private String name;

    @ManyToOne
    private Club club;

    @Basic(optional = true)
    private String name2;
    
    public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public CgfNumber() {
    }

    public CgfNumber(String nr, String aname) {
        this.nr = nr;
        this.name = aname;
        date = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @Override
    public String toString() {
        return "CgfNumber{" +
                "id=" + id +
                ", date=" + date +
                ", nr='" + nr + '\'' +
                ", name='" + name + '\'' +
                ", club=" + club +
                ", name2='" + name2 + '\'' +
                '}';
    }
}
