FROM docker-registry.default.svc:5000/soldier-platform/soldier-java

ENV DAL_SET_AGENT_DIR=/usr/local/soldier/dal_set

RUN mkdir -p $DAL_SET_AGENT_DIR && \
 mkdir -p $DAL_SET_AGENT_DIR/bin && \
 mkdir -p $DAL_SET_AGENT_DIR/config && \
 mkdir -p $DAL_SET_AGENT_DIR/data && \
 mkdir -p $DAL_SET_AGENT_DIR/log
 
COPY config/config.properties $DAL_SET_AGENT_DIR/daemon_config.properties
COPY java/target/dal_set_agent_daemon-1.0-SNAPSHOT-jar-with-dependencies.jar $DAL_SET_AGENT_DIR/bin 

CMD java -jar $DAL_SET_AGENT_DIR/bin/dal_set_agent_daemon-1.0-SNAPSHOT-jar-with-dependencies.jar $DAL_SET_AGENT_DIR/daemon_config.properties