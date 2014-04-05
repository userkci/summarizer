package org.github.userkci.summarizer.writer;

import org.github.userkci.summarizer.analysis.ArticleSummary;
import org.github.userkci.summarizer.writer.CSVFormatter;
import org.junit.Assert;
import org.junit.Test;

public class CSVFormatterTests {
	
	@Test
	public void fullData() {
		ArticleSummary article= new ArticleSummary("article url", "article title");
		article.setCompany("company name");
		article.setCompanyURL("company website");
		
		CSVFormatter formatter= new CSVFormatter();
		String result= formatter.format(article);
		String expected= "company name,company website,article title,article url";
		Assert.assertEquals("Wrong value for missing company name", expected, result);
	}
	
	@Test
	public void noCompanyName() {
		ArticleSummary article= new ArticleSummary("article url", "article title");
		// Don't provide company name; NULL implies unknown
		article.setCompanyURL("company website");
		
		CSVFormatter formatter= new CSVFormatter();
		String result= formatter.format(article);
		String expected= "n/a,company website,article title,article url";
		Assert.assertEquals("Wrong value for missing company name", expected, result);
	}
	
	@Test
	public void noCompanyWebsite() {
		ArticleSummary article= new ArticleSummary("article url", "article title");
		article.setCompany("company name");
		// Don't provide company website; NULL implies unknown
		
		CSVFormatter formatter= new CSVFormatter();
		String result= formatter.format(article);
		String expected= "company name,n/a,article title,article url";
		Assert.assertEquals("Wrong value for missing company website", expected, result);
	}

}
