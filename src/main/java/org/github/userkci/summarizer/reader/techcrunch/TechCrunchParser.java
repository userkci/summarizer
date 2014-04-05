package org.github.userkci.summarizer.reader.techcrunch;

import java.io.IOException;
import java.util.Collection;

import org.github.userkci.summarizer.reader.Article;
import org.github.userkci.summarizer.webclient.Link;
import org.github.userkci.summarizer.webclient.WebClient;
import org.github.userkci.summarizer.webclient.WebPage;

import com.google.inject.Inject;

class TechCrunchParser  {
	
	private final WebClient client;

	@Inject
	TechCrunchParser(WebClient webClient) {
		client= webClient;
	}

	public Article readArticle(String url) throws IOException {
			WebPage page= client.getPage(url);
			// Title has trailing text we don't want
			String title= page.getElementText("title");
			title= title.replaceFirst("\\s+\\|\\s+TechCrunch$", "");

			// It appears that the main text appears inside 
			String body= page.getElementText("div.article-entry");
			Collection<Link> links= page.getLinks("div.article-entry");
			
			return new Article(url, title, body, links);
	}

}
