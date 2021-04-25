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
cd ${THIS_SCRIPT_REAL_DIR} > /dev/null
CURRENT_SCRIPT_DIR=$( pwd )
cd - > /dev/null

function exit_popd() {
	popd > /dev/null
	exit $1
}

INSTALL_DIR=$CURRENT_SCRIPT_DIR/.bin
mkdir -p ${INSTALL_DIR} || exit 1

function install_automake() {
	local AUTOMAKE_DIR=${CURRENT_SCRIPT_DIR}/tools/automake-1.14.1
	pushd $AUTOMAKE_DIR > /dev/null
	chmod u+x configure
	./configure --prefix=${INSTALL_DIR}/automake || exit_popd 1
	make || exit_popd 1
	make install || exit_popd 1
	popd > /dev/null
}

function install_blade() {
	local BLADE_DIR=${CURRENT_SCRIPT_DIR}/tools/typhoon-blade
	pushd ${BLADE_DIR} > /dev/null
	mkdir -p $INSTALL_DIR/typhoon-blade
	/bin/bash install $INSTALL_DIR/typhoon-blade || exit_popd 1
	chmod u+x $INSTALL_DIR/typhoon-blade/*
	popd > /dev/null
}

function install_swig() {
	local SWIG_DIR=${CURRENT_SCRIPT_DIR}/tools/swig-3.0.12
	pushd ${SWIG_DIR} > /dev/null
	mkdir -p ${INSTALL_DIR}/swig
	chmod u+x autogen.sh configure
	./autogen.sh || exit_popd 1
	./configure --prefix=${INSTALL_DIR}/swig || exit_popd 1
	make || exit_popd 1
	make install || exit_popd 1
	popd > /dev/null
}

install_automake || exit 1
install_blade || exit 1
install_swig || exit 1
echo "Finished All!!!"
