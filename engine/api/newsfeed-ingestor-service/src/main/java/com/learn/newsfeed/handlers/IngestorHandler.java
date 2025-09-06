package com.learn.newsfeed.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.newsfeed.logging.EventLog;
import com.learn.newsfeed.service.IndexService;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class IngestorHandler implements Handler<RoutingContext> {
    private final String MODULE = IngestorHandler.class.getCanonicalName();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Supplier<IndexService> indexServiceSupplier;

    public IngestorHandler(Supplier<IndexService> indexServiceSupplier) {
        this.indexServiceSupplier = indexServiceSupplier;
    }

    @Override
    public void handle(RoutingContext ctx) {
        ctx.request().bodyHandler(buffer -> {
            try {
                List<Map<String, Object>> requestPayload = objectMapper.readValue(
                        buffer.getBytes(),
                        new TypeReference<>() {
                        }
                );

                for (Map<String, Object> wrapper : requestPayload) {
                    Object articlesObj = wrapper.get("articles");
                    if (articlesObj instanceof List) {
                        indexServiceSupplier.get().handleIndexing((List<Map<String, Object>>)articlesObj);
                    }
                }

                ctx.response()
                        .setStatusCode(200)
                        .end("Documents indexed dynamically");

            } catch (Exception e) {
                EventLog.module(MODULE)
                        .message(e.getMessage())
                        .info()
                        .log();                ctx.response()
                        .setStatusCode(400)
                        .end("Invalid JSON: " + e.getMessage());
            }
        });    }
}
