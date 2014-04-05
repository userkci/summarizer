package org.github.userkci.summarizer.analysis;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.github.userkci.summarizer.webclient.Link;
import org.junit.Test;

public class CompanyAnalyzerTests {
	
	@Test
	public void candidateGeneration() {
		CompanyAnalyzer analyzer= new CompanyAnalyzer();
		Set<String> actual= analyzer.findCompanyCandidates("Microsoft soup eBay toaster Today Twitter.");
		
		Set<String> expected= new HashSet<String>(Arrays.asList("Microsoft", "eBay", "Today", "Twitter."));
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void candidateGenerationExclusions() {
		CompanyAnalyzer analyzer= new CompanyAnalyzer();
		Set<String> actual= analyzer.findCompanyCandidates("I  Microsoft You soup A eBay Of toaster Not Today Twitter.");
		
		Set<String> expected= new HashSet<String>(Arrays.asList("Microsoft", "eBay", "Today", "Twitter."));
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void titleFiltering() {
		CompanyAnalyzer analyzer= new CompanyAnalyzer();
		Set<String> candidates= new HashSet<String>(Arrays.asList("Microsoft", "Today", "eBay", "Twitter."));
		Set<String> actual= analyzer.filterCompanyCandidatesByTitle(candidates, "Microsoft To Sell Toasters On eBay");
		
		Set<String> expected= new HashSet<String>(Arrays.asList("Microsoft", "eBay"));
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void linkFiltering() {
		CompanyAnalyzer analyzer= new CompanyAnalyzer();
		Set<String> candidates= new HashSet<String>(Arrays.asList("Microsoft", "Today", "eBay", "Twitter."));
		List<Link> links= Arrays.asList(new Link("xxx", "Microsoft"), new Link("yyy", "eBay"));
		Set<String> actual= analyzer.filterCompanyCandidatesByLinks(candidates, links);
		
		Set<String> expected= new HashSet<String>(Arrays.asList("Microsoft", "eBay"));
		Assert.assertEquals(expected, actual);
		
	}
	
	// only one candidate generated
	@Test
	public void singleCandidateGenerated() {
		CompanyAnalyzer analyzer= new CompanyAnalyzer();
		String actual= analyzer.findCompany("Happy Days Ahead!", "Microsoft releases a new toaster today.",  Collections.<Link>emptyList());
		Assert.assertEquals("Microsoft", actual);
	}
	
	// no candidates generated
	@Test
	public void noCandidateGenerated() {
		CompanyAnalyzer analyzer= new CompanyAnalyzer();
		String actual= analyzer.findCompany("Happy Days Ahead!", "A new toaster is available today from an unexpected source.", Collections.<Link>emptyList());
		Assert.assertNull("Unexpected candidate found", actual);
	}
	
	// title produces single candidate
	@Test
	public void titleAndLinksFindCompany() {
		CompanyAnalyzer analyzer= new CompanyAnalyzer();
		String actual= analyzer.findCompany("Microsoft To Sell Toasters On eBay", "Microsoft toaster is on eBay starting today!", 
				Arrays.asList(new Link("xxx","Microsoft"), new Link("yyy", "toaster")));
		Assert.assertEquals("Unexpected candidate found", "Microsoft", actual);
	}
	
	@Test
	public void titleFindsCompany() {
		CompanyAnalyzer analyzer= new CompanyAnalyzer();
		// No intersection in title and links, but title has a single match
		String actual= analyzer.findCompany("Microsoft To Sell Toaster On eBay", "Today, Microsoft released a new toaster.", Collections.<Link>emptyList());
		Assert.assertEquals("Unexpected candidate found", "Microsoft", actual);
	}
	
	@Test
	public void linkFindsCompany() {
		CompanyAnalyzer analyzer= new CompanyAnalyzer();
		// No intersection in title and links, but both have a single match
		String actual= analyzer.findCompany("eBay Selling New Toaster", "Microsoft toaster is on eBay starting today!",
				Arrays.asList(new Link("xxx","Microsoft"), new Link("yyy", "toaster")));
		Assert.assertEquals("Unexpected candidate found", "Microsoft", actual);
	}
	
	// no clear candidate
	@Test
	public void ambiguousCompany() {
		CompanyAnalyzer analyzer= new CompanyAnalyzer();
		String actual= analyzer.findCompany("Microsoft To Sell Toaster On eBay", "Microsoft toaster is on eBay starting today!",
				Arrays.asList(new Link("xxx","Microsoft"), new Link("yyy", "eBay")));
		Assert.assertNull("Unexpected candidate found", actual);
	}
	
	// company website
	
	@Test
	public void website() {
		CompanyAnalyzer analyzer= new CompanyAnalyzer();
		String actual= analyzer.findCompanyWebsite("Microsoft",
				Arrays.asList(new Link("xxx","Microsoft"), new Link("yyy", "Microsoft on eBay")));
		Assert.assertEquals("Unexpected website found", "xxx", actual);
	}
	
	@Test
	public void noWebsite() {
		CompanyAnalyzer analyzer= new CompanyAnalyzer();
		String actual= analyzer.findCompanyWebsite("Microsoft",
				Arrays.asList(new Link("xxx","eBay"), new Link("yyy", "Microsoft on eBay")));
		Assert.assertNull("Unexpected website found", actual);
	}
}
