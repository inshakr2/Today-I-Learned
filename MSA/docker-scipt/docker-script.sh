# Network
docker network create --gateway 172.18.0.1 --subnet 172.18.0.0/16 ecommerce-network

# RabbitMQ
docker run -d --network ecommerce-network \ 
    -p 15672:15672 -p 5672:5672 -p 15671:15671 -p 5671:5671 -p 4369:4369 \ 
    -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest \ 
    --name rabbitmq rabbitmq:3.13.7-management

# config-service
docker run -d -p 8888:8888 --network ecommerce-network \ 
    -e "spring.rabbitmq.host=rabbitmq" \ 
    -e "spring.profiles.active=default" \ 
    -e "encrypt.key-store.location=file://apiEncryptionKey.jks"
    --name config-service chany:config-service:1.0

# discovery-service
docker run -d -p 8761:8761 --network ecommerce-network \ 
    -e "spring.config.import=optional:configserver:http://config-service:8888" \ 
    --name discovery-service chany:discovery-service:1.0

# gateway-service
docker run -d -p 8000:8000 --network ecommerce-network \ 
    -e "spring.rabbitmq.host=rabbitmq" \ 
    -e "spring.config.import=optional:configserver:http://config-service:8888" \ 
    -e "eureka.client.service-url.defaultZone=http://discovery-service:8761/eureka" \ 
    --name apigateway-service chany:apigateway-service:1.0

# MySQL
docker -d -p 3306:3306 --network ecommerce-network \ 
    --name mysql chany:mysql:1.0

# Zipkin
docker run -d -p 9411:9411 --network ecommerce-network \ 
    --name zipkin openzipkin/zipkin

# Prometheus
docker run -d -p 9090:9090 --network ecommerce-network \ 
    -v $(pwd)/docker-data/prometheus/config:/etc/prometheus \ 
    -v $(pwd)/docker-data/prometheus/data:/prometheus \ 
    --name prometheus prom/prometheus:v3.1.0 \ 
    --storage.tsdb.path=/prometheus \ 
    --config.file=/etc/prometheus/prometheus.yml

# Grafana
docker run -d -p 3000:3000 --network ecommecr-network \ 
    -v $(pwd)/docker-data/grafana/data:/var/lib/grafana \ 
    -v $(pwd)/docker-data/grafana/provisioning/:/etc/grafana/provisioning/ \ 
    --restart always --link prometheus \ 
    --name grafana grafana/grafana:11.5.0 

# UserService
docker run -d --network ecommerce-network \ 
    -e "spring.config.import=optional:configserver:http://config-service:8888" \ 
    -e "spring.rabbitmq.host:rabbitmq" \ 
    -e "management.tracing.zipkin.tracing.endpoint=http://zipkin:9411/api/v2/spans" \ 
    -e "eureka.client.service-url.defaultZone=http://discovery-service:8761/eureka" \ 
    -e "logging.file=/api-logs/user-ws.log" \ 
    --name user-service chany:user-service:1.0

# OrderService
docker run -d --network ecommerce-network \ 
    -e "spring.config.import=optional:configserver:http://config-service:8888" \ 
    -e "spring.rabbitmq.host:rabbitmq" \ 
    -e "management.tracing.zipkin.tracing.endpoint=http://zipkin:9411/api/v2/spans" \ 
    -e "eureka.client.service-url.defaultZone=http://discovery-service:8761/eureka" \ 
    -e "spring.datasource.url=jdbc:mysql://mysql:3306/msa" \ 
    -e "logging.file=/api-logs/order-ws.log" \ 
    --name order-service chany:order-service:1.0 

# CatalogService
docker run -d --network ecommerce-network \ 
    -e "spring.config.import=optional:configserver:http://config-service:8888" \ 
    -e "spring.rabbitmq.host:rabbitmq" \ 
    -e "eureka.client.service-url.defaultZone=http://discovery-service:8761/eureka" \ 
    -e "logging.file=/api-logs/catalog-ws.log" \ 
    --name catalog-service chany:catalog-service:1.0