
NAME=mitty
VERSION=1.6
RELEASE=7
GROUP=Company

PEARPATH=/usr/local/soldier/mitty
BUILD_TYPE=Debug
ARCH=$(shell uname -m )
FINAL_PACKAGE_NAME=$(NAME)-$(VERSION)-$(RELEASE).$(ARCH).rpm
TARGET_FOLDER=/public/packages/platform

compile:
	cd ../java;mvn clean package;cd -

package:compile
	su root -c 'checkinstall --pkgname=$(NAME) --pkgversion=$(VERSION) --pkgrelease=$(RELEASE) --pkggroup=$(GROUP) --backup=no'

package_deploy:
	su root -c 'cp -rf /root/rpmbuild/RPMS/$(ARCH)/$(FINAL_PACKAGE_NAME) $(TARGET_FOLDER) && ln -sf $(TARGET_FOLDER)/$(FINAL_PACKAGE_NAME) $(TARGET_FOLDER)/$(NAME)-latest.rpm'


install:
	mkdir -p $(PEARPATH)
	mkdir -p $(PEARPATH)/etc
	mkdir -p $(PEARPATH)/etc/example
	mkdir -p $(PEARPATH)/log
	mkdir -p $(PEARPATH)/lib
	mkdir -p $(PEARPATH)/webapps
	mkdir -p $(PEARPATH)/work
	mkdir -p $(PEARPATH)/func
	mkdir -p $(PEARPATH)/lock
	mkdir -p $(PEARPATH)/backup
	cp -f ../deploy/etc/webdefault.xml $(PEARPATH)/etc/
	cp -f ../deploy/etc/example/mitty.ini $(PEARPATH)/etc/example/
	cp -f ../deploy/log/* $(PEARPATH)/log/
	cp -f ../deploy/*.sh $(PEARPATH)/
	cp -f ../deploy/lib/* $(PEARPATH)/lib/
	cp -f ../java/target/mitty*.jar $(PEARPATH)/lib/
	cp -f ../deploy/lock/*.lock $(PEARPATH)/lock/
	cp -f ../deploy/func/*.sh $(PEARPATH)/func
	touch $(PEARPATH)/webapps/.empty
	touch $(PEARPATH)/work/.empty
	touch $(PEARPATH)/backup/.empty
	

