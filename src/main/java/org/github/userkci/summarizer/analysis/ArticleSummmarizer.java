package org.github.userkci.summarizer.analysis;

import org.github.userkci.summarizer.reader.Article;


public interface ArticleSummmarizer {

	ArticleSummary summarize(Article article) throws ArticleSummaryException;

}
