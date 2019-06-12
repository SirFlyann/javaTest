FROM java:8-jdk-alpine
COPY ./target/javaTest-0.1.0.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch javaTest-0.1.0-SNAPSHOT.jar'

ENTRYPOINT ["java", "-jar", "javaTest-0.1.0.jar"]
