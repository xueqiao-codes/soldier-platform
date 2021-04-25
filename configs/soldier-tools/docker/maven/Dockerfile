FROM docker-registry.default.svc:5000/soldier-platform/soldier-java

RUN yum -y install wget which && \
 wget http://apache-mirror.rbc.ru/pub/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz && \
 tar xzvf apache-maven-3.3.9-bin.tar.gz && \
 mv apache-maven-3.3.9 /usr/local/ && \
 rm -f apache-maven-3.3.9-bin.tar.gz && \
 ln -sf /usr/local/apache-maven-3.3.9/bin/mvn /usr/local/bin/mvn
 
ADD conf/settings.xml /usr/local/apache-maven-3.3.9/conf/