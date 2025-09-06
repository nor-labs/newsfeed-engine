package com.learn.newsfeed.model;

public class SearchRequest {
    private String userId;
    private String userSearchQuery;

    public SearchRequest() {
    }

    public SearchRequest(String userId, String userSearchQuery) {
        this.userId = userId;
        this.userSearchQuery = userSearchQuery;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserSearchQuery() {
        return userSearchQuery;
    }

    public void setUserSearchQuery(String userSearchQuery) {
        this.userSearchQuery = userSearchQuery;
    }

}
