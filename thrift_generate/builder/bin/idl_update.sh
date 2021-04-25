#!/bin/bash



function gitUpdate() {
	local branch=$1
	local giturl=$2 
	local idldir=$3

	if [ ! -d "$idldir" -o ! -f "$idldir/comm.thrift" ];then
		git clone -b $branch $giturl $idldir
		if [ $? -ne 0 ];then
			echo "Git clone failed!!!"
			return 
		fi
	fi
	
	pushd $idldir > /dev/null

	echo "Git Pull for $( pwd ) In $( date ) ..."
	git pull
	
	popd > /dev/null
	
}

if [ "z" = "z${ENV_BRANCH}" ];then
	echo "NO BRANCH is set!!!"
	exit 1
fi 

if [ "z" = "z${ENV_GITURL}" ];then
	echo "NO GITURL is set!!!"
	exit 1
fi

if [ "z" = "z${ENV_IDLDIR}" ];then
	echo "NO IDLDIR is set"
	exit 1
fi

while :
do
	gitUpdate $ENV_BRANCH $ENV_GITURL $ENV_IDLDIR
	sleep 5s
done
