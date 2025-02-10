FROM amazoncorretto:17-alpine-jdk
VOLUME /tmp
COPY target/discoveryservice-1.0.jar DiscoveryService.jar
ENTRYPOINT ["java", "-jar", "DiscoveryService.jar"]