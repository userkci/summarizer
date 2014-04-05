package org.github.userkci.summarizer.writer;

import org.github.userkci.summarizer.analysis.ArticleSummary;

public class CSVFormatter implements ArticleFormatter {

	private static final String NULL_INDICATOR = "n/a";
	
	private String handleNull(String value) {
		return value != null ? value : NULL_INDICATOR;
	}
	
	public String format(ArticleSummary article) {
		StringBuilder buffer= new StringBuilder();
		buffer.append(article.getTitle());
		buffer.append(',');
		buffer.append(article.getUrl());
		buffer.append(',');
		buffer.append(handleNull(article.getCompany()));
		buffer.append(',');
		buffer.append(handleNull(article.getCompanyURL()));
		return buffer.toString();
	}

}
