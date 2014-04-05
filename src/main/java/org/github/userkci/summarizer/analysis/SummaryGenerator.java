package org.github.userkci.summarizer.analysis;

import org.github.userkci.summarizer.reader.Article;

import com.google.inject.Inject;

class SummaryGenerator implements ArticleSummmarizer {
	
	private final CompanyAnalyzer analyzer;
	
	@Inject
	SummaryGenerator(CompanyAnalyzer analyzer) {
		this.analyzer= analyzer;
	}

	@Override
	public ArticleSummary summarize(Article article) throws ArticleSummaryException {
		ArticleSummary summary= new ArticleSummary(article.getUrl(), article.getTitle());
		String company= analyzer.findCompany(article.getTitle(), article.getText(), article.getLinks());
		String companyUrl= company != null ? analyzer.findCompanyWebsite(company, article.getLinks()) : null;
		summary.setCompany(company);
		summary.setCompanyURL(companyUrl);
		return summary;
	}

}
