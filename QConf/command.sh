#!/bin/bash
#


if [ -L "$0" ];then
    THIS_SCRIPT_REAL_DIR=$( dirname $0 )
else
    # run this script directly, can be run everywhere
    THIS_SCRIPT_REAL_DIR="$(dirname "${BASH_SOURCE:-$0}")"
    if [ "$( echo ${THIS_SCRIPT_REAL_DIR} | grep '^/' | wc -l )" = "0" ];then
        THIS_SCRIPT_REAL_DIR="$( pwd )/${THIS_SCRIPT_REAL_DIR}"
    fi
fi
cd ${THIS_SCRIPT_REAL_DIR} > /dev/null
CURRENT_SCRIPT_DIR=$( pwd )
cd - > /dev/null

QCONF_VERSION=1.2.0
QCONF_DIR="QConf-${QCONF_VERSION}"
TARGET_DIR="build-${QCONF_VERSION}"

function build() {
	pushd $CURRENT_SCRIPT_DIR > /dev/null
	
	if [ ! -L "cpp_platform" ];then
		ln -sf ../cpp_platform  cpp_platform || exit 1
	fi
	
	if [ ! -d $TARGET_DIR ];then
		mkdir $TARGET_DIR || exit 1
	fi

	chmod a+x ${CURRENT_SCRIPT_DIR}/${QCONF_DIR}/deps/zookeeper/configure
	chmod a+x ${CURRENT_SCRIPT_DIR}/${QCONF_DIR}/deps/gdbm/configure
	chmod a+x ${CURRENT_SCRIPT_DIR}/${QCONF_DIR}/deps/curl/configure
	chmod a+x ${CURRENT_SCRIPT_DIR}/${QCONF_DIR}/manager/deps/zookeeper/configure
	pushd $TARGET_DIR > /dev/null
	if [ "$1" = "first" ];then
		cmake ${CURRENT_SCRIPT_DIR}/${QCONF_DIR} -DCMAKE_INSTALL_PREFIX=/usr/local/soldier/qconf_agent || exit 2
	fi
	make || exit 2

	popd > /dev/null

	pushd ${CURRENT_SCRIPT_DIR}/${QCONF_DIR}/driver/java > /dev/null
	make || exit 3
	popd > /dev/null

	pushd ${CURRENT_SCRIPT_DIR}/${QCONF_DIR}/driver/python > /dev/null
	python setup.py build || exit 4
	popd > /dev/null

	popd > /dev/null

}

function install_dependencies() {
    yum -y install cmake gcc gcc-c++ gdbm-devel
}

function deploy() {
	echo "deploy start..."
	
	CURRENT_USER_ID=$( id -u )
	if [ "$CURRENT_USER_ID" != "0" ];then
		echo "please use root user"
		exit 1
	fi

	mkdir -p /public/packages/qconf/qconf_agent
	mkdir -p /public/packages/qconf/qconf_py

	cp -rf /usr/local/soldier/qconf_agent/*  /public/packages/qconf/qconf_agent || exit 1
	cp -f ${CURRENT_SCRIPT_DIR}/${QCONF_DIR}/driver/java/qconf_jar/libqconf_java.so /public/packages/qconf/qconf_agent/lib || exit 1
	rm -f /public/packages/qconf/qconf_agent/pid || exit 1
	rm -rf /public/packages/qconf/qconf_agent/dumps/* || exit 1
	rm -rf /public/packages/qconf/qconf_agent/logs/* || exit 1
	mv /public/packages/qconf/qconf_agent/auto_restart.sh /public/packages/qconf/qconf_agent/auto_restart.sh.install || exit 1
	cp -rf ${CURRENT_SCRIPT_DIR}/${QCONF_DIR}/driver/python/* /public/packages/qconf/qconf_py || exit 1
	rm -f /public/packages/qconf/qconf_agent/script/*
	
	echo "deploy tree..."
	tree /public/packages/qconf
	echo "deploy finished..."
}

if [ $# -lt 1 ];then
	echo "please input build or deploy"
	exit 1
fi

if [ "$1" = "build" ];then
	if [ "$2" = "first" ];then
		build "first"
	else
		build "again"
	fi
	
elif  [ "$1" = "deploy" ];then
	deploy
else
	echo "unkown commad, please input build or deploy"
	exit 1
fi

