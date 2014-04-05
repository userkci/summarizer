package org.github.userkci.summarizer.webclient.jsoup;

import java.util.ArrayList;
import java.util.List;

import org.github.userkci.summarizer.webclient.Link;
import org.github.userkci.summarizer.webclient.WebPage;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupWebPageWrapper implements WebPage {
	
	private final Document page;
	
	public JsoupWebPageWrapper(Document document) {
		this.page= document;
	}

	@Override
	public String getElementText(String cssSelector) {
		Elements bodyElement= page.select(cssSelector);
		if (bodyElement.isEmpty()) {
			return "";
		}
		return bodyElement.get(0).text();
	}

	@Override
	public List<Link> getLinks(String cssSelector) {
		Elements linkElements= page.select(cssSelector + " a[href]");
		List<Link> links= new ArrayList<>(linkElements.size());
		for (Element e : linkElements) {
			links.add(new Link(e.attr("abs:href"), e.text()));
		}
		return links;
	}

}
