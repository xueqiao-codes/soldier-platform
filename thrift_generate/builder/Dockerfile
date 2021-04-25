# java_service_builder
FROM docker-registry.default.svc:5000/soldier-platform/mitty
LABEL maintainer="wangli"

# TODO: Rename the builder environment variable to inform users about application you provide them
# ENV BUILDER_VERSION 1.0

# TODO: Set labels used in OpenShift to describe the builder image
LABEL io.k8s.description="Platform for building java service" \
      io.k8s.display-name="builder java.service" \
      io.openshift.s2i.scripts-url="image:///usr/libexec/s2i" \
      io.openshift.tags="builder,mitty"
  
# install maven
RUN yum -y install wget which && \
 wget http://apache-mirror.rbc.ru/pub/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz && \
 tar xzvf apache-maven-3.3.9-bin.tar.gz && \
 mv apache-maven-3.3.9 /usr/local/ && \
 rm -f apache-maven-3.3.9-bin.tar.gz && \
 ln -sf /usr/local/apache-maven-3.3.9/bin/mvn /usr/local/bin/mvn
 
ADD maven/conf/settings.xml /usr/local/apache-maven-3.3.9/conf/

# install git and ssh keys
RUN yum -y install git && \ 
 mkdir -p /root/.ssh && \
 chmod 700 /root/.ssh 
 
ADD keys/* /root/.ssh/

RUN chmod 600 /root/.ssh/gitreader.pem && \
 chmod 644 /root/.ssh/gitreader.pub && \
 git config --global user.name "gitreader" && \
 git config --global user.email "gitreader@xueqiao.cn" && \
 echo "Host git.soldier-tools.svc" >> /root/.ssh/config && \
 echo "  HostName git.soldier-tools.svc" >> /root/.ssh/config && \
 echo "  Port 29418" >> /root/.ssh/config && \
 echo "  User gitreader" >> /root/.ssh/config && \
 echo "  IdentityFile  /root/.ssh/gitreader.pem" >> /root/.ssh/config && \
 echo "  IdentitiesOnly yes" >> /root/.ssh/config && \
 echo "  StrictHostKeyChecking no" >> /root/.ssh/config  && \
 echo "  UserKnownHostsFile /dev/null" >> /root/.ssh/config && \
 chmod 644 /root/.ssh/config 

# install thrift
RUN mkdir -p /public/scripts/bin
ADD bin/*  /public/scripts/bin/
RUN chmod a+x /public/scripts/bin/thrift && \
 chmod a+x /public/scripts/bin/java_svr_with_py_test.sh && \
 chmod a+x /public/scripts/bin/idl_update.sh
 
COPY ./s2i/bin/ /usr/libexec/s2i
RUN chmod a+x /usr/libexec/s2i/*

# Set the default CMD for the image
# CMD ["/usr/libexec/s2i/usage"]
