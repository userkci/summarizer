package org.github.userkci.summarizer.writer;

import java.io.IOException;

import org.github.userkci.summarizer.analysis.ArticleSummary;

public interface ArticleSummaryWriter {

	public abstract void write(ArticleSummary article) throws IOException;

	public abstract void close();

}