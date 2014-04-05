package org.github.userkci.summarizer.reader.techcrunch;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;

import junit.framework.Assert;

import org.github.userkci.summarizer.reader.ArticleDiscoveryException;
import org.junit.Test;
import org.mockito.Mockito;

public class TechCrunchSourceTests {
	
	private static String ARTICLE_URL= "http://techcrunch.com/2014/04/01/something/";
	
	private static void assertIterableCount(int expected, Iterable<?> result) {
		int actual= 0;
		for (@SuppressWarnings("unused") Object o : result) {
			actual++;
		}
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void standardInteraction() throws Exception {
		TechCrunchScanner crawler= mock(TechCrunchScanner.class);
		when(crawler.start(anyString())).thenReturn(Arrays.asList(ARTICLE_URL, ARTICLE_URL, ARTICLE_URL));
		TechCrunchParser parser= mock(TechCrunchParser.class);
		TechCrunchSource source= new TechCrunchSource(crawler, parser);

		assertIterableCount(3, source.discoverArticles());
		verify(parser, times(3)).readArticle(ARTICLE_URL);
	}
	
	@Test
	public void excludeFragmentPattern() throws Exception {
		final String IGNORE_URL= ARTICLE_URL + "#comment";
		TechCrunchScanner crawler= mock(TechCrunchScanner.class);
		when(crawler.start(anyString())).thenReturn(Arrays.asList(ARTICLE_URL, IGNORE_URL, ARTICLE_URL));
		TechCrunchParser parser= mock(TechCrunchParser.class);
		TechCrunchSource source= new TechCrunchSource(crawler, parser);

		// Should parse article URLs, skip others
		assertIterableCount(2, source.discoverArticles());
		verify(parser, never()).readArticle(IGNORE_URL);
		verify(parser, times(2)).readArticle(ARTICLE_URL);
	}
	
	@Test
	public void continueOnParseError() throws Exception {
		final String BAD_URL= "boom";
		TechCrunchScanner crawler= mock(TechCrunchScanner.class);
		when(crawler.start(Mockito.anyString())).thenReturn(Arrays.asList(ARTICLE_URL, BAD_URL, ARTICLE_URL, ARTICLE_URL));
		TechCrunchParser parser= mock(TechCrunchParser.class);
		when(parser.readArticle(BAD_URL)).thenThrow(new NullPointerException("Mock Error"));
		TechCrunchSource source= new TechCrunchSource(crawler, parser);
		
		// Should get result for each good URL
		assertIterableCount(3, source.discoverArticles());
		verify(parser, times(3)).readArticle(ARTICLE_URL);
	}
	
	@Test(expected=ArticleDiscoveryException.class)
	public void failOnStartOfCrawl() throws Exception {
		TechCrunchScanner crawler= mock(TechCrunchScanner.class);
		when(crawler.start(anyString())).thenThrow(new IOException("Mock Error"));
		TechCrunchParser parser= mock(TechCrunchParser.class);
		TechCrunchSource source= new TechCrunchSource(crawler, parser);
		// Should catch and wrap exception
		source.discoverArticles();
	}
}
