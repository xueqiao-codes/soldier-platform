#!/bin/bash

function closeAppLog() {
	if [ -f 'debug.log' ];then
		if [ ! -f "log_open" ];then
			touch no_info
		fi
	fi

	ls -l | egrep ^d | awk '{print $NF;}' | while read dir
	do
		pushd $dir
			closeAppLog
		popd
	done
}


pushd /data/applog

closeAppLog

popd



