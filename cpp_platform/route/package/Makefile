
NAME=route_agent
VERSION=1.7
RELEASE=0
GROUP=Company

BUILD_TYPE=Debug

ARCH=$(shell uname -m )
FINAL_PACKAGE_NAME=$(NAME)-$(VERSION)-$(RELEASE).$(ARCH).rpm
TARGET_FOLDER=/public/packages/platform

PEARPATH=/usr/local/soldier/route_agent

compile:
	../../../build.sh route_all $(BUILD_TYPE)
	cd ../route_agent_daemon/java;mvn package;cd -

package: compile
	su root -c 'checkinstall --requires=soldier --pkgname=$(NAME) --pkgversion=$(VERSION) --pkgrelease=$(RELEASE) --pkggroup=$(GROUP) --backup=no'

package_deploy:
	su root -c 'cp -rf /root/rpmbuild/RPMS/$(ARCH)/$(FINAL_PACKAGE_NAME) $(TARGET_FOLDER) && ln -sf $(TARGET_FOLDER)/$(FINAL_PACKAGE_NAME) $(TARGET_FOLDER)/$(NAME)-latest.rpm'


install: 
	mkdir -p $(PEARPATH)
	mkdir -p $(PEARPATH)/include
	mkdir -p $(PEARPATH)/lib
	mkdir -p $(PEARPATH)/log
	mkdir -p $(PEARPATH)/bin
	mkdir -p $(PEARPATH)/data
	mkdir -p $(PEARPATH)/conf
	mkdir -p $(PEARPATH)/route_finder_python
	cp -f ../deploy/auto_restart.sh $(PEARPATH)
	cp -f ../source/route_finder.h $(PEARPATH)/include
	cp -f ../deploy/conf/config.properties -p $(PEARPATH)/conf
	cp -f ../deploy/log/auto_restart.sh.log $(PEARPATH)/log
	cp -f ../deploy/log/auto_restart.log $(PEARPATH)/log
	cp -f ../deploy/bin/route_agent_daemon.sh $(PEARPATH)/bin
	cp -f ../../../out/$(BUILD_TYPE)/route_agent_tool2 $(PEARPATH)/bin
	cp -f ../route_agent_daemon/java/target/route_agent_daemon-1.0-SNAPSHOT-jar-with-dependencies.jar $(PEARPATH)/bin
	cp -f ../../../out/$(BUILD_TYPE)/lib/libjroute_agent_daemon.so $(PEARPATH)/lib
	cp -f ../../../out/$(BUILD_TYPE)/lib/libjroute_agent.so $(PEARPATH)/lib
	cp -f ../../../out/$(BUILD_TYPE)/lib/libroute_agent_shared.so $(PEARPATH)/lib
	cp -f ../../../out/$(BUILD_TYPE)/python/route_finder_support/__init__.pyc $(PEARPATH)/route_finder_python
	cp -f ../../../out/$(BUILD_TYPE)/python/route_finder_support/route_finder.pyc $(PEARPATH)/route_finder_python
	cp -f ../../../out/$(BUILD_TYPE)/python/route_finder_support/_route_finder.so $(PEARPATH)/route_finder_python
	touch $(PEARPATH)/data/.empty


