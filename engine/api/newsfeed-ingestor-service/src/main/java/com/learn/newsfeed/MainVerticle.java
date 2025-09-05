package com.learn.newsfeed;
import com.learn.newsfeed.handlers.HealthHandler;
import com.learn.newsfeed.handlers.IngestorHandler;
import com.learn.newsfeed.repository.IndexRepository;
import com.learn.newsfeed.repository.SolrRepository;
import com.learn.newsfeed.service.IndexService;
import com.learn.newsfeed.util.EnvironmentResolve;
import com.learn.newsfeed.util.SolrClientFactory;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {
    private IndexService indexService;
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

                String solrUrl = EnvironmentResolve.resolve(ar.result().getJsonObject("solr").getString("serverUrl"));
                String collection = EnvironmentResolve.resolve(ar.result().getJsonObject("solr").getString("collection"));
                int batchSize = Integer.parseInt(EnvironmentResolve.resolve(ar.result().getJsonObject("index").getString("batchSize")));

                SolrClientFactory solrClientFactory = new SolrClientFactory(solrUrl,collection);
                IndexRepository indexRepository = new SolrRepository(solrClientFactory.getSolrClient(),solrClientFactory.getCollection(),batchSize);

                Router router = Router.router(vertx);
                router.get("/").handler(new HealthHandler());
                router.post("/index").handler(new IngestorHandler(()-> new IndexService(indexRepository)));

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
