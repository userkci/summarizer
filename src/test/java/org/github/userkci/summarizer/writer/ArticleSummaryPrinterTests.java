package org.github.userkci.summarizer.writer;

import java.io.ByteArrayOutputStream;

import org.github.userkci.summarizer.analysis.ArticleSummary;
import org.github.userkci.summarizer.writer.ArticleSummaryPrinter;
import org.github.userkci.summarizer.writer.CSVFormatter;
import org.junit.Assert;
import org.junit.Test;

public class ArticleSummaryPrinterTests {
	
	private static final String NEWLINE = System.getProperty("line.separator").toString();
	
	@Test
	public void outputAllArticles() throws Exception {
		ByteArrayOutputStream buffer= new ByteArrayOutputStream();
		ArticleFormatter formatter= new CSVFormatter();
		ArticleSummaryPrinter printer= new ArticleSummaryPrinter(buffer, formatter);
		ArticleSummary article= new ArticleSummary("a", "b");
		article.setCompany("X");
		article.setCompanyURL("Y");
		
		final String expectedFormat= formatter.format(article);
		
		printer.write(article);
		printer.write(article);
		printer.write(article);
		
		// Wrote three times, so expect three lines - all the same, since same object
		String expected= expectedFormat + NEWLINE + expectedFormat + NEWLINE + expectedFormat + NEWLINE;
		Assert.assertEquals("Wrong output from writer", expected, new String(buffer.toByteArray()));
	}
	
	
}
