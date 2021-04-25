FROM docker-registry.default.svc:5000/soldier-platform/soldier-java

ENV ROUTE_AGENT_DIR=/usr/local/soldier/route_agent

RUN mkdir -p $ROUTE_AGENT_DIR && \
 mkdir -p $ROUTE_AGENT_DIR/include && \
 mkdir -p $ROUTE_AGENT_DIR/bin && \
 mkdir -p $ROUTE_AGENT_DIR/lib && \
 mkdir -p $ROUTE_AGENT_DIR/log && \
 mkdir -p $ROUTE_AGENT_DIR/data && \
 mkdir -p $ROUTE_AGENT_DIR/conf 
 
COPY route_agent_tool2 $ROUTE_AGENT_DIR/bin/
COPY conf/config.properties $ROUTE_AGENT_DIR/conf/
COPY java/target/route_agent_daemon-1.0-SNAPSHOT-jar-with-dependencies.jar $ROUTE_AGENT_DIR/bin

CMD java -jar $ROUTE_AGENT_DIR/bin/route_agent_daemon-1.0-SNAPSHOT-jar-with-dependencies.jar $ROUTE_AGENT_DIR/conf/config.properties
