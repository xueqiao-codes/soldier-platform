
NAME=dal_set
VERSION=1.1
RELEASE=2
GROUP=Company

BUILD_TYPE=Debug

PEARPATH=/usr/local/soldier/dal_set
ARCH=$(shell uname -m )
FINAL_PACKAGE_NAME=$(NAME)-$(VERSION)-$(RELEASE).$(ARCH).rpm
TARGET_FOLDER=/public/packages/platform

compile:
	cd ../dal_set_agent_daemon/java;mvn package;cd -

package: compile
	su root -c 'checkinstall --requires=soldier --pkgname=$(NAME) --pkgversion=$(VERSION) --pkgrelease=$(RELEASE) --pkggroup=$(GROUP) --backup=no'

package_deploy:
	su root -c 'cp -rf /root/rpmbuild/RPMS/$(ARCH)/$(FINAL_PACKAGE_NAME) $(TARGET_FOLDER) && ln -sf $(TARGET_FOLDER)/$(FINAL_PACKAGE_NAME) $(TARGET_FOLDER)/$(NAME)-latest.rpm'


install:
	mkdir -p $(PEARPATH) 
	mkdir -p $(PEARPATH)/config
	mkdir -p $(PEARPATH)/log
	mkdir -p $(PEARPATH)/bin
	mkdir -p $(PEARPATH)/data
	mkdir -p $(PEARPATH)/bin/func
	cp -f ../deploy/auto_restart.sh $(PEARPATH)
	cp -f ../deploy/config/config.properties -p $(PEARPATH)/config
	cp -f ../deploy/log/auto_restart.sh.log $(PEARPATH)/log
	cp -f ../deploy/log/auto_restart.log $(PEARPATH)/log
	cp -f ../deploy/bin/dal_set_agent_daemon.sh $(PEARPATH)/bin
	cp -f ../deploy/bin/dal_set_agent_daemon_sh.lock $(PEARPATH)/bin
	cp -f ../deploy/bin/func/*.sh $(PEARPATH)/bin/func
	cp -f ../dal_set_agent_daemon/java/target/dal_set_agent_daemon-1.0-SNAPSHOT-jar-with-dependencies.jar $(PEARPATH)/bin
	touch $(PEARPATH)/data/.empty


