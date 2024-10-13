#Alpine
FROM jenkins/jenkins:lts-alpine

#usuario root 

USER root

#OpenJDK 21, Maven y Docker CLI
RUN apk update && \
    apk add --no-cache openjdk21 maven docker-cli docker-compose

RUN apk add --no-cache sudo

RUN echo "jenkins ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers

# entorno para OpenJDK 21
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk
ENV MAVEN_HOME=/usr/share/maven
ENV PATH="$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH"

#usuario Jenkins
USER jenkins

#puerto 8080 de Jenkins
EXPOSE 8080

#comando de entrada por defecto para Jenkins
ENTRYPOINT ["/sbin/tini", "--", "/usr/local/bin/jenkins.sh"]