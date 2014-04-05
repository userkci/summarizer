package org.github.userkci.summarizer.webclient;

import java.util.List;

public interface WebPage {
	
	/** Get text of selected element **/
	public String getElementText(String cssSelector);
	/** Gets links contained in selected element **/
	public List<Link> getLinks(String cssSelector);

}
