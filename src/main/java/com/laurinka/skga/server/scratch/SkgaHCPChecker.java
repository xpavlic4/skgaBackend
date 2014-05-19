package com.laurinka.skga.server.scratch;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.laurinka.skga.server.model.Result;

public class SkgaHCPChecker {
	Logger log = Logger.getLogger(SkgaHCPChecker.class.getName());

	final String ID_HCP = "ctl00_RightContentPlaceholder_lbHcp";
	final String ID_NR = "ctl00_RightContentPlaceholder_lbMemberNumber";
	final String ID_CLUB = "ctl00_RightContentPlaceholder_lbClub";
	
	public Result query(SkgaGolferNumber nr) throws IOException {
		String url = "http://data.skga.sk/CheckHcp.aspx?MemberNumber=" + nr.asString()
				+ "&button_dosearch=";
//        Document document = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
		final Connection connect = Jsoup.connect(url).timeout(Constants.TIMEOUT_IN_SECONDS);
		connect.header("Accept-Charset", "utf-8");
		Document document = connect.get();
		
		
		if (!isValid(document))
			return null;
		Result result = Result.newSkga();
		findHcp(document, result);
		findNumber(document, result);
		findClub(document, result);
		findName(document, result);
		return result;
	}

	private boolean isValid(Document document) {
		Element elementById = document.getElementById("ctl00_lblMessage");
		return elementById == null;
	}

	private void findName(Document document, Result result) {
		Elements elementsByClass = document.getElementsByClass("bigbobhead");
		Element next = elementsByClass.iterator().next();
		Elements bs = next.getElementsByTag("b");
		if (!bs.iterator().hasNext())
			return;
		Element b = bs.iterator().next();
		String text = b.text();
        log.info("Name: " + text);
		result.setName(text);
	}

	private Result findHcp(Document document, Result result) {
		String hcp = document.getElementById(
				ID_HCP).text();
		if ("".equals(hcp))
			return result;
		result.setHcp(hcp);
		return result;
	}

	private void findNumber(Document document, Result result) {
		String skganr = document.getElementById(
				ID_NR).text();
		result.setSkgaNr(skganr);
	}

	private void findClub(Document document, Result result) {
		String club = document.getElementById(ID_CLUB).text();
        log.info("Club: "+ club);
		result.setClub(club);
	}

}
