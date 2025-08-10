# Newsfeed-parser-service

## Building

To launch your tests:
```
./gradlew clean test
```

To package your application:
```
./gradlew clean assemble
```

To run your application:
```
./gradlew clean run
```

## Help

* https://vertx.io/docs/[Vert.x Documentation]
* https://stackoverflow.com/questions/tagged/vert.x?sort=newest&pageSize=15[Vert.x Stack Overflow]
* https://groups.google.com/forum/?fromgroups#!forum/vertx[Vert.x User Group]
* https://discord.gg/6ry7aqPWXy[Vert.x Discord]

## Docker

### Build
```bash
docker build -t newsfeed-parser-service:latest .
```
#### Run
```bash
docker run -p 8080 \
-e AWS_ACCESS_KEY_ID=REPLACE_ME \
-e AWS_SECRET_ACCESS_KEY=REPLACE_ME \
-e AWS_REGION=REPLACE_ME \
-e SQS_QUEUE_URL=REPLACE_ME \
newsfeed-parser-service:latest
```