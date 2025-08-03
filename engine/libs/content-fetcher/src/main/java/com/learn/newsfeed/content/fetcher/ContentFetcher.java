package com.learn.newsfeed.content.fetcher;
public interface ContentFetcher {
    byte[] getContentAsBytes(String bucket, String key);
    String getContentAsString(String bucket, String key);
}
