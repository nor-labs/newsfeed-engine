package com.learn.newsfeed.search;

import com.learn.newsfeed.model.SearchRequest;
import com.learn.newsfeed.model.SearchResponse;
public interface IndexSearcher {
    /**
     * Search documents by a query string.
     *
     * @param searchRequest the search request
     * @return list of matching documents
     */
    SearchResponse search(SearchRequest searchRequest);
}
