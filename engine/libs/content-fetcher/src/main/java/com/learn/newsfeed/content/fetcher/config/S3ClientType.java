package com.learn.newsfeed.content.fetcher.config;

public enum S3ClientType {
    DEFAULT("default"),
    DEFAULT_ASYNC("default_async"),
    CREDENTIALS("credentials"),
    CREDENTIALS_ASYNC("credentials_async");

    private String clientType;

    S3ClientType(String clientType){
        this.clientType = clientType;
    }

    public String getClientType(){
        return clientType;
    }
}
