package com.learn.newsfeed.model;

import java.util.List;

public class SearchResponse {

    private int totalResults;
    private List<ResultDocument> documents;

    public SearchResponse(int totalResults, List<ResultDocument> documents) {
        this.totalResults = totalResults;
        this.documents = documents;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<ResultDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<ResultDocument> documents) {
        this.documents = documents;
    }
}
