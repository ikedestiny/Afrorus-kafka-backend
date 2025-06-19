# Stage 1: Builder with full JDK
FROM eclipse-temurin:17-jdk-jammy AS builder

# Cache Gradle dependencies
WORKDIR /app
COPY gradle ./gradle
COPY gradlew .
COPY settings.gradle .
COPY build.gradle .

# Download dependencies first (cache this layer)
RUN ./gradlew dependencies --no-daemon

# Copy source code and build
COPY src ./src
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Runtime with JRE only
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app
COPY --from=builder /app/build/libs/user-service-*.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]