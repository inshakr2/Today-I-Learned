FROM amazoncorretto:17-alpine-jdk
VOLUME /tmp
COPY target/order-service-1.0.jar OrderService.jar
ENTRYPOINT ["java", "-jar", "OrderService.jar"]