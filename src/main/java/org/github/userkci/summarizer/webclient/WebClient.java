package org.github.userkci.summarizer.webclient;

import java.io.IOException;

public interface WebClient {

	public WebPage getPage(String url) throws IOException;

}
