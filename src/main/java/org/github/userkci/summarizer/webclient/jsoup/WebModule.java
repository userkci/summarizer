package org.github.userkci.summarizer.webclient.jsoup;

import org.github.userkci.summarizer.webclient.WebClient;

import com.google.inject.AbstractModule;

public class WebModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(WebClient.class).to(JsoupWebClient.class);
	}

}
