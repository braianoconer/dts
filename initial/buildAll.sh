#!/bin/bash
DOCKER_BUILDKIT=1 docker build -f English-Dockerfile -t observability/english .
DOCKER_BUILDKIT=1 docker build -f Translator-Dockerfile -t observability/translator .
DOCKER_BUILDKIT=1 docker build -f Spanish-Dockerfile -t observability/spanish .
DOCKER_BUILDKIT=1 docker build -f Italian-Dockerfile -t observability/italian .
DOCKER_BUILDKIT=1 docker build -f German-Dockerfile -t observability/german .
