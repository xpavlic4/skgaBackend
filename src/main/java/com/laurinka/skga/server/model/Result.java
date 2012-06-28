package com.laurinka.skga.server.model;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Embeddable;

@Embeddable
public class Result {

	private String hcp;
	private String skgaNr;
	private String club;
	private String name;


	public void setClub(String club) {
		this.club = club;
	}

	public String getSkgaNr() {
		return skgaNr;
	}

	public void setSkgaNr(String skgaNr) {
		this.skgaNr = skgaNr;
	}

	public void setHcp(String hcp) {
		this.hcp = hcp;
	}

	public String getHcp() {
		return hcp;
	}

	public String getClub() {
		return club;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Result [hcp=" + hcp + ", skgaNr=" + skgaNr + ", club=" + club
				+ ", name=" + name + "]";
	}

	public Map<String, String> asMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("hcp", getHcp());
		map.put("skgaNr", getSkgaNr());
		map.put("club", getClub());
		map.put("name", getName());
		return map;
	}
	
}
