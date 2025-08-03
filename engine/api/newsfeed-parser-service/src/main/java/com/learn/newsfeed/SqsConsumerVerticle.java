package com.learn.newsfeed;
import com.learn.newsfeed.content.fetcher.AwsS3ContentFetcher;
import com.learn.newsfeed.content.fetcher.ContentFetcher;
import com.learn.newsfeed.content.fetcher.config.S3ClientFactory;
import com.learn.newsfeed.handlers.HealthCheckHandler;
import com.learn.newsfeed.handlers.PollHandler;
import com.learn.newsfeed.model.SqsMessage;
import com.learn.newsfeed.parser.SaxonExtractor;
import com.learn.newsfeed.util.EnvironmentResolve;
import com.learn.newsfeed.util.SqsService;
import com.learn.newsfeed.util.SqsUtil;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import net.sf.saxon.s9api.SaxonApiException;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import java.util.List;

public class SqsConsumerVerticle extends AbstractVerticle {
    private volatile boolean healthy = true;
    private  String queueUrl="<REPLACE_WITH_CONFIG";
    private SqsClient sqsClient;
    private ContentFetcher contentFetcher;
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        contentFetcher = new AwsS3ContentFetcher(S3ClientFactory.createS3Client());
        sqsClient = SqsClient.create();
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
                queueUrl = EnvironmentResolve.resolve(ar.result().getJsonObject("sqs").getString("queueUrn"));

                SqsService sqsService = new SqsService(queueUrl, sqsClient, contentFetcher);
                // Setup router
                Router router = Router.router(vertx);
                router.get("/health").handler(new HealthCheckHandler(() -> healthy));
                router.get("/poll").handler(new PollHandler(() -> sqsService));

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
