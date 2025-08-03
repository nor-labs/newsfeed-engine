package com.learn.newsfeed.model;
import java.util.List;

public class Article {
    private String id;
    private String headline;
    private String articleLink;
    private String summary;
    private String publishDate;
    private List<String> authors;
    private List<String> categories;

    public Article(String id, String headline, String articleLink, String summary, String publishDate, List<String> authors, List<String> categories) {
        this.id = id;
        this.headline = headline;
        this.articleLink = articleLink;
        this.summary = summary;
        this.publishDate = publishDate;
        this.authors = authors;
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
