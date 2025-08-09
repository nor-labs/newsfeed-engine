package com.learn.newsfeed.parser;

import com.learn.newsfeed.content.fetcher.ContentFetcher;
import com.learn.newsfeed.model.Document;
import com.learn.newsfeed.service.MessageService;

import java.util.List;

public abstract class ParserProcessor {
    protected final MessageService messageService;
    protected final ContentFetcher contentFetcher;
    public ParserProcessor(MessageService messageService, ContentFetcher contentFetcher) {
        this.messageService = messageService;
        this.contentFetcher = contentFetcher;
    }
    public abstract List<Document> process();
}
