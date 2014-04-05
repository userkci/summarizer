package org.github.userkci.summarizer.reader.techcrunch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.userkci.summarizer.reader.Article;
import org.github.userkci.summarizer.reader.ArticleDiscoveryException;
import org.github.userkci.summarizer.reader.ArticleReader;

import com.google.inject.Inject;

public class TechCrunchSource implements ArticleReader {
	
	private final TechCrunchParser parser;
	private final TechCrunchScanner crawler;
	private final Matcher articleMatcher;
	
	@Inject
	TechCrunchSource(TechCrunchScanner crawler, TechCrunchParser parser) {
		this.crawler= crawler;
		this.parser= parser;

		// Articles URLs appear to match this pattern
		Pattern pattern= Pattern.compile("http://techcrunch\\.com/\\d{4}/\\d{2}/\\d{2}.*/");
		articleMatcher= pattern.matcher("");
	}
	
	private Iterable<String> generateLinks() throws ArticleDiscoveryException {
		try {
			Collection<String> links= crawler.start("http://techcrunch.com");
			return links;
		}
		catch (IOException e) {
			throw new ArticleDiscoveryException(e);
		}
	}

	private boolean isArticleLink(String url) {
		articleMatcher.reset(url);
		return articleMatcher.matches();
	}
	
	@Override
	public Iterable<Article> discoverArticles() throws ArticleDiscoveryException
	{
		Iterable<String> links= generateLinks();
		
		// In practice, it might be better to stream instead of collecting all
		// articles in memory.
		List<Article> articles= new ArrayList<>();
		for (String url : links) {
			// Try to prefilter out non-articles
			if (isArticleLink(url)) {
				try {
					Article article= parser.readArticle(url);
					articles.add(article);
				}
				catch (Exception e) {
					// Log error and try to continue; next article may be fine
				}
			}
		}
		return articles;
	}

}
