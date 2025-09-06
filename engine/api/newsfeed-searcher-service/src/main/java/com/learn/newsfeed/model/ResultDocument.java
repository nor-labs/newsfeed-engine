package com.learn.newsfeed.model;

import java.util.Date;
import java.util.List;


import java.util.List;

public class ResultDocument {
    private final String id;
    private final String headline;
    private final String articleLink;
    private final String summary;
    private final Date publishDate;
    private final List<String> authors;
    private final List<String> categories;
    private final String headlineExact;

    private ResultDocument(ResultDocumentBuilder builder) {
        this.id = builder.id;
        this.headline = builder.headline;
        this.articleLink = builder.articleLink;
        this.summary = builder.summary;
        this.publishDate = builder.publishDate;
        this.authors = builder.authors;
        this.categories = builder.categories;
        this.headlineExact = builder.headlineExact;
    }

    public static ResultDocumentBuilder builder() {
        return new ResultDocumentBuilder();
    }

    public String getId() {
        return id;
    }

    public String getHeadline() {
        return headline;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public String getSummary() {
        return summary;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getHeadlineExact() {
        return headlineExact;
    }

    public static class ResultDocumentBuilder {
        private String id;
        private String headline;
        private String articleLink;
        private String summary;
        private Date publishDate;
        private List<String> authors;
        private List<String> categories;
        private String headlineExact;

        public ResultDocumentBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ResultDocumentBuilder headline(String headline) {
            this.headline = headline;
            return this;
        }

        public ResultDocumentBuilder articleLink(String articleLink) {
            this.articleLink = articleLink;
            return this;
        }

        public ResultDocumentBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public ResultDocumentBuilder publishDate(Date publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public ResultDocumentBuilder authors(List<String> authors) {
            this.authors = authors;
            return this;
        }

        public ResultDocumentBuilder categories(List<String> categories) {
            this.categories = categories;
            return this;
        }

        public ResultDocumentBuilder headlineExact(String headlineExact) {
            this.headlineExact = headlineExact;
            return this;
        }

        public ResultDocument build() {
            return new ResultDocument(this);
        }
    }
}
