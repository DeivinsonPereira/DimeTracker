FROM openjdk:17
VOLUME ./tmp
EXPOSE 8080
ADD ./target/dimeTracker-0.0.1-SNAPSHOT.jar dimiTracker.jar
ENTRYPOINT ["java","-jar","/dimiTracker.jar"]