package org.github.userkci.summarizer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.github.userkci.summarizer.Summarizer;
import org.github.userkci.summarizer.analysis.ArticleSummary;
import org.github.userkci.summarizer.analysis.ArticleSummaryException;
import org.github.userkci.summarizer.analysis.ArticleSummmarizer;
import org.github.userkci.summarizer.reader.Article;
import org.github.userkci.summarizer.reader.ArticleReader;
import org.github.userkci.summarizer.webclient.Link;
import org.github.userkci.summarizer.writer.ArticleSummaryPrinter;
import org.junit.Test;

public class SummarizerTests {

	private static final Article TEST_ARTICLE= new Article("url", "title", "body", Collections.<Link>emptyList());
	private static final Article BAD_ARTICLE= new Article("url2", "title2", "body2", Collections.<Link>emptyList());
	private static final ArticleSummary TEST_SUMMARY= new ArticleSummary("url", "title");
	
	@Test
	public void normalOperation() throws Exception
	{
		ArticleReader source= mock(ArticleReader.class);
		when(source.discoverArticles()).thenReturn(Arrays.asList(TEST_ARTICLE, TEST_ARTICLE, TEST_ARTICLE));
		ArticleSummmarizer parser= mock(ArticleSummmarizer.class);
		when(parser.summarize(any(Article.class))).thenReturn(TEST_SUMMARY);
		ArticleSummaryPrinter output= mock(ArticleSummaryPrinter.class);
		Summarizer scanner= new Summarizer(source, parser, output);
		scanner.run();
		
		verify(source).discoverArticles();
		verify(parser, times(3)).summarize(TEST_ARTICLE);
		verify(output, times(3)).write(TEST_SUMMARY);
		verify(output).close();
	}	
	
	@Test
	public void analysisErrorSkipped() throws Exception
	{
		ArticleReader source= mock(ArticleReader.class);
		when(source.discoverArticles()).thenReturn(Arrays.asList(TEST_ARTICLE, BAD_ARTICLE, TEST_ARTICLE));
		ArticleSummmarizer parser= mock(ArticleSummmarizer.class);
		when(parser.summarize(TEST_ARTICLE)).thenReturn(TEST_SUMMARY);
		when(parser.summarize(BAD_ARTICLE)).thenThrow(new ArticleSummaryException());
		ArticleSummaryPrinter output= mock(ArticleSummaryPrinter.class);
		Summarizer scanner= new Summarizer(source, parser, output);
		
		scanner.run();
		
		// Should still invoke on third article
		verify(parser, times(2)).summarize(TEST_ARTICLE);
		verify(output, times(2)).write(TEST_SUMMARY);
		verify(output).close();
	}

}
