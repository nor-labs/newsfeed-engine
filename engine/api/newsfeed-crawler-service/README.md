# Newsfeed Crawler Service

This service is responsible for fetching rrs xml files over the internet and stores them in AWS S3 bucket.

### Local Setup

```bash
git clone https://github.com/nor-labs/newsfeed-engine.git
```
Then navigate to service directory

```bash
cd   newsfeed-engine/engine/newsfeed-crawler-service
```

Install dependencies

```bash
go mod tidy
```