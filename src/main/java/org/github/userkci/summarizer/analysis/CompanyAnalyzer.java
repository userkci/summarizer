package org.github.userkci.summarizer.analysis;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.github.userkci.summarizer.webclient.Link;

class CompanyAnalyzer {
	
	private static final Set<String> ignoredWords= new HashSet<>(Arrays.asList("A","I","You","We","Your","My","The","For","Of","By","And","But","Or","An","In","To","Not"));
	
	
	private static boolean isPossibleName(String s) {
		for (int i= 0; i < s.length(); i++) {
			if (Character.isUpperCase(s.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	Set<String> findCompanyCandidates(String text) {
		// Find words with capital letters, as these are likely proper nouns.
		Set<String> candidates= new HashSet<>();
		String[] words= text.split("\\s+");
		for (String word : words) {
			if (isPossibleName(word) && !ignoredWords.contains(word)) {
				candidates.add(word);
			}
		}
		return candidates;
	}
	
	Set<String> filterCompanyCandidatesByTitle(Collection<String> candidates, String title) {
		Set<String> filteredCandidates= new HashSet<>();
		// Remove anything not found in title.
		for (String candidate : candidates) {
			if (title.contains(candidate)) {
				filteredCandidates.add(candidate);
			}
		}
		return filteredCandidates;
	}
	
	Set<String> filterCompanyCandidatesByLinks(Collection<String> candidates, Collection<Link> links) {
		Set<String> filteredCandidates= new HashSet<>(candidates);
		// Only keep candidates which appear as hyperlink
		Set<String> linkText= new HashSet<>();
		for (Link l : links) {
			linkText.add(l.getText());
		}
		
		filteredCandidates.retainAll(linkText);
		return filteredCandidates;
	}
	
	private static <T> T getSingletonElement(Collection<T> values) {
		if (values.size() == 1) {
			for (T v : values) {
				return v;
			}
		}
		return null;
	}
	
	public String findCompany(String title, String text, Collection<Link> links) {
		Set<String> candidates= findCompanyCandidates(text);
		
		String bestCandidate= getSingletonElement(candidates);
		
		if (bestCandidate == null) {
			Set<String> candidatesByTitle= filterCompanyCandidatesByTitle(candidates, title);
			Set<String> candidatesByLink= filterCompanyCandidatesByLinks(candidates, links);
			
			Set<String> intersect= new HashSet<>(candidatesByLink);
			intersect.retainAll(candidatesByTitle);
			
			if (intersect.size() == 1) {
				bestCandidate= getSingletonElement(intersect);
			}
			else if (candidatesByLink.size() == 1) {
				bestCandidate= getSingletonElement(candidatesByLink);
			}
			else if (candidatesByTitle.size() == 1) {
				bestCandidate= getSingletonElement(candidatesByTitle);
			}
			
		}
		return bestCandidate;
	}
	
	public String findCompanyWebsite(String company, Collection<Link> links) {
		for (Link l : links) {
			String text= l.getText();
			if (text.equals(company)) {
				return l.getUrl();
			}
		}
		return null;
	}

}
