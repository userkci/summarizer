package org.github.userkci.summarizer.analysis;

import com.google.inject.AbstractModule;

public class AnalyzerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ArticleSummmarizer.class).to(SummaryGenerator.class);
	}

}
