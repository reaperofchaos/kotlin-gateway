package com.jacobconner.gateway.configuration.util

import io.netty.resolver.HostsFileEntriesResolver
import io.netty.resolver.ResolvedAddressTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.InetAddress

@Component
class CustomHostsFileResolver(@Autowired private val customHostsResolver: Map<String, String>) :
    HostsFileEntriesResolver {

    override fun address(inetHost: String, resolvedAddressTypes: ResolvedAddressTypes): InetAddress? {
        return customHostsResolver[inetHost]?.let {
            InetAddress.getByName(it)
        }
    }
}