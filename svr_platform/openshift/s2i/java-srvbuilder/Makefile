IMAGE_NAME = docker-registry.default.svc:5000/soldier-platform/java-svrbuilder:1.1

.PHONY: build
build:
	docker build -t $(IMAGE_NAME) .

.PHONY: test
test:
	docker build -t $(IMAGE_NAME)-candidate .
	IMAGE_NAME=$(IMAGE_NAME)-candidate test/run
