package com.learn.newsfeed.search.solr;
import com.learn.newsfeed.config.SolrConfigDto;
import com.learn.newsfeed.exception.SearchException;
import com.learn.newsfeed.logging.EventLog;
import com.learn.newsfeed.model.ResultDocument;
import com.learn.newsfeed.model.SearchRequest;
import com.learn.newsfeed.model.SearchResponse;
import com.learn.newsfeed.search.IndexSearcher;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.MapSolrParams;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolrIndexSearcher implements IndexSearcher {
    private final String MODULE = SolrIndexSearcher.class.getCanonicalName();
    private final SolrClient solrClient;
    private final SolrConfigDto solrConfigDto;
    public SolrIndexSearcher(SolrClient solrClient, SolrConfigDto solrConfigDto) {
        this.solrClient = solrClient;
        this.solrConfigDto = solrConfigDto;
    }

    @Override
    public SearchResponse search(SearchRequest searchRequest) {
        try{
            final Map<String, String> queryParamMap = new HashMap<>();
            queryParamMap.put("q",searchRequest.getUserSearchQuery());
            queryParamMap.put("fl", "id,headline,articleLink,summary,publishDate,authors,categories");
            queryParamMap.put("rows", "10");

            MapSolrParams queryParams = new MapSolrParams(queryParamMap);

            final QueryResponse response = solrClient.query(solrConfigDto.getCollection(), queryParams);

            EventLog.module(MODULE)
                    .message(String.format("search response count=%s, status=%s",response.getResults().size(),response.getStatus()))
                    .info()
                    .log();

            List<ResultDocument> docs =  response.getResults().stream()
                    .map(this::mapResultDocument)
                    .toList();

            return new SearchResponse(docs.size(),docs);
        }
        catch (SolrServerException | IOException ex )
        {
            EventLog.module(MODULE)
                    .message(String.format("Error during Solr search: %s ",ex.getMessage()))
                    .error()
                    .log();
            throw new SearchException("Failed to search Solr", ex);        }
        catch (Exception ex)
        {
            EventLog.module(MODULE)
                    .message(String.format("Encountered an error while attempting to search: %s ",ex.getMessage()))
                    .error()
                    .log();
            throw new SearchException("Unexpected error while searching Solr", ex);        }
    }

    private ResultDocument mapResultDocument(SolrDocument doc) {
        if (doc == null) return null;

        return ResultDocument.builder()
                .id((String) doc.getFieldValue("id"))
                .headline((String) doc.getFieldValue("headline"))
                .articleLink((String) doc.getFieldValue("articleLink"))
                .summary((String) doc.getFieldValue("summary"))
                .publishDate((Date) doc.getFieldValue("publishDate"))
                .authors((List<String>) doc.getFieldValue("authors"))
                .categories((List<String>) doc.getFieldValue("categories"))
                .build();
    }
}
