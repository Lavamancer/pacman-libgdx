#!/usr/bin/env bash

bash -x gradlew html:dist

ssh root@jalbarracin.com "rm -rf /root/nginx/html/index/pacman"
ssh root@jalbarracin.com "mkdir /root/nginx/html/index/pacman"
scp -r html/build/dist/* root@jalbarracin.com:/root/nginx/html/index/pacman

