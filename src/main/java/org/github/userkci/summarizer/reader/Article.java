package org.github.userkci.summarizer.reader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.github.userkci.summarizer.webclient.Link;

public class Article {

	private final String url;
	private final String title;
	private final String text;
	private final List<Link> links;
	
	public Article(String url, String title, String text, Collection<Link> links) {
		this.url= url;
		this.title= title;
		this.text= text;
		this.links= Collections.unmodifiableList(new ArrayList<>(links));
	}
	
	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public List<Link> getLinks() {
		return links;
	}
}
