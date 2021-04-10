#!/bin/bash

for i in {1..9}
do
  echo "Login $i attempt"
  mvn test > /tmp/test${i} &
  sleep 1
done

