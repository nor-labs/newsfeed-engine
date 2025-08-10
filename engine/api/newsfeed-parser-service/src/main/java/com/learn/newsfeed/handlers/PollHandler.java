package com.learn.newsfeed.handlers;

import com.learn.newsfeed.model.Document;
import com.learn.newsfeed.parser.ParserProcessor;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class PollHandler implements Handler<RoutingContext> {
    private final Supplier<ParserProcessor> processorSupplier;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    public PollHandler(Supplier<ParserProcessor> processorSupplier) {
        this.processorSupplier = processorSupplier;
    }

    @Override
    public void handle(RoutingContext ctx) {
        var vertxContext = ctx.vertx().getOrCreateContext();
        executor.submit(() -> {
            try {
                List<Document> results = processorSupplier.get().process();
                String json = Json.encode(results);

                vertxContext.runOnContext(v -> {
                    ctx.response()
                            .putHeader("content-type", "application/json")
                            .end(Buffer.buffer(json));
                });
            } catch (Exception e) {
                vertxContext.runOnContext(v -> {
                    ctx.fail(e);
                });
            }
        });
    }
}
