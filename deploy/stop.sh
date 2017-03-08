#!/bin/sh
ps ux | grep "mkt-iac-api\.jar" | grep -v grep | grep -v stop.sh | cut -c 9-15 | xargs kill

i=0
while true
do
    ps ux | grep "mkt-iac-api\.jar" | grep -v grep | grep -v stop.sh
    if [ $? -ne 0 ]; then
        break
    fi
    sleep 5s
    let i=i+1
    if [ $i -gt 10 ]; then
        ps ux | grep "mkt-iac-api\.jar" | grep -v grep | grep -v stop.sh | cut -c 9-15 | xargs kill -9
        break
    fi
done

for var in $@
do
    ps ux | grep "$var" | grep -v grep | grep -v stop.sh | cut -c 9-15 | xargs kill
done

echo "kill $@ done"
