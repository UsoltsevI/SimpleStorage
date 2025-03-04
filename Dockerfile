###
# build S service image
###

FROM ubuntu:22.04

WORKDIR /root

RUN apt update && apt install -y \
    openjdk-21-jdk
