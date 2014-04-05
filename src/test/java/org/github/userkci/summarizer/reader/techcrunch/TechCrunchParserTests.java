package org.github.userkci.summarizer.reader.techcrunch;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.github.userkci.summarizer.reader.Article;
import org.github.userkci.summarizer.webclient.Link;
import org.github.userkci.summarizer.webclient.WebClient;
import org.github.userkci.summarizer.webclient.WebPage;
import org.junit.Test;

public class TechCrunchParserTests {
	
	// Check we properly link results of queries to article
	
	private static final List<Link> TEST_LINKS= Arrays.asList(new Link("a","b"), new Link("c", "d"));
	
	@Test
	public void parseArticle() throws Exception {
		WebClient client= mock(WebClient.class);
		WebPage page= mock(WebPage.class);
		when(page.getElementText("title")).thenReturn("test title");
		when(page.getElementText("div.article-entry")).thenReturn("test body");
		when(page.getLinks("div.article-entry")).thenReturn(TEST_LINKS);
		when(client.getPage("xyz")).thenReturn(page);
		TechCrunchParser parser= new TechCrunchParser(client);
		Article article= parser.readArticle("xyz");

		Assert.assertEquals("Wrong URL", "xyz", article.getUrl());
		Assert.assertEquals("Wrong title", "test title", article.getTitle());
		Assert.assertEquals("Wrong text", "test body", article.getText());
		Assert.assertEquals("Wrong links", TEST_LINKS, article.getLinks());
	}
	
	@Test
	public void stripTitle() throws Exception {
		WebClient client= mock(WebClient.class);
		WebPage page= mock(WebPage.class);
		when(page.getElementText("title")).thenReturn("test title | TechCrunch");
		when(page.getElementText("div.article-entry")).thenReturn("test body");
		when(page.getLinks("div.article-entry")).thenReturn(TEST_LINKS);
		when(client.getPage("xyz")).thenReturn(page);
		TechCrunchParser parser= new TechCrunchParser(client);
		Article article= parser.readArticle("xyz");

		// Should remove trailing TechCrunch label
		Assert.assertEquals("Wrong title", "test title", article.getTitle());
	}
}
