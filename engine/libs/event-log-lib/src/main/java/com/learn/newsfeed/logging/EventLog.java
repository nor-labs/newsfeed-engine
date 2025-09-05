package com.learn.newsfeed.logging;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventLog {
    private static final Logger LOG = LoggerFactory.getLogger(EventLog.class);

    private final String module;
    private String message;
    private LogLevel level;

    private EventLog(String module) {
        this.module = module;
    }

    public static EventLog module(String module) {
        return new EventLog(module);
    }

    public EventLog message(String message) {
        this.message = message;
        return this;
    }

    public EventLog info() {
        this.level = LogLevel.INFO;
        return this;
    }


    public EventLog warn() {
        this.level = LogLevel.WARN;
        return this;
    }

    public EventLog error() {
        this.level = LogLevel.ERROR;
        return this;
    }

    public void log() {
        String formattedMessage = String.format("module: [%s] message: %s", module, message);
        switch (level) {
            case INFO -> LOG.info(formattedMessage);
            case WARN -> LOG.warn(formattedMessage);
            case ERROR -> LOG.error(formattedMessage);
        }
    }

    private enum LogLevel {
        INFO, WARN, ERROR
    }
}
