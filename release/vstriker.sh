#!/bin/bash
#####################################
# Startup script for vStriker on *nix
#####################################

if type -p java; then
  echo found java executable in PATH
  _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
  echo found java executable in JAVA_HOME     
  _java="$JAVA_HOME/bin/java"
else
  echo "no java"
fi

if [[ "$_java" ]]; then
  version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
  echo version "$version"
  if [[ "$version" > "1.7" ]]; then
    echo version is more than 1.5
  else         
    echo version is less than 1.5
    die "ERROR: Upgrade to latest version of Java"
  fi
fi

[[ -d logs ]] || mkdir logs

now=$(date +%Y%m%d%H%M%S)

java -classpath ./beta/vStriker.jar com.emccode.vstriker.VStriker > logs/$now.log 2>&1

