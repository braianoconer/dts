#!/bin/bash

set -e

helm repo add bitnami https://charts.bitnami.com/bitnami

k8s_ns=kafka
kubectl get ns $k8s_ns || kubectl create ns $k8s_ns

zookeeper_release=zookeeper
kafka_release=kafka

helm status $zookeeper_release -n $k8s_ns || helm install $zookeeper_release bitnami/zookeeper \
	--namespace $k8s_ns \
	--set replicaCount=3 \
	--set auth.enabled=false \
	--set allowAnonymousLogin=true

helm status $kafka_release -n $k8s_ns || helm install $kafka_release bitnami/kafka \
	--namespace $k8s_ns \
	--set zookeeper.enabled=false \
	--set replicaCount=3 \
	--set externalZookeeper.servers=${zookeeper_release}.${k8s_ns}.svc.cluster.local
