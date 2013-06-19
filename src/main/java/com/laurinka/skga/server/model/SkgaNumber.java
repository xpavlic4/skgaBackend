package com.laurinka.skga.server.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "nr"))
@NamedQueries({
        @NamedQuery(name = SkgaNumber.BYNR, query = "select s from SkgaNumber s where s.nr = :nr")}
)
public class SkgaNumber implements Serializable {
    public static final String BYNR = "skgaNumber.byNr";

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


    @Basic(optional = true)
    private String name2;
    
	public SkgaNumber() {
    }

    public SkgaNumber(String nr, String aname) {
        this.nr = nr;
        this.name = aname;
        date = new Date();
    }

    @PrePersist
    public void init() {
        setDate(new Date());
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
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

}
