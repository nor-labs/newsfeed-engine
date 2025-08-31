package com.learn.newsfeed.handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class HealthHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        ctx.response().setStatusCode(200).end("Ingestor Consumer is healthy");
    }
}
