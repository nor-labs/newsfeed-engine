package com.learn.newsfeed.handlers;

import com.learn.newsfeed.model.Document;
import com.learn.newsfeed.util.SqsService;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.function.Supplier;

public class PollHandler implements Handler<RoutingContext> {
    private final Supplier<SqsService> sqsServiceSupplier;

    public PollHandler(Supplier<SqsService> sqsServiceSupplier) {
        this.sqsServiceSupplier = sqsServiceSupplier;
    }

    @Override
    public void handle(RoutingContext ctx) {
        List<Document> results = sqsServiceSupplier.get().poll();
        String json = Json.encode(results);
        ctx.response()
                .putHeader("content-type", "application/json")
                .end(Buffer.buffer(json));
    }
}
