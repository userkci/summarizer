package org.github.userkci.summarizer.webclient.jsoup;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.github.userkci.summarizer.webclient.Link;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;



public class JsoupWebPageWrapperTests {
	
	@Test
	public void elementText() throws Exception {
		Document document= mock(Document.class);
		Elements selected= mock(Elements.class);
		Element body= mock(Element.class);
		when(body.text()).thenReturn("Sample text");
		when(selected.isEmpty()).thenReturn(false);
		when(selected.get(0)).thenReturn(body);
		when(document.select("body")).thenReturn(selected);
		JsoupWebPageWrapper page= new JsoupWebPageWrapper(document);
		Assert.assertEquals("Sample text", page.getElementText("body"));
	}
	
	@Test
	public void noElementsText() throws Exception {
		Document document= mock(Document.class);
		Elements selected= mock(Elements.class);
		when(selected.isEmpty()).thenReturn(true);
		when(document.select("body")).thenReturn(selected);
		JsoupWebPageWrapper page= new JsoupWebPageWrapper(document);
		Assert.assertEquals("", page.getElementText("body"));
	}
	
	@Test
	public void links() throws Exception {
		Document document= mock(Document.class);
		Elements selected= mock(Elements.class);
		Element link= mock(Element.class);
		when(link.attr("abs:href")).thenReturn("url");
		when(link.text()).thenReturn("text");
		when(selected.isEmpty()).thenReturn(false);
		when(selected.iterator()).thenReturn(Arrays.asList(link).iterator());
		when(document.select("foo a[href]")).thenReturn(selected);
		JsoupWebPageWrapper page= new JsoupWebPageWrapper(document);
		
		List<Link> result = page.getLinks("foo");
		Assert.assertEquals(1, result.size());
		Assert.assertEquals("url", result.get(0).getUrl());
		Assert.assertEquals("text", result.get(0).getText());
	}
}
