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

function help() {
    echo "$0 IDL OUTPUTDIR"
    exit 1
}

if [ $# -lt 1 ];then
    help
fi

IDL=$1
OUTPUTDIR=$( pwd )

if [ $# -ge 2 ];then
    OUTPUTDIR=$2
fi

if [ ! -f "$IDL" ];then
    echo "$IDL not existed! please check"
    exit 1
fi

if [ ! -d "$OUTPUTDIR" ];then
    echo "$OUTPUTDIR is not existed, please check"
    exit 1
fi

IDL_PROGRAME_NAME=$(basename ${IDL%.*})
SERVICE_NAME_COUNT=$( ${THIS_SCRIPT_REAL_DIR}/thrift -detect $IDL | grep '^detect service' | wc -l )
if [ $SERVICE_NAME_COUNT -eq 0 ];then
	echo "no service is detected, please check"
	exit 1
else
	if [ $SERVICE_NAME_COUNT -gt 1 ];then
		echo "multi service is detected, please check"
		exit 2
	fi
fi

IDL_SERVICE_NAME=$( ${THIS_SCRIPT_REAL_DIR}/thrift -detect $IDL | grep '^detect service' | awk '{print $NF}' )
if [ "$IDL_SERVICE_NAME" == "" ];then
    echo "can not detect service name for $IDL"
    exit 2
fi

PY_NAMESPACE=$( ${THIS_SCRIPT_REAL_DIR}/thrift -detect $IDL | grep '^detect py namespace' | awk '{print $NF}' )

PY_IMPORT_START="$IDL_PROGRAME_NAME"
if [ "$PY_NAMESPACE" != "" ];then
	PY_IMPORT_START="$PY_NAMESPACE"
fi
echo "PY_IMPORT_START=$PY_IMPORT_START"

${THIS_SCRIPT_REAL_DIR}/thrift -r -gen java -o $OUTPUTDIR $IDL
if [ $? -ne 0 ];then
    echo "generate java code for $IDL failed, please check!"
    exit 2
fi

${THIS_SCRIPT_REAL_DIR}/thrift -r -gen py -out $OUTPUTDIR/gen-java/${IDL_PROGRAME_NAME} $IDL
if [ $? -ne 0 ];then
   echo "generate py clients code for $IDL failed, please check!"
   exit 2
fi

PY_TEST_FILE=$OUTPUTDIR/gen-java/${IDL_PROGRAME_NAME}/py_clients/${IDL_SERVICE_NAME}_test.py
if [ -f "$PY_TEST_FILE" ];then
   exit 0
fi

echo '#!/usr/bin/env python' > $PY_TEST_FILE
echo "# test for service ${IDL_SERVICE_NAME}" >> $PY_TEST_FILE
echo "import random" >> $PY_TEST_FILE
echo "from $PY_IMPORT_START.ttypes import *" >> $PY_TEST_FILE
echo "from $PY_IMPORT_START.constants import *" >> $PY_TEST_FILE
echo "from $PY_IMPORT_START.client.${IDL_SERVICE_NAME}Stub import *" >> $PY_TEST_FILE
echo >> $PY_TEST_FILE
echo "stub=${IDL_SERVICE_NAME}Stub()" >> $PY_TEST_FILE
echo '#using like stub.xxxfunc(routeKey=random.randint(0, 100000), timeout=3000, args...)' >> $PY_TEST_FILE
echo '#testing...' >> $PY_TEST_FILE
echo '' >> $PY_TEST_FILE

