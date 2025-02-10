FROM amazoncorretto:17-alpine-jdk
VOLUME /tmp
COPY target/catalog-service-1.0.jar CatalogService.jar
ENTRYPOINT ["java", "-jar", "Catalog.jar"]