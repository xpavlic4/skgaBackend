package com.laurinka.skga.server.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@Table
@NamedQueries({
        @NamedQuery(name = Club.BYNAME, query = "select s from Club s where s.name = :name")}
)
public class Club implements Serializable {
    /**
     * Default value included to remove warning. Remove or modify at will. *
     */
    private static final long serialVersionUID = 1L;
    public static final String BYNAME = "Club.byName";

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private
    String name;

    private Result.Type type;

    public Club() {
    }

    @Enumerated(EnumType.STRING)
    public Result.Type getType() {
        return type;
    }
    public void setType(Result.Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}