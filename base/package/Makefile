
NAME=soldier
VERSION=1.2
RELEASE=1
GROUP=Company

PEARPATH=/usr/local/soldier
ARCH=$(shell uname -m )
FINAL_PACKAGE_NAME=$(NAME)-$(VERSION)-$(RELEASE).$(ARCH).rpm
TARGET_FOLDER=/public/packages/platform

package:
	su root -c 'checkinstall --pkgname=$(NAME) --pkgversion=$(VERSION) --pkgrelease=$(RELEASE) --pkggroup=$(GROUP) --backup=no'

package_deploy:
	su root -c 'cp -rf /root/rpmbuild/RPMS/$(ARCH)/$(FINAL_PACKAGE_NAME) $(TARGET_FOLDER) && ln -sf $(TARGET_FOLDER)/$(FINAL_PACKAGE_NAME) $(TARGET_FOLDER)/$(NAME)-latest.rpm'


install:
	mkdir -p $(PEARPATH)/lib
	mkdir -p $(PEARPATH)/log
	mkdir -p $(PEARPATH)/bin
	cp -f ../deploy/log/auto_restart.sh.log $(PEARPATH)/log
	cp -f ../deploy/*.sh $(PEARPATH)
	cp -f ../deploy/bin/*.sh $(PEARPATH)/bin
	cp -f ../third_party/jnotify-lib-0.94/64_bit_Linux/libjnotify.so $(PEARPATH)/lib
