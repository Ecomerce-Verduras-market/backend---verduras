FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /workspace
ARG SERVICE
COPY pom.xml .
COPY shared-events shared-events
COPY discovery-service discovery-service
COPY gateway-service gateway-service
COPY auth-service auth-service
COPY user-service user-service
COPY product-service product-service
COPY inventory-service inventory-service
COPY order-service order-service
COPY payment-service payment-service
COPY notification-service notification-service
RUN --mount=type=cache,target=/root/.m2 mvn -pl ${SERVICE} -am -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
ARG SERVICE
ENV JAVA_OPTS=""
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*
COPY --from=builder /workspace/${SERVICE}/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
