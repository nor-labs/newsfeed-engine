package com.learn.newsfeed.config;
import com.learn.newsfeed.search.IndexSearcher;
import com.learn.newsfeed.search.solr.SolrIndexSearcher;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;

@Configuration
public class Config {

    private final  SolrConfigDto solrConfig;
    public Config(SolrConfigDto solrConfig) {
        this.solrConfig = solrConfig;
    }

    @Bean
    public SolrClient getSolrClient(){
        return new HttpSolrClient.Builder(solrConfig.getServerUrl())
                .withConnectionTimeout(solrConfig.getConnectionTimeout(), TimeUnit.SECONDS)
                .withSocketTimeout(solrConfig.getIdleTimeout(), TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public IndexSearcher getIndexSearcher(SolrClient solrClient){
        return new SolrIndexSearcher(solrClient,solrConfig);
    }
}
