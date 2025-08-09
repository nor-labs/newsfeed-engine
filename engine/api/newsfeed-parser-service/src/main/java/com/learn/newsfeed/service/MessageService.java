package com.learn.newsfeed.service;
import com.learn.newsfeed.model.Event;

import java.util.List;

public interface MessageService{
    List<Event> poll();
}
