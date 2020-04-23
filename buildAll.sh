#!/bin/bash
DOCKER_BUILDKIT=1 docker build -f English-Dockerfile -t dts/english .
DOCKER_BUILDKIT=1 docker build -f Translator-Dockerfile -t dts/translator .
DOCKER_BUILDKIT=1 docker build -f Spanish-Dockerfile -t dts/spanish .
DOCKER_BUILDKIT=1 docker build -f Italian-Dockerfile -t dts/italian .
DOCKER_BUILDKIT=1 docker build -f German-Dockerfile -t dts/german .
