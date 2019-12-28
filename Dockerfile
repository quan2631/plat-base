FROM reg.yondervision.com.cn:8081/rdgrp/java:8u161-k8s
ADD plat-base-run/target/plat-base-run.jar /var/k8s/plat-base-run.jar
USER root
RUN chown 1000:1000 /var/k8s/plat-base-run.jar
USER k8s
