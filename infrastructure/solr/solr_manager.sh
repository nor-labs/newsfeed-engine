#!/bin/sh

echo "Uploading configsets to zookeeper..."
# Upload config before starting Solr - this works when ZK is available
bin/solr zk upconfig -n news_articles -d /opt/solr/server/solr/configsets/news_articles/conf -z zookeeper:2181

echo "Starting Solr..."
# Start Solr in foreground mode
bin/solr start -c -f -z zookeeper:2181 &
SOLR_PID=$!

echo "Waiting for Solr to be ready..."
sleep 15

echo "Creating collection..."
bin/solr create -c news_articles -n news_articles -shards 1 -replicationFactor 1

echo "Solr setup complete. Bringing to foreground..."
# Bring the background Solr process to foreground
wait $SOLR_PID