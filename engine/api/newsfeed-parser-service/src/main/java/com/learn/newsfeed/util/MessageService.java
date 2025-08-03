package com.learn.newsfeed.util;

import com.learn.newsfeed.model.Document;

import java.util.List;

public interface MessageService {
    List<Document> poll();
}
