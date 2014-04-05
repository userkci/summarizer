package org.github.userkci.summarizer.analysis;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;

import junit.framework.Assert;

import org.github.userkci.summarizer.reader.Article;
import org.github.userkci.summarizer.webclient.Link;
import org.junit.Test;

public class SummaryGeneratorTests {
	
	@Test
	public void summarize() throws Exception {
		Article article= new Article("url", "title", "text", Collections.<Link>emptyList());
		CompanyAnalyzer analyzer= mock(CompanyAnalyzer.class);
		when(analyzer.findCompany(anyString(), anyString(), any(Collection.class))).thenReturn("company");
		when(analyzer.findCompanyWebsite(anyString(), any(Collection.class))).thenReturn("companyUrl");
		SummaryGenerator summarizer= new SummaryGenerator(analyzer);
		ArticleSummary summary= summarizer.summarize(article);

		Assert.assertEquals("Wrong URL", "url", summary.getUrl());
		Assert.assertEquals("Wrong title", "title", summary.getTitle());
		Assert.assertEquals("Wrong text", "company", summary.getCompany());
		Assert.assertEquals("Wrong links", "companyUrl", summary.getCompanyURL());
	}
}
