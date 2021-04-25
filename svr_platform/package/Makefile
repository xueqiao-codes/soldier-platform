
NAME=svr_platform
VERSION=1.6
RELEASE=5
GROUP=Company

PEARPATH=/usr/local/soldier/svr_platform
BUILD_TYPE=Debug
ARCH=$(shell uname -m )
FINAL_PACKAGE_NAME=$(NAME)-$(VERSION)-$(RELEASE).$(ARCH).rpm
TARGET_FOLDER=/public/packages/platform

package:
	su root -c 'checkinstall --requires=soldier --pkgname=$(NAME) --pkgversion=$(VERSION) --pkgrelease=$(RELEASE) --pkggroup=$(GROUP) --backup=no'

package_deploy:
	su root -c 'cp -rf /root/rpmbuild/RPMS/$(ARCH)/$(FINAL_PACKAGE_NAME) $(TARGET_FOLDER) && ln -sf $(TARGET_FOLDER)/$(FINAL_PACKAGE_NAME) $(TARGET_FOLDER)/$(NAME)-latest.rpm'


install:
	mkdir -p $(PEARPATH)
	mkdir -p $(PEARPATH)/lib
	mkdir -p $(PEARPATH)/log
	mkdir -p $(PEARPATH)/backup
	mkdir -p $(PEARPATH)/daemon
	mkdir -p $(PEARPATH)/plugins
	mkdir -p $(PEARPATH)/services
	mkdir -p $(PEARPATH)/lock
	mkdir -p $(PEARPATH)/func
	touch $(PEARPATH)/lib/.empty
	touch $(PEARPATH)/services/.empty
	touch $(PEARPATH)/plugins/.empty
	touch $(PEARPATH)/daemon/.empty
	touch $(PEARPATH)/backup/.empty
	touch $(PEARPATH)/log/.empty
	cp -f ../deploy/*.sh $(PEARPATH)
	cp -f ../deploy/func/*.sh $(PEARPATH)/func
	cp -f ../deploy/lock/*.lock $(PEARPATH)/lock

