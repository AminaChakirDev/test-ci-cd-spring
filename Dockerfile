# Utilise une image de base Java 21
FROM eclipse-temurin:21-jdk-jammy

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré par Maven
COPY target/myblog-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port 8080
EXPOSE 8080

# Commande pour lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]