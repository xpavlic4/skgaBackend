package com.laurinka.skga.server.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NameNumberXml {
	private String name;
	private String number;
	
	public NameNumberXml() {
	}
	public NameNumberXml(String name, String number) {
		super();
		this.name = name;
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}
