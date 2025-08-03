package com.learn.newsfeed.handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.util.function.Supplier;

public class HealthCheckHandler implements Handler<RoutingContext> {
    private final Supplier<Boolean> healthSupplier;
    public HealthCheckHandler(Supplier<Boolean> healthSupplier) {
        this.healthSupplier = healthSupplier;
    }

    @Override
    public void handle(RoutingContext ctx) {
        boolean healthy = healthSupplier.get();
        if (healthy) {
            ctx.response().setStatusCode(200).end("SQS Consumer is healthy");
        } else {
            ctx.response().setStatusCode(500).end("SQS Consumer has issues");
        }
    }
}
