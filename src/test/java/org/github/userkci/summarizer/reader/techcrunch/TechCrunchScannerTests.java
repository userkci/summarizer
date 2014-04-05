package org.github.userkci.summarizer.reader.techcrunch;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;

import org.github.userkci.summarizer.webclient.Link;
import org.github.userkci.summarizer.webclient.WebClient;
import org.github.userkci.summarizer.webclient.WebPage;
import org.junit.Test;
import org.mockito.Mockito;

public class TechCrunchScannerTests {
	
	private static Link LOCAL_LINK1= new Link("http://techcrunch.com/2014/04/01/something/", "something");
	private static Link DUPLICATE_LOCAL_LINK1= new Link("http://techcrunch.com/2014/04/01/something/", "read more");
	private static Link LOCAL_LINK2= new Link("http://techcrunch.com/2014/04/02/something/#comments", "comments");
	private static Link EXTERNAL_LINK= new Link("http://www.facebook.com/techcrunch", "");

	@Test
	public void foldDuplicateUrls() throws Exception {
		WebClient client= mock(WebClient.class);
		WebPage page= mock(WebPage.class);
		when(page.getLinks("")).thenReturn(Arrays.asList(LOCAL_LINK1, DUPLICATE_LOCAL_LINK1, LOCAL_LINK2));
		when(client.getPage(Mockito.anyString())).thenReturn(page);
		TechCrunchScanner scanner= new TechCrunchScanner(client);
		Collection<String> articles= scanner.start("http://techcrunch.com");
		
		Assert.assertEquals("Wrong number of articles", 2, articles.size());
		Assert.assertTrue("Wrong number of articles", articles.contains(LOCAL_LINK1.getUrl()));
		Assert.assertTrue("Wrong number of articles", articles.contains(LOCAL_LINK2.getUrl()));
	}
	
	@Test
	public void excludeExternalLinks() throws Exception {
		WebClient client= mock(WebClient.class);
		WebPage page= mock(WebPage.class);
		when(page.getLinks("")).thenReturn(Arrays.asList(LOCAL_LINK1, EXTERNAL_LINK, LOCAL_LINK2));
		when(client.getPage(Mockito.anyString())).thenReturn(page);
		TechCrunchScanner scanner= new TechCrunchScanner(client);
		Collection<String> articles= scanner.start("http://techcrunch.com");
		
		Assert.assertEquals("Wrong number of articles", 2, articles.size());
		Assert.assertTrue("Wrong number of articles", articles.contains(LOCAL_LINK1.getUrl()));
		Assert.assertTrue("Wrong number of articles", articles.contains(LOCAL_LINK2.getUrl()));
	}
}
