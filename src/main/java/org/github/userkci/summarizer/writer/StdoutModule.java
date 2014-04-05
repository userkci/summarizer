package org.github.userkci.summarizer.writer;

import java.io.OutputStream;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

public class StdoutModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ArticleFormatter.class).to(CSVFormatter.class);
		bind(ArticleSummaryWriter.class).to(ArticleSummaryPrinter.class);
	}
	
	@Provides @Named("outputTarget")
	OutputStream getOutputStream() {
		return System.out;
	}

}
