FROM openjdk:11-jdk-slim
COPY target/spam-filter-gateway-service-0.0.1-SNAPSHOT.jar spam-filter-gateway-service-0.0.1-SNAPSHOT.jar
LABEL maintainer="Ramazan Sakin <ramazansakin63@gmail.com>"
ENTRYPOINT ["java", "-jar", "spam-filter-gateway-service-0.0.1-SNAPSHOT.jar"]