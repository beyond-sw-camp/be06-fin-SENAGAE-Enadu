FROM openjdk:17-jdk-alpine

# bash, curl, python3, py3-pip 설치
RUN apk add --no-cache bash curl python3 py3-pip \
  && curl -sSL https://sdk.cloud.google.com | bash \
  && /root/google-cloud-sdk/install.sh

EXPOSE 8080

ADD ./build/libs/batch.jar /app.jar

CMD ["java", "-jar", "/app.jar"]