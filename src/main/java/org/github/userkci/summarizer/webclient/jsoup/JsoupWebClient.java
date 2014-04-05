package org.github.userkci.summarizer.webclient.jsoup;

import java.io.IOException;

import org.github.userkci.summarizer.webclient.WebClient;
import org.github.userkci.summarizer.webclient.WebPage;
import org.jsoup.Jsoup;

public class JsoupWebClient implements WebClient {
	
	@Override
	public WebPage getPage(String url) throws IOException {
		return new JsoupWebPageWrapper(Jsoup.connect(url).get());
	}

}
