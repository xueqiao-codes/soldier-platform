#!/bin/bash

LOG_FILE="/usr/local/soldier/snginx/log/watchdog.log"

count=$( ps -ef | egrep "/usr/local/soldier/snginx/bin/nginx" | grep -v grep | wc -l )
if [ $count -eq 0 ];then
	echo "[$( date +'%Y-%m-%d %H:%M:%S')] start snginx" >> ${LOG_FILE}	
	/usr/local/soldier/snginx/snginx start
fi

count=$( /usr/local/soldier/snginx/snginx_agent.sh show | wc -l )
if [ $count -eq 0 ];then
	echo "[$( date +'%Y-%m-%d %H:%M:%S')] start snginx_agent" >> ${LOG_FILE}
	/usr/local/soldier/snginx/snginx_agent.sh start
fi

