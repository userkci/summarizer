package org.github.userkci.summarizer.reader.techcrunch;

import org.github.userkci.summarizer.reader.ArticleReader;

import com.google.inject.AbstractModule;

public class TechCrunchModule extends AbstractModule {

	@Override
	protected void configure() {
		
		bind(ArticleReader.class).to(TechCrunchSource.class);
		
	}

}
