FROM alpine:edge
MAINTAINER coarsehorse
COPY . /url-shortener
RUN apk add --no-cache \
    openjdk11 \
    maven
WORKDIR /url-shortener
RUN mvn clean install
WORKDIR /url-shortener/target
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "url-shortener-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
