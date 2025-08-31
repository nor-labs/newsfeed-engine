#!/bin/sh
echo "Building Local Images"
docker build -t solr-news-articles:latest ./infrastructure/solr/

#echo "Pulling Zookeeper"
docker pull zookeeper:3.8

echo "Starting up Zookeeper and Solr"

docker compose up