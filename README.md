# Gateway
This is a SpringBoot cloud gateway using Consul for service discovery. This code is written in kotlin.
Consul gives a great amount of flexibility to the gateway since routes do not
have to be specified as long as the service connecting to the gateway is registered with Consul.


<br />
<br />
This is largely just a few packages and an application yaml file. I discovered
that windows does a lousy job of handling dns resolution with the Consul/spring docker image.
In order to mitigate this, a Java solution was used with the custom host file resolver and the 
dns configuration files. This maps an environment variable of HOST_NAME which is equal to the windows
hostname of the computer running consul. In a perfect universe, I would fix this by making sure Consul can access the windows DNS.  <br />

# Environment Variables
```
HOST_NAME=my-computer.mshome.net 
```

# Gateway Application Yaml
The contents of the yaml in this gateway.

```
server:
  port: 3001

# Custom DNS configuration
dns:
  # used for mapping windows hostname to 127.0.0.1
  hostname: ${HOST_NAME}
  
spring:
  main:
    web-application-type: reactive
  application:
    name: api-gateway
  config:
    import: "optional:consul:"
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:
        register: false
        registerHealthCheck: false
        prefer-ip-address: true
        instance-id: ${spring.application.name}:${random.value}
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      httpclient:
        connect-timeout: 1000
        response-timeout: 5s
      default-filters:
        - name: RewritePath
          args:
            regexp: "/(?<service>[^/]+)/(?<remaining>.*)"
            replacement: "/${remaining}"
        - name: RewritePath
          args:
            regexp: "/(?<service>[^/]+)$"
            replacement: "/"

management:
  endpoints:
    web:
      exposure:
        include: "*"
```


# Client Application yaml
Something similar to this should be in the application yaml in the services that will connect to the gateway.

```
spring:
  application:
    name: <name of application>
  cloud:
    consul:
      host: localhost
      # port consul is running on
      port: 8500
      discovery:
        instance-id: ${spring.application.name}:${vcap.application.instance_id:${spring.application.instance_id:${random.value}}}
```



