package com.learn.newsfeed.service;

import com.learn.newsfeed.content.fetcher.ContentFetcher;
import com.learn.newsfeed.model.Event;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.MessageSystemAttributeName;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;
import java.util.stream.Collectors;

public class SqsService implements MessageService {
    private String queueUrn;
    private SqsClient sqsClient;

    public SqsService(String queueUrn, SqsClient sqsClient) {
        this.queueUrn = queueUrn;
        this.sqsClient = sqsClient;
    }

    @Override
    public List<Event> poll() {
        try{
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest
                    .builder()
                    .maxNumberOfMessages(1)
                    .waitTimeSeconds(3)
                    .queueUrl(queueUrn)
                    .build();

           return sqsClient.receiveMessage(receiveMessageRequest).messages().stream().map(message -> {
                var timestamp = message.attributes().get(MessageSystemAttributeName.SENT_TIMESTAMP);
                return  new Event(message.messageId(),message.body(),timestamp);
            }).collect(Collectors.toList());
        }catch (Exception ex){
            return null;
        }
    }
}
