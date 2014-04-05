package org.github.userkci.summarizer.reader;



public interface ArticleReader {

	Iterable<Article> discoverArticles() throws ArticleDiscoveryException;

}
