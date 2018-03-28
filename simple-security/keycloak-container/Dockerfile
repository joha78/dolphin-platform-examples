FROM openjdk:8u151-jdk
MAINTAINER Hendrik Ebbers, guigarage.com

RUN apt-get update
RUN apt-get install -y ansible
RUN echo '[local]\nlocalhost' > /etc/ansible/hosts
ADD ansible .
RUN ansible-playbook -vv site.yml -c local

EXPOSE 8080 9990

ENTRYPOINT /opt/keycloak-3.4.0.Final/bin/standalone.sh -b 0.0.0.0
