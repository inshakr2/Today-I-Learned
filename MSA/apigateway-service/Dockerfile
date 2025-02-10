FROM amazoncorretto:17-alpine-jdk
VOLUME /tmp
COPY target/apigateway-service-1.0.jar ApiGatewayService.jar
ENTRYPOINT ["java", "-jar", "ApiGatewayService.jar"]