FROM openjdk:17-jdk

# bash, curl, python3, py3-pip 설치
RUN apt-get update && apt-get install -y bash curl python3 python3-pip \
  && curl -sSL https://sdk.cloud.google.com | bash \
  && /root/google-cloud-sdk/install.sh

EXPOSE 8080

ADD ./build/libs/batch.jar /app.jar

CMD ["java", "-jar", "/app.jar"]