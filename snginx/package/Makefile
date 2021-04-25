
NAME=snginx
VERSION=1.3
RELEASE=7
GROUP=Company

BUILD_TYPE=Debug
ARCH=$(shell uname -m )
FINAL_PACKAGE_NAME=$(NAME)-$(VERSION)-$(RELEASE).$(ARCH).rpm
TARGET_FOLDER=/public/packages/platform
PEARPATH=/usr/local/soldier/snginx

NGINX_DIR=../nginx-1.8.1

compile: nginx
	cd ../snginx_agent/java;mvn package;cd -
	
nginx: $(NGINX_DIR)
	cd $(NGINX_DIR);./configure --with-cc-opt='-O2' --with-http_ssl_module --error-log-path=/var/log/nginx/error.log --http-log-path=/var/log/nginx/access.log --pid-path=/var/run/nginx.pid  --lock-path=/var/run/nginx.lock --http-client-body-temp-path=/var/cache/nginx/client_temp --http-proxy-temp-path=/var/cache/nginx/proxy_temp --http-fastcgi-temp-path=/var/cache/nginx/fastcgi_temp --http-uwsgi-temp-path=/var/cache/nginx/uwsgi_temp --http-scgi-temp-path=/var/cache/nginx/scgi_temp --with-http_auth_request_module;make;cd - 


package: compile
	su root -c 'checkinstall --requires=soldier --pkgname=$(NAME) --pkgversion=$(VERSION) --pkgrelease=$(RELEASE) --pkggroup=$(GROUP) --backup=no'

package_deploy:
	su root -c 'cp -rf /root/rpmbuild/RPMS/$(ARCH)/$(FINAL_PACKAGE_NAME) $(TARGET_FOLDER) && ln -sf $(TARGET_FOLDER)/$(FINAL_PACKAGE_NAME) $(TARGET_FOLDER)/$(NAME)-latest.rpm'

install: 
	mkdir -p $(PEARPATH)
	mkdir -p $(PEARPATH)/bin
	mkdir -p $(PEARPATH)/log
	mkdir -p $(PEARPATH)/config
	mkdir -p $(PEARPATH)/data
	mkdir -p $(PEARPATH)/etc
	mkdir -p $(PEARPATH)/etc/conf.d
	mkdir -p $(PEARPATH)/etc/html
	cp -f ../deploy/*.sh $(PEARPATH)
	cp -f ../deploy/snginx $(PEARPATH)
	cp -f ../deploy/config/snginx_agent_config.properties -p $(PEARPATH)/config
	cp -f ../deploy/log/watchdog.log $(PEARPATH)/log
	cp -f $(NGINX_DIR)/objs/nginx $(PEARPATH)/bin
	cp -f ../snginx_agent/java/target/snginx_agent-1.0-SNAPSHOT-jar-with-dependencies.jar $(PEARPATH)/bin
	cp -f ../deploy/data/version $(PEARPATH)/data
	cp -f ../deploy/data/web_servers.conf $(PEARPATH)/data
	cp -f ../deploy/etc/*.conf $(PEARPATH)/etc
	cp -f ../deploy/etc/mime.types $(PEARPATH)/etc
	cp -f ../deploy/etc/html/*.html $(PEARPATH)/etc/html
	


