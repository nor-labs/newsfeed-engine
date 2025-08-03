package com.learn.newsfeed.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SqsMessage {
    @JsonProperty("Timestamp")
    private String timestamp;

    @JsonProperty("ObjectUrl")
    private String objectUrl;

    @JsonProperty("BucketName")
    private String bucketName;

    @JsonProperty("MessageId")
    private String messageId;

    public SqsMessage() {
    }

    public SqsMessage(String timestamp, String objectUrl, String bucketName, String messageId) {
        this.timestamp = timestamp;
        this.objectUrl = objectUrl;
        this.bucketName = bucketName;
        this.messageId = messageId;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getObjectUrl() {
        return objectUrl;
    }

    public void setObjectUrl(String objectUrl) {
        this.objectUrl = objectUrl;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
