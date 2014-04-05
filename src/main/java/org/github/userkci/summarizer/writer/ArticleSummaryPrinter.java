package org.github.userkci.summarizer.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.github.userkci.summarizer.analysis.ArticleSummary;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ArticleSummaryPrinter implements ArticleSummaryWriter {
	
	private PrintStream output;
	private ArticleFormatter formatter;

	@Inject
	public ArticleSummaryPrinter(@Named("outputTarget") OutputStream output, ArticleFormatter formatter) {
		this.output= new PrintStream(output);
		this.formatter= formatter;
	}

	@Override
	public void write(ArticleSummary article) throws IOException {
		output.println(formatter.format(article));
	}

	@Override
	public void close() {
		output.close();
	}

}
