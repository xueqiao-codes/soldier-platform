#!/bin/bash

if [ -L "$0" ];then
	THIS_SCRIPT_REAL_DIR=$( dirname $0 )
else
	# run this script directly, can be run everywhere
	THIS_SCRIPT_REAL_DIR="$(dirname "${BASH_SOURCE:-$0}")"
	if [ "$( echo ${THIS_SCRIPT_REAL_DIR} | grep '^/' | wc -l )" = "0" ];then
		THIS_SCRIPT_REAL_DIR="$( pwd )/${THIS_SCRIPT_REAL_DIR}"
	fi
fi

yum -y install autoconf automake texinfo pcre pcre-devel bison gcc \
   gcc-c++ boost-devel openssl-devel libevent-devel make scons cmake python-devel flex git || exit
rpm -ivh rpms/python2-scons-3.0.1-5.el7.noarch.rpm || exit

localedef -c -f UTF-8 -i zh_CN zh_CN.UTF-8 || exit

mkdir -p /root/.ssh || exit
chmod 700 /root/.ssh || exit

cp -f ${THIS_SCRIPT_REAL_DIR}/keys/*  /root/.ssh || exit
chmod 600 /root/.ssh/gitreader.pem || exit
chmod 644 /root/.ssh/gitreader.pub || exit
 
git config --global user.name "gitreader" || exit
git config --global user.email "gitreader@xueqiao.cn" || exit

cat <<EOF>> /root/.ssh/config
Host git.soldier-tools.svc
  HostName git.soldier-tools.svc
  Port 29418
  User gitreader
  IdentityFile  /root/.ssh/gitreader.pem
  IdentitiesOnly yes
  StrictHostKeyChecking no
  UserKnownHostsFile /dev/null
EOF

chmod 644 /root/.ssh/config ||  exit