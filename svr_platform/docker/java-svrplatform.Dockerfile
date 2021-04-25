FROM docker-registry.default.svc:5000/soldier-platform/soldier-java

ENV JAVA_SVR_PLATFORM_DIR="/usr/local/soldier/svr_platform"

RUN yum install -y zip unzip boost

RUN mkdir -p $JAVA_SVR_PLATFORM_DIR && \
 mkdir -p $JAVA_SVR_PLATFORM_DIR/backup && \
 mkdir -p $JAVA_SVR_PLATFORM_DIR/services && \
 mkdir -p $JAVA_SVR_PLATFORM_DIR/lib && \
 mkdir -p $JAVA_SVR_PLATFORM_DIR/log && \
 mkdir -p $JAVA_SVR_PLATFORM_DIR/services  && \
 mkdir -p $JAVA_SVR_PLATFORM_DIR/daemon

COPY deploy/docker/*.sh $JAVA_SVR_PLATFORM_DIR/

RUN chmod a+x $JAVA_SVR_PLATFORM_DIR/*.sh && \
 chmod 777 $JAVA_SVR_PLATFORM_DIR/services && \
 chmod 777 $JAVA_SVR_PLATFORM_DIR/daemon
