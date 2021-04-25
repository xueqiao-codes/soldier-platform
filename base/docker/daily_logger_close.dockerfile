FROM alpine:3.7

RUN echo "https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/main/" > /etc/apk/repositories

RUN apk update \
        && apk upgrade \
        && apk add --no-cache bash \
        bash-doc \
        bash-completion \
        && rm -rf /var/cache/apk/* 

COPY deploy/bin/daily_logger_close.sh /
RUN chmod a+x /daily_logger_close.sh

# crontabs for root user
RUN echo "0 2 * * * /daily_logger_close.sh" >> /etc/crontabs/root

# start crond with log level 8 in foreground, output to stderr
CMD ["crond", "-f", "-d", "8"]