#!/bin/sh
export JAVA_HOME=$BUILD_KIT_PATH/java/jdk-1.8-8u20
export MAVEN_3_0_4=/usr/maven/apache-maven-3.0.4/bin
export PATH=${JAVA_HOME}/bin:/usr/maven/apache-maven-3.0.4/bin:$PATH 

find ./  -name "pom.xml" -exec sed -i "s/<version>\${mkt-iac-version}/<version>$1/g" {} \;

mvn -Dversion=$1 -Dmaven.test.skip=true clean package deploy:deploy -U
