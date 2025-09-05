package com.learn.newsfeed.repository;
import java.util.List;
import java.util.Map;

public interface IndexRepository {
    void indexDocuments(List<Map<String, Object>> articles);
    void indexDocumentsInBatches(List<Map<String, Object>> articles, int batchSize);
}
