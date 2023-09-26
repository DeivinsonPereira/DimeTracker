FROM openjdk:17
VOLUME ./tmp
EXPOSE 8080
ADD ./target/gerenciador-despesas-0.0.1-SNAPSHOT.jar dimiTracker.jar
ENTRYPOINT ["java","-jar","/dimiTracker.jar"]