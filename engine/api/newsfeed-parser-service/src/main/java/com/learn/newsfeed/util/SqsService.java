package com.learn.newsfeed.util;

import com.learn.newsfeed.content.fetcher.ContentFetcher;
import com.learn.newsfeed.model.Document;
import com.learn.newsfeed.model.SqsMessage;
import com.learn.newsfeed.parser.SaxonExtractor;
import net.sf.saxon.s9api.SaxonApiException;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SqsService implements MessageService{
    private String queueUrn;
    private SqsClient sqsClient;
    private ContentFetcher contentFetcher;

    public SqsService(String queueUrn, SqsClient sqsClient, ContentFetcher contentFetcher) {
        this.queueUrn = queueUrn;
        this.sqsClient = sqsClient;
        this.contentFetcher = contentFetcher;
    }

    @Override
    public List<Document> poll() {
        List<Document> documents = new ArrayList<>();
        try{
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest
                    .builder()
                    .maxNumberOfMessages(1)
                    .waitTimeSeconds(3)
                    .queueUrl(queueUrn)
                    .build();

            List<Message> messages =  sqsClient.receiveMessage(receiveMessageRequest).messages();

            messages.parallelStream().forEach(message -> {
                //extract
                SqsMessage metatdata = SqsUtil.messageToObject(message.body());
                if(metatdata!=null){
                    byte[] content =  contentFetcher.getContentAsBytes(metatdata.getBucketName(),metatdata.getObjectUrl());
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
            return documents;
        }catch (Exception ex){
            return documents;
        }
    }
}
