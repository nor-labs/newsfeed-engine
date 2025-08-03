package com.learn.newsfeed.model;

import java.util.List;

public class Document {

    private DocMeta docMeta;
    private List<Article> articles;
    public Document(){}
    public Document(DocMeta docMeta, List<Article> articles) {
        this.docMeta = docMeta;
        this.articles = articles;
    }

    public DocMeta getDocMeta() {
        return docMeta;
    }

    public void setDocMeta(DocMeta docMeta) {
        this.docMeta = docMeta;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
