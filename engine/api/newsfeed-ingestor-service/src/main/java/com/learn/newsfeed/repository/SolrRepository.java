package com.learn.newsfeed.repository;
import com.learn.newsfeed.logging.EventLog;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrInputDocument;

import java.util.*;
import java.util.stream.Collectors;

public class SolrRepository implements IndexRepository {
    private final String MODULE = SolrRepository.class.getCanonicalName();
    private final SolrClient solrClient;
    private final String collection;
    private final int batchSize;

    public SolrRepository(SolrClient solrClient, String collection, int batchSize) {
        this.solrClient = solrClient;
        this.collection = collection;
        this.batchSize = batchSize;
    }

    @Override
    public void indexDocuments(List<Map<String, Object>> articles) {
        if (articles == null || articles.isEmpty()){
            EventLog.module(MODULE)
                    .message("No documents to index")
                    .warn()
                    .log();
            return;
        }
        try
        {
            Collection<SolrInputDocument> documents = articles.stream().map(this::buildSolrDocument).collect(Collectors.toList());

            UpdateResponse addResponse = solrClient.add(collection, documents);
            EventLog.module(MODULE)
                    .message(String.format("Added %s documents, response status: %s",documents.size(),addResponse.getStatus()))
                    .info()
                    .log();

            UpdateResponse commitResponse = solrClient.commit(collection);
            EventLog.module(MODULE)
                    .message(String.format("Commit response status: %s",commitResponse.getStatus()))
                    .info()
                    .log();
        }catch (SolrException ex)
        {
            EventLog.module(MODULE)
                    .message(String.format("Solr server error while indexing documents, exception: %s ",ex.getMessage()))
                    .error()
                    .log();
        }
        catch (Exception ex){
            EventLog.module(MODULE)
                    .message(String.format("Encountered an error while attempting to index documents: %s ",ex.getMessage()))
                    .error()
                    .log();
        }
    }

    @Override
    public void indexDocumentsInBatches(List<Map<String, Object>> articles, int batchSize) {
        throw new RuntimeException();
    }

    private SolrInputDocument buildSolrDocument(Map<String, Object> articles){
        final SolrInputDocument document = new SolrInputDocument();
        for (Map.Entry<String, Object> entry : articles.entrySet()) {
            document.addField(entry.getKey(), entry.getValue());
        }
        return document;
    }
}
