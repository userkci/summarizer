package org.github.userkci.summarizer.writer;

import org.github.userkci.summarizer.analysis.ArticleSummary;

public interface ArticleFormatter {

	String format(ArticleSummary article);

}
