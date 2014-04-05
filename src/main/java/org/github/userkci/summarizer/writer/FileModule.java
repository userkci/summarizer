package org.github.userkci.summarizer.writer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

public class FileModule extends AbstractModule {
	
	private final String file;
	
	public FileModule(String file) {
		this.file= file;
	}

	@Override
	protected void configure() {
		bind(ArticleFormatter.class).to(CSVFormatter.class);
		bind(ArticleSummaryWriter.class).to(ArticleSummaryPrinter.class);
	}
	
	@Provides @Named("outputTarget")
	OutputStream getOutputStream() throws FileNotFoundException {
		return new FileOutputStream(file);
	}

}
