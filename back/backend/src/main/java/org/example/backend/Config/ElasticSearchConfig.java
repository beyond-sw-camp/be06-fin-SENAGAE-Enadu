package org.example.backend.Config;

import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import java.util.Base64;

@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.host}")
    private String host;

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(host)
                .withBasicAuth(username, password)
                .build();
    }

    // RestHighLevelClient
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        String[] hostPort = host.split(":");
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostPort[0], Integer.parseInt(hostPort[1]), "http")
                ).setDefaultHeaders(new BasicHeader[]{
                        new BasicHeader("Authorization",
                                "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes()))
                })
        );
    }
}
