FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test --no-daemon

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar ./

EXPOSE 8080
CMD ["java", "-jar", "auth-service-0.0.1-SNAPSHOT.jar"]