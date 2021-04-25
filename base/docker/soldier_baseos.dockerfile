FROM centos:7.4.1708
LABEL maintainer="wangli"

RUN yum update -y && \
    yum reinstall -y glibc-common && \
    yum install -y telnet net-tools && \
    yum clean all && \
    rm -rf /tmp/* rm -rf /var/cache/yum/* && \
    localedef -c -f UTF-8 -i zh_CN zh_CN.UTF-8 

# locale
ENV LANG=zh_CN.UTF-8 \
    LANGUAGE=zh_CN:zh \
    LC_ALL=zh_CN.UTF-8

# timezone 
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


# all project need boost
RUN yum -y install boost
