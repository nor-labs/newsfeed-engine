package com.learn.newsfeed.model;

public class DocMeta {
    private String bucket;
    private String docId;
    private String publicationDate;
    private String copyRight;
    private String sourceTitle;

    public DocMeta() {
    }

    public DocMeta(String bucket, String docId, String publicationDate, String copyRight, String sourceTitle) {
        this.bucket = bucket;
        this.docId = docId;
        this.publicationDate = publicationDate;
        this.copyRight = copyRight;
        this.sourceTitle = sourceTitle;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getCopyRight() {
        return copyRight;
    }

    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }
}
