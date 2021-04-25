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
InstallPath=$( pwd )
cd - > /dev/null

if [ $# -lt "1" ];then
	echo "please input the install zip path"
	exit 1
fi

if [ ! -f $1 ];then
	echo "zip file is not exist! please check"
	exit 1
fi

if [ 0 -eq $( echo $1 | egrep "*.zip$" | wc -l ) ];then
	echo "it's not a zip file for $1! please check"
	exit 1
fi 

FORCE=false
if [ $# -ge 2 ];then
	if [ $2 = "--force" ];then
		FORCE=true
		echo "Using Force Mode!!!"
	fi
fi

if [ 0 -ne $( echo $1 | egrep "^/" | wc -l ) ];then
	ZIP_PATH=$1
else
	ZIP_PATH=$(pwd)/$1
fi
ZIP_NAME=$( basename $1 )

# get service name from zip
SERVICE_NAME=$( echo "$ZIP_NAME" | awk -F '.' '{service_name=$1;for(i=2;i<=NF-1;++i)service_name=service_name"."$i;print service_name;}' | \
	awk -F '-' '{print $1}' )
echo "service name is $SERVICE_NAME"

function extractParameters() {	
	SVRPLATFORM_DIR=${THIS_SCRIPT_REAL_DIR}

	SERVICE_PACKAGE_NAME="$SERVICE_NAME.zip"
	SERVICE_PACKAGE=$ZIP_PATH

	RUNNING_SERVICE_PACKAGE="$SVRPLATFORM_DIR/services/${SERVICE_PACKAGE_NAME}"
	
	FIRST_INSTALL=true
	if [ -d "$SVRPLATFORM_DIR/services/$SERVICE_NAME" ];then
		FIRST_INSTALL=false
	fi
}

function checkZip() {
	unzip -t $ZIP_PATH > /dev/null 2>&1
	if [ $? -ne 0 ];then
		echo "zip file $ZIP_PATH is not right! please check"
		exit 1
	fi
	
	count=$( unzip -l $ZIP_PATH | egrep "service.properties" | wc -l )
	if [ $count -eq 0 ];then
		echo "zip file $ZIP_PATH is not a service zip for svr_platform"
		exit 1
	fi
}

function checkNeedInstall() {
	if [ $( md5sum $SERVICE_PACKAGE | awk '{print $1}' ) == $( md5sum $RUNNING_SERVICE_PACKAGE | awk {'print $1'} ) ];then
		echo "the update jar md5 is same to running jar, not need to update"
		exit 0
	fi
}

function backupOldZip() {
	currentTime=$( date +%Y-%m-%d_%H-%M-%S )

	BACKUP_SERVICE_PACKAGE=${InstallPath}/backup/${SERVICE_NAME}_$currentTime.zip

	echo "copy ${RUNNING_SERVICE_PACKAGE} to ${BACKUP_SERVICE_PACKAGE}"
	cp -f $RUNNING_SERVICE_PACKAGE  ${BACKUP_SERVICE_PACKAGE}
	if [ $? -ne 0 ];then
		echo "backup $RUNNING_SERVICE_PACKAGE failed"
		exit 2
	fi
}

function revertBackupZip() {
	cp -f ${BACKUP_SERVICE_PACKAGE} ${InstallPath}/services/$SERVICE_NAME.zip
	if [ $? -eq 0 ];then
		rm -f ${BACKUP_SERVICE_PACKAGE}
	fi
}

function preparedInstallZip() {
	echo "copy $SERVICE_PACKAGE to ${InstallPath}/services/$SERVICE_NAME.zip"
	cp -f $SERVICE_PACKAGE  ${InstallPath}/services/$SERVICE_NAME.zip
	if [ $? -ne 0 ];then
		echo "copy installing $SERVICE_PACKAGE failed!"
		revertBackupZip
		exit 3
	fi
}

function cleanUpOldInstall() {
	if [ -d ${InstallPath}/services/$SERVICE_NAME ];then
		echo "Cleanup Old Install"
		pushd ${InstallPath}/services/$SERVICE_NAME  > /dev/null
		if [ "$FORCE" = "true" ];then
			rm -rf *
		else
			rm -rf ext_jar
			rm -f *.jar
		fi
		popd > /dev/null
	fi
}

function installZip() {
	cd ${InstallPath}/services
	INSTALL_RESULT=true
	unzip -n $SERVICE_NAME.zip
	if [ $? -ne 0 ];then
		echo "unzip $SERVICE_NAME.zip failed! restore the old zip"
		rm -f $SERVICE_NAME.zip

		if [ "z$BACKUP_SERVICE_PACKAGE" != "z" ];then
			mv ${BACKUP_SERVICE_PACKAGE} $SERVICE_NAME.zip
		fi
		INSTALL_RESULT=false
	fi
	cd -

	if [ "$INSTALL_RESULT" == "true" ];then
		if [ "$FIRST_INSTALL" == "true" ];then
			${InstallPath}/service.sh start $SERVICE_NAME
		else
			${InstallPath}/service.sh restart $SERVICE_NAME
		fi
		exit $?
	else
		exit 1
	fi
}

function main() {
	extractParameters 
	checkZip
	
	if [ -f "$RUNNING_SERVICE_PACKAGE" ];then
		if [ "$FORCE" = "false" ];then
			checkNeedInstall
		fi
		backupOldZip
	fi
	
	preparedInstallZip
	cleanUpOldInstall
	installZip
}

main

