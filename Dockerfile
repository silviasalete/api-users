# Etapa 1: Build da aplicação
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copiar apenas pom.xml e baixar dependências (cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fonte e compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final (leve)
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar o .jar da etapa anterior
COPY --from=builder /app/target/*.jar api-users-0.0.1-SNAPSHOT.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8081

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "api-users-0.0.1-SNAPSHOT.jar"]
