package com.learn.newsfeed;
import com.learn.newsfeed.handlers.HealthHandler;
import com.learn.newsfeed.handlers.IngestorHandler;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
public class MainVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        ConfigStoreOptions fileStore = new ConfigStoreOptions()
                .setType("file")
                .setFormat("yaml")
                .setConfig(new JsonObject()
                        .put("path","application.yaml"));
        ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(fileStore);

        ConfigRetriever retriever = ConfigRetriever.create(vertx,options);

        retriever.getConfig().onComplete(ar -> {
            if (ar.failed()) {
                System.err.println("Failed to load config");
                startPromise.fail(ar.cause());
            } else {

                Router router = Router.router(vertx);
                router.get("/").handler(new HealthHandler());
                router.get("/index").handler(new IngestorHandler());

                vertx.createHttpServer()
                        .requestHandler(router)
                        .listen(8089)
                        .onSuccess(http -> {
                            System.out.println("HTTP server started on port 8089");
                            startPromise.complete();
                        })
                        .onFailure(startPromise::fail);
            }
        });
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        super.stop(stopPromise);
    }
}
