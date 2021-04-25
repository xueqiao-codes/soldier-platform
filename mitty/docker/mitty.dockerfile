FROM docker-registry.default.svc:5000/soldier-platform/soldier-java

ENV MITTY_DIR="/usr/local/soldier/mitty"

RUN yum install -y zip unzip boost

RUN mkdir -p $MITTY_DIR && \
 mkdir -p $MITTY_DIR/etc && \
 mkdir -p $MITTY_DIR/etc/example && \
 mkdir -p $MITTY_DIR/log && \
 mkdir -p $MITTY_DIR/lib && \
 mkdir -p $MITTY_DIR/webapps && \
 mkdir -p $MITTY_DIR/work && \
 mkdir -p $MITTY_DIR/func && \
 mkdir -p $MITTY_DIR/lock && \
 mkdir -p $MITTY_DIR/backup 

COPY deploy/docker/mitty.sh $MITTY_DIR/
COPY deploy/lib/* $MITTY_DIR/lib/
COPY java/target/mitty*.jar $MITTY_DIR/lib/

RUN chmod a+x $MITTY_DIR/*.sh && \
 chmod 777 $MITTY_DIR/work && \
 chmod 777 $MITTY_DIR/webapps