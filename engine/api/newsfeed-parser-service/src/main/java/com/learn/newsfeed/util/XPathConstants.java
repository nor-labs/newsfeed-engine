package com.learn.newsfeed.util;

public class XPathConstants {
    public static final String DOC_ID = "if (//newsfeed/metadata/DocId) then //newsfeed/metadata/DocId else //newsfeed/metadata/docId";
    public static final String PUBLICATION_DATE = "//newsfeed/metadata/publicationDate";
    public static final String COPYRIGHT = "//newsfeed/metadata/copyright";
    public static final String SOURCE_TITLE = "//newsfeed/metadata/copyright";

    public static final String ARTICLE_ROOT = "//newsfeed/articles/article";
    public static final String ARTICLE_ID = "@uniqueIdentifier";
    public static final String ARTICLE_HEADLINE = "headline";
    public static final String ARTICLE_LINK = "articleLink";
    public static final String ARTICLE_SUMMARY = "summary";
    public static final String ARTICLE_PUBLICATION_DATE = "publishDate";
    public static final String ARTICLE_AUTHORS = "authors/authors";
    public static final String ARTICLE_CATEGORIES = "categories/category";
}
