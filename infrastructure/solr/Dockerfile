FROM solr

ARG CONFIGSET_NAME=news_articles

COPY configsets/${CONFIGSET_NAME}/conf/ /opt/solr/server/solr/configsets/${CONFIGSET_NAME}/conf/

COPY solr_manager.sh ./solr_manager.sh

USER root

RUN chown -R solr:solr /opt/solr/server/solr/configsets/${CONFIGSET_NAME}

USER solr

CMD ["sh", "./solr_manager.sh"]