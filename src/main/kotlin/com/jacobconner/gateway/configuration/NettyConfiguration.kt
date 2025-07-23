package com.jacobconner.gateway.configuration

import com.jacobconner.gateway.configuration.util.CustomHostsFileResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.config.HttpClientProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration

@Configuration
class NettyConfiguration {

    @Autowired
    private lateinit var customHostsFileResolver: CustomHostsFileResolver

    @Bean
    @Primary
    fun httpClient(properties: HttpClientProperties): HttpClient {
        val provider = ConnectionProvider.builder("custom")
            .maxConnections(500) // Default value
            .pendingAcquireTimeout(Duration.ofMillis(45000)) // Default timeout in milliseconds
            .build()

        return HttpClient.create(provider)
            .resolver { spec ->
                spec.hostsFileEntriesResolver(customHostsFileResolver)
            }
    }
}