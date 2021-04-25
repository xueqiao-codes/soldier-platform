# java_service_builder
FROM docker-registry.default.svc:5000/soldier-platform/java-svrplatform
LABEL maintainer="wangli"

# TODO: Rename the builder environment variable to inform users about application you provide them
# ENV BUILDER_VERSION 1.0

# TODO: Set labels used in OpenShift to describe the builder image
LABEL io.k8s.description="Platform for building java service" \
      io.k8s.display-name="builder java.service" \
      io.openshift.s2i.scripts-url="image:///usr/libexec/s2i" \
      io.openshift.tags="builder,java_service"
  
# install maven
RUN yum -y install wget which && \
 wget http://apache-mirror.rbc.ru/pub/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz && \
 tar xzvf apache-maven-3.3.9-bin.tar.gz && \
 mv apache-maven-3.3.9 /usr/local/ && \
 rm -f apache-maven-3.3.9-bin.tar.gz && \
 ln -sf /usr/local/apache-maven-3.3.9/bin/mvn /usr/local/bin/mvn
 
ADD maven/conf/settings.xml /usr/local/apache-maven-3.3.9/conf/

COPY ./s2i/bin/ /usr/libexec/s2i
RUN chmod a+x /usr/libexec/s2i/*

# Set the default CMD for the image
# CMD ["/usr/libexec/s2i/usage"]
