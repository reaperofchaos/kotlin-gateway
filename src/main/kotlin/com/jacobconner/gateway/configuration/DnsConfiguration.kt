package com.jacobconner.gateway.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DnsConfiguration {

    @Value("\${dns.hostname:Jacob-laptop.mshome.net}")
    private lateinit var hostname: String

    @Bean
    fun customHostsResolver(): Map<String, String> {
        // Map the hostname from environment variable to localhost
        return mapOf(hostname to "127.0.0.1")
    }
}