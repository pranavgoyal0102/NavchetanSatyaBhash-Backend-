FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . .
RUN ./mvnw clean install
CMD ["java", "-jar", "target/NavchetanSatyabhash-0.0.1-SNAPSHOT.jar"]

