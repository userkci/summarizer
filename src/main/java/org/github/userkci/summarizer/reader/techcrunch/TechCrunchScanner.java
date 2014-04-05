package org.github.userkci.summarizer.reader.techcrunch;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.github.userkci.summarizer.webclient.Link;
import org.github.userkci.summarizer.webclient.WebClient;
import org.github.userkci.summarizer.webclient.WebPage;

import com.google.inject.Inject;

class TechCrunchScanner {
	
	private final WebClient client;
	
	@Inject
	TechCrunchScanner(WebClient client) {
		this.client= client;
	}
	
	private boolean isLocal(String baseUrl, Link link) {
		return link.getUrl().startsWith(baseUrl);
	}

//	public Collection<String> start(String baseUrl) throws IOException {
//		Queue<String> toVisit= new LinkedList<>();
//		toVisit.add(baseUrl);
//		Set<String> urls= new LinkedHashSet<>();
//		while (!toVisit.isEmpty()) {
//			String url= toVisit.remove();
//			try {
//				System.out.println("Visiting " + url);
//			WebPage page= client.getPage(url);
//			for (Link link : page.getLinks("")) {
//				if (isLocal(baseUrl, link)) {
//					if (urls.add(link.getUrl())) {
//						toVisit.add(link.getUrl());
//					}
//				}
//			}
//			}
//			catch (IOException e) {
//				// Log and continue
//			}
//		}
//		return urls;
//	}

	public Collection<String> start(String baseUrl) throws IOException {
		// For our purposes here, we only scrape the starting page.
		// I have no idea how long it would take to go through all pages.
		WebPage frontPage= client.getPage(baseUrl);
		Set<String> urls= new LinkedHashSet<>();
		for (Link link : frontPage.getLinks("")) {
			if (isLocal(baseUrl, link)) {
				urls.add(link.getUrl());
			}
		}
		return urls;
	}

}
