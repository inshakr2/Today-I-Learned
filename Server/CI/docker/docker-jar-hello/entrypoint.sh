#!/bin/sh

ACTIVE_PROFILE="${PROFILE:-dev}"

echo "ACTIVE_PROFILE=${ACTIVE_PROFILE}"

exec java -Djava.security.egd=file:/dev/./urandom \
          -Dspring.profiles.active=${ACTIVE_PROFILE} \
          -jar hello.jar