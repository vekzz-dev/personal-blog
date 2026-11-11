# Etapa 1: Construcción
FROM docker.io/library/maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

# Copiar configuración y dependencias
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copiar código fuente
COPY src ./src

# Construir aplicación (jar ejecutable + dependencias en target/lib)
RUN mvn -B clean package -DskipTests

# Etapa 2: Ejecución
FROM docker.io/library/eclipse-temurin:25-jre

WORKDIR /app

# Usuario sin privilegios y directorio de datos persistente
RUN groupadd --system app \
 && useradd --system --gid app --home-dir /app app \
 && mkdir -p /data \
 && chown -R app:app /app /data

COPY --from=build --chown=app:app /app/target/*.jar app.jar
COPY --from=build --chown=app:app /app/target/lib ./lib

# CUSTOM_DIR mantiene los artículos fuera del ciclo de vida del contenedor.
# PORT debe coincidir con el valor por defecto de App.java.
ENV CUSTOM_DIR=/data \
    PORT=7070 \
    JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75 -XX:+ExitOnOutOfMemoryError"

USER app

# Puerto de la aplicación
EXPOSE 7070

VOLUME ["/data"]

HEALTHCHECK --interval=30s --timeout=3s --start-period=20s --retries=3 \
  CMD bash -c 'exec 3<>/dev/tcp/127.0.0.1/${PORT}' || exit 1

# Ejecutar aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
