#!/bin/sh
echo "Building Local Images"
docker build -t solr-news-articles:latest ./infrastructure/solr/

echo "Starting up Zookeeper and Solr"

docker compose up