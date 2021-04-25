# java_service_builder
FROM docker-registry.default.svc:5000/soldier-tools/maven
LABEL maintainer="wangli"

# TODO: Rename the builder environment variable to inform users about application you provide them
# ENV BUILDER_VERSION 1.0

# TODO: Set labels used in OpenShift to describe the builder image
LABEL io.k8s.description="Platform for building java job" \
      io.k8s.display-name="builder java.job" \
      io.openshift.s2i.scripts-url="image:///usr/libexec/s2i" \
      io.openshift.tags="builder,java_service"

COPY ./s2i/bin/ /usr/libexec/s2i
RUN chmod a+x /usr/libexec/s2i/*

# Set the default CMD for the image
# CMD ["/usr/libexec/s2i/usage"]
