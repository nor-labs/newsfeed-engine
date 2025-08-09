package com.learn.newsfeed;
import com.learn.newsfeed.content.fetcher.AwsS3ContentFetcher;
import com.learn.newsfeed.content.fetcher.ContentFetcher;
import com.learn.newsfeed.content.fetcher.config.S3ClientFactory;
import com.learn.newsfeed.handlers.HealthCheckHandler;
import com.learn.newsfeed.handlers.PollHandler;
import com.learn.newsfeed.parser.ParserProcessor;
import com.learn.newsfeed.parser.RestParserProcessor;
import com.learn.newsfeed.util.EnvironmentResolve;
import com.learn.newsfeed.service.SqsService;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import software.amazon.awssdk.services.sqs.SqsClient;

public class SqsConsumerVerticle extends AbstractVerticle {
    private volatile boolean healthy = true;
    private  String queueUrl="<REPLACE_WITH_CONFIG";
    private ParserProcessor processor;
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
//        vertx.setPeriodic(3600000,id -> {
//            pollMessages();
//        });
        //Setup configurations
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
                ContentFetcher contentFetcher = new AwsS3ContentFetcher(S3ClientFactory.createS3Client());
                SqsClient sqsClient = SqsClient.create();
                queueUrl = EnvironmentResolve.resolve(ar.result().getJsonObject("sqs").getString("queueUrn"));

                SqsService sqsService = new SqsService(queueUrl, sqsClient);
                processor = new RestParserProcessor(sqsService,contentFetcher);
                // Setup router
                Router router = Router.router(vertx);
                router.get("/health").handler(new HealthCheckHandler(() -> healthy));
                router.get("/poll").handler(new PollHandler(() -> processor));

                vertx.createHttpServer()
                        .requestHandler(router)
                        .listen(8888)
                        .onSuccess(http -> {
                            System.out.println("HTTP server started on port 8888");
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

    private void pollMessages(){


    }
}
