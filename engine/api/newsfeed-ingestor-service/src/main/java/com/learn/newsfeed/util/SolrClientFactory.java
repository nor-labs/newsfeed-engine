package com.learn.newsfeed.util;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient;

import java.util.concurrent.TimeUnit;

public class SolrClientFactory {
    private final String solrUrl;
    private final String collection;

    private SolrClient solrClient;

    public SolrClientFactory(String solrUrl, String collection) {
        this.solrUrl = solrUrl;
        this.collection = collection;
        this.solrClient = createSolrClient();
    }

    private SolrClient createSolrClient() {
        return new Http2SolrClient.Builder(solrUrl)
                .withConnectionTimeout(10, TimeUnit.SECONDS)
                .withIdleTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public SolrClient getSolrClient() {
        return solrClient;
    }

    public String getCollection() {
        return collection;
    }
}
