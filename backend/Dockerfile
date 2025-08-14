# Backend Dockerfile - yahan par Spring Boot application ka Docker image hai
FROM openjdk:17-jdk-slim

# yahan par working directory set kar rahe hain
WORKDIR /app

# yahan par Maven wrapper aur pom.xml copy kar rahe hain
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# yahan par dependencies download kar rahe hain (for Docker layer caching)
RUN ./mvnw dependency:go-offline -B

# yahan par source code copy kar rahe hain
COPY src src

# yahan par application build kar rahe hain
RUN ./mvnw clean package -DskipTests

# yahan par non-root user create kar rahe hain
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# yahan par JAR file copy kar rahe hain
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# yahan par port expose kar rahe hain
EXPOSE 8080

# yahan par health check add kar rahe hain
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/api/auth/health || exit 1

# yahan par application start kar rahe hain
ENTRYPOINT ["java", "-jar", "/app/app.jar"]