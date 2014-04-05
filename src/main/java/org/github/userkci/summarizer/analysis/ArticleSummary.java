package org.github.userkci.summarizer.analysis;

public class ArticleSummary {

	private final String url;
	private final String title;
	private String company;
	private String companyURL;
	
	public ArticleSummary(String url, String title) {
		this.url= url;
		this.title= title;
	}
	
	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public void setCompanyURL(String companyURL) {
		this.companyURL = companyURL;
	}
}
