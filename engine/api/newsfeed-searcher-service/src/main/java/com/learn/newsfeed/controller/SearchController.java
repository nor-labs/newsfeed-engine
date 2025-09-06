package com.learn.newsfeed.controller;

import com.learn.newsfeed.model.ResultDocument;
import com.learn.newsfeed.model.SearchRequest;
import com.learn.newsfeed.model.SearchResponse;
import com.learn.newsfeed.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api")
public class SearchController {
    private final SearchService service;

    public SearchController(SearchService service) {
        this.service = service;
    }

    @PostMapping("search")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest searchRequest){
        return ResponseEntity.ok(service.search(searchRequest));
    }
}
