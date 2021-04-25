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
	if [ $# -gt 1 ];then
		local extra_popd_num=$2
		for((i=0;i<$extra_popd_num -1;++i))
		do
			popd > /dev/null
		done
	fi
	exit $1
}

LIBRARYS=("zeromq" "thrift" "curlpp" "libmemcached")

if [ $# -lt 1 ];then
	echo "$0 library|all"
	echo "supported library: ${LIBRARYS[*]}"
	exit 1
fi

BUILD_LIBRARY_NAME=$1


INSTALL_DIR=$CURRENT_SCRIPT_DIR/.libs
mkdir -p $INSTALL_DIR || exit 1

function build_zeromq() {
	local ZEROMQ_SRC_DIR=$CURRENT_SCRIPT_DIR/thirdparty/zeromq/zeromq-4.2.1
	pushd $ZEROMQ_SRC_DIR > /dev/null
	mkdir -p out
	pushd out > /dev/null
	cmake -DCMAKE_BUILD_TYPE=RelWithDebInfo .. || exit_popd 1 2
	make || exit_popd 1 2
	cp -f lib/libzmq-static.a $INSTALL_DIR/libzmq.a || exit_popd 1 2
	popd > /dev/null
	popd > /dev/null
}

function build_thrift() {
	local THRIFT_SRC_DIR=$CURRENT_SCRIPT_DIR/thirdparty/thrift/thrift-0.9.1
	pushd $THRIFT_SRC_DIR > /dev/null
	chmod u+x configure
	./configure LDFLAGS="-fPIC" CXXFLAGS="-fPIC -O2 -g" --prefix="$INSTALL_DIR/thrift" || exit_popd 1
	pushd $THRIFT_SRC_DIR/lib/cpp > /dev/null
	make
	make install
	cp -f .libs/libthrift.a .libs/libthriftnb.a $INSTALL_DIR 
	if [ $? -ne 0 ];then
		echo "build thrift failed!!!"
		popd > /dev/null
		exit_popd 1
	fi
	popd > /dev/null
	
	pushd $THRIFT_SRC_DIR/compiler/cpp > /dev/null
	make || exit_popd 1
	cp -f thrift $CURRENT_SCRIPT_DIR/.bin || exit_popd 2
	popd > /dev/null
	
	popd > /dev/null
}


function build_curlpp() {
	local CURL_SRC_DIR=$CURRENT_SCRIPT_DIR/thirdparty/curlpp/curl-7.53.1
	pushd $CURL_SRC_DIR > /dev/null
	chmod u+x configure || exit_popd 1
	./configure --disable-ldap --disable-ldaps --disable-sspi --without-zlib --without-ssl || exit_popd 1
	make || exit_popd 1
	cp -f lib/.libs/libcurl.a $INSTALL_DIR || exit_popd 1
	popd > /dev/null
	
	local CURLPP_SRC_DIR=$CURRENT_SCRIPT_DIR/thirdparty/curlpp/curlpp-0.8.1
	pushd $CURLPP_SRC_DIR > /dev/null
	mkdir -p build || exit_popd 1
	pushd build > /dev/null
	cmake -DCMAKE_BUILD_TYPE=RelWithDebInfo .. -DCURL_INCLUDE_DIR=$CURL_SRC_DIR/include -DCURL_LIBRARY=$CURL_SRC_DIR/lib/.libs || exit_popd 1 2
	make || exit_popd 1 2
	cp -f libcurlpp.a $INSTALL_DIR || exit_popd 1 2
	popd > /dev/null
	popd > /dev/null
}

function build_libmemcached() {
	local LIBMEMCACHED_SRC_DIR=$CURRENT_SCRIPT_DIR/thirdparty/libmemcached/libmemcached-1.0.18
	pushd ${LIBMEMCACHED_SRC_DIR} > /dev/null
	chmod u+x configure
	./configure --prefix=$INSTALL_DIR/libmemcached --enable-shared=false || exit_popd 1
	make || exit_popd 1
	make install || exit_popd 1
	cp -f ./libmemcached/.libs/libmemcached.a $INSTALL_DIR || exit_popd 1
	cp -f ./libmemcached/.libs/libmemcachedutil.a $INSTALL_DIR || exit_popd 1
	cp -f ./libhashkit/.libs/libhashkit.a $INSTALL_DIR || exit_popd 1
	popd > /dev/null
}

if [ "$BUILD_LIBRARY_NAME" == "all" ];then
	for library_name in ${LIBRARYS[@]}
	do
		build_$library_name
	done
else
	library_existed="false"
	for library_name in ${LIBRARYS[@]}
	do
		if [ "$library_name" == "$BUILD_LIBRARY_NAME" ];then
			build_$BUILD_LIBRARY_NAME
			library_existed="true"
			break 
		fi
	done
	if [ "$library_existed" == "false" ];then
		echo "$BUILD_LIBRARY_NAME is not supported, please check"
	fi
fi

echo "Finished!!!"

