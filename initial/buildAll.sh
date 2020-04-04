#!/bin/bash
DOCKER_BUILDKIT=1 docker build -f Init-Dockerfile -t observability/initiator .
DOCKER_BUILDKIT=1 docker build -f Translator-Dockerfile -t observability/translator .
DOCKER_BUILDKIT=1 docker build -f Light-Dockerfile -t observability/light .
DOCKER_BUILDKIT=1 docker build -f Medium-Dockerfile -t observability/medium .
DOCKER_BUILDKIT=1 docker build -f Heavy-Dockerfile -t observability/heavy .
