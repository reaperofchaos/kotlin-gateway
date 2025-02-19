package com.jacobconner.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class GatewayApplication

fun main(args: Array<String>) {
	runApplication<GatewayApplication>(*args)
}

@Configuration
class GatewayRoutes {
	@Bean
	fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator =
		builder.routes {
			route {
				path("/service/source/**")
				filters { stripPrefix(1) }
				uri("http://localhost:8081")
			}
		}
}