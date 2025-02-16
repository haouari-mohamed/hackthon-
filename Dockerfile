FROM eclipse-temurin:17-jdk

WORKDIR /karizmavolunteers

COPY Back-End/target/benevoleKarizma-*.jar /karizmavolunteers/karizmavolunteers.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "/karizmavolunteers/karizmavolunteers.jar"]
