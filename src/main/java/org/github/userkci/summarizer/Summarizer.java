package org.github.userkci.summarizer;

import java.util.ArrayList;
import java.util.List;

import org.github.userkci.summarizer.analysis.AnalyzerModule;
import org.github.userkci.summarizer.analysis.ArticleSummary;
import org.github.userkci.summarizer.analysis.ArticleSummaryException;
import org.github.userkci.summarizer.analysis.ArticleSummmarizer;
import org.github.userkci.summarizer.reader.Article;
import org.github.userkci.summarizer.reader.ArticleReader;
import org.github.userkci.summarizer.reader.techcrunch.TechCrunchModule;
import org.github.userkci.summarizer.webclient.jsoup.WebModule;
import org.github.userkci.summarizer.writer.ArticleSummaryWriter;
import org.github.userkci.summarizer.writer.FileModule;
import org.github.userkci.summarizer.writer.StdoutModule;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

public class Summarizer {
	
	private final ArticleReader source;
	private final ArticleSummmarizer analyzer;
	private final ArticleSummaryWriter output;
	
	@Inject
	public Summarizer(ArticleReader source, ArticleSummmarizer analyzer, ArticleSummaryWriter output) {
		this.source= source;
		this.analyzer= analyzer;
		this.output= output;
	}
	
	public void run() throws Exception {
		for (Article article : source.discoverArticles()) {
			try {
				ArticleSummary summary= analyzer.summarize(article);
				output.write(summary);
			}
			catch (ArticleSummaryException e) {
				// Errors in analysis should not fail;
				// Try to continue
			}
		}
		output.close();
	}
	
	public static void main(String[] argv) throws Exception {
		List<Module> modules= new ArrayList<>();
		modules.add(new WebModule());
		modules.add(new TechCrunchModule());
		modules.add(new AnalyzerModule());
		
		if (argv.length > 0) {
			modules.add(new FileModule(argv[0]));
		}
		else {
			modules.add(new StdoutModule());
		}
		
		Injector injector= Guice.createInjector(modules);
		Summarizer scanner= injector.getInstance(Summarizer.class);
		scanner.run();
	}

}
