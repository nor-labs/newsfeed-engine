package com.learn.newsfeed.service;

import com.learn.newsfeed.exception.SearchException;
import com.learn.newsfeed.logging.EventLog;
import com.learn.newsfeed.model.ResultDocument;
import com.learn.newsfeed.model.SearchRequest;
import com.learn.newsfeed.model.SearchResponse;
import com.learn.newsfeed.search.IndexSearcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    private final String MODULE = SearchService.class.getCanonicalName();
    private final IndexSearcher indexSearcher;

    public SearchService(IndexSearcher indexSearcher) {
        this.indexSearcher = indexSearcher;
    }

    public SearchResponse search(SearchRequest searchRequest) {
        try {
            return indexSearcher.search(searchRequest);
        } catch (SearchException ex) {
            System.err.println("Search failed: " + ex.getMessage());
            EventLog.module(MODULE)
                    .message(String.format("Search failed: %s ",ex.getMessage()))
                    .error()
                    .log();
            throw ex;
        } catch (Exception ex) {
            throw new SearchException("Unexpected error during search", ex);
        }
    }
}
