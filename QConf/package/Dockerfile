FROM docker-registry.default.svc:5000/soldier-platform/soldier-baseos

ENV QCONF_AGENT_DIR=/usr/local/soldier/qconf_agent
ENV QCONF_BUILD_DIRNAME=build-1.2.0

RUN mkdir -p $QCONF_AGENT_DIR && \
 mkdir -p $QCONF_AGENT_DIR/bin && \
 mkdir -p $QCONF_AGENT_DIR/cmd && \
 mkdir -p $QCONF_AGENT_DIR/conf && \
 mkdir -p $QCONF_AGENT_DIR/doc && \
 mkdir -p $QCONF_AGENT_DIR/dumps && \
 mkdir -p $QCONF_AGENT_DIR/include && \
 mkdir -p $QCONF_AGENT_DIR/lib && \
 mkdir -p $QCONF_AGENT_DIR/lock && \
 mkdir -p $QCONF_AGENT_DIR/logs && \
 mkdir -p $QCONF_AGENT_DIR/monitor && \
 mkdir -p $QCONF_AGENT_DIR/result && \
 mkdir -p $QCONF_AGENT_DIR/script && \
 touch $QCONF_AGENT_DIR/pid
 
COPY $QCONF_BUILD_DIRNAME/agent/qconf_agent  $QCONF_AGENT_DIR/bin/
COPY $QCONF_BUILD_DIRNAME/driver/sh/qconf $QCONF_AGENT_DIR/bin/
COPY package/conf/* $QCONF_AGENT_DIR/conf/


CMD $QCONF_AGENT_DIR/bin/qconf_agent
