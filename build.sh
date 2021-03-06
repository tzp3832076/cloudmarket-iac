#!/bin/sh
if [ $# -eq 0 ] ; then
  mvn -Dspring.profiles.active=test clean package -U
else
  if [ $1 = "-q" ] ; then
    mvn -Dmaven.test.skip=true clean package -U
  else
    echo "build.sh [-q]"
    exit 0
  fi
fi

if [ $? -ne 0 ] ; then
  echo "mvn package error"
  exit -1
fi

rm -rf output
mkdir output

mkdir -p output/bin
mkdir -p output/conf
cp mkt-iac-api/target/mkt-iac-api-version.jar output/bin/mkt-iac-api.jar
cp deploy/* output/bin

chmod +x output/bin/*
