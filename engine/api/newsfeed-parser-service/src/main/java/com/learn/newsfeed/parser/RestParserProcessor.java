package com.learn.newsfeed.parser;

import com.learn.newsfeed.content.fetcher.ContentFetcher;
import com.learn.newsfeed.model.Document;
import com.learn.newsfeed.model.Event;
import com.learn.newsfeed.model.EventMessage;
import com.learn.newsfeed.service.MessageService;
import com.learn.newsfeed.util.SqsUtil;
import net.sf.saxon.s9api.SaxonApiException;

import java.util.ArrayList;
import java.util.List;

public class RestParserProcessor extends ParserProcessor {

    public RestParserProcessor(MessageService messageService, ContentFetcher contentFetcher) {
        super(messageService, contentFetcher);
    }

    @Override
    public List<Document> process() {
        List<Document> documents = new ArrayList<>();
        try{
            //Get messagges
            List<Event> events = messageService.poll();
            events.parallelStream().forEach(event -> {
                EventMessage eventMessage = SqsUtil.messageToObject(event.getEventData());
                if(eventMessage!=null){
                    byte[] content =  contentFetcher.getContentAsBytes(eventMessage.getBucketName(),eventMessage.getObjectUrl());
                    try {
                        SaxonExtractor.SaxonExtractorBuilder saxonExtractorBuilder = new SaxonExtractor.SaxonExtractorBuilder()
                                .fromBytes(content);
                        SaxonExtractor saxonExtractor = new SaxonExtractor(saxonExtractorBuilder);
                        documents.add(saxonExtractor.extract());
                    } catch (SaxonApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return documents;
    }
}
