FROM docker-registry.default.svc:5000/soldier-platform/soldier-baseos

RUN yum -y install readline-devel gcc autoconf automake libtool openssl-devel zlib-devel pcre-devel make
