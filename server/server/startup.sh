#! /bin/bash
ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
java -jar -Dspring.profiles.active=prod app.jar