package com.learn.newsfeed.service;

import com.learn.newsfeed.repository.IndexRepository;

import java.util.List;
import java.util.Map;

public class IndexService {

    private final IndexRepository indexRepository;

    public IndexService(IndexRepository indexRepository) {
        this.indexRepository = indexRepository;
    }

    public void handleIndexing(List<Map<String, Object>> indexDocs){
        indexRepository.indexDocuments(indexDocs);
    }
}
