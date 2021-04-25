FROM docker-registry.default.svc:5000/soldier-platform/soldier-baseos:1.0

ADD jdk-11.0.1_linux-x64_bin.rpm /root/

RUN rpm -ivh /root/jdk-11.0.1_linux-x64_bin.rpm && \
 rm -f /root/jdk-11.0.1_linux-x64_bin.rpm

