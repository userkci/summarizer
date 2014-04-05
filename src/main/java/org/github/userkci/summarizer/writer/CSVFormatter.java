package org.github.userkci.summarizer.writer;

import org.github.userkci.summarizer.analysis.ArticleSummary;

public class CSVFormatter implements ArticleFormatter {

	private static final String NULL_INDICATOR = "n/a";
	
	private String handleNull(String value) {
		return value != null ? value : NULL_INDICATOR;
	}
	
	public String format(ArticleSummary article) {
		StringBuilder buffer= new StringBuilder();
		buffer.append(handleNull(article.getCompany()));
		buffer.append(',');
		buffer.append(handleNull(article.getCompanyURL()));
		buffer.append(',');
		buffer.append(article.getTitle());
		buffer.append(',');
		buffer.append(article.getUrl());
		return buffer.toString();
	}

}
