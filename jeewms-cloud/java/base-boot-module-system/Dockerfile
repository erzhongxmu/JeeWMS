FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER baseos@163.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /base-boot

WORKDIR /base-boot

EXPOSE 8080

ADD ./target/base-boot-module-system-2.4.6.jar ./

CMD sleep 60;java -Djava.security.egd=file:/dev/./urandom -jar base-boot-module-system-2.4.6.jar
