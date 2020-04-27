## Install

# Install helm
./install-helm.sh

# Install kafka + zookeeper
./kafka/install.sh

# Install translation services
kubectl apply -f app/

## Delete

kubectl delete -f app/
for i in $(helm ls -n kafka | awk '{print $1}'|tail -n+2); do
	helm uninstall $i -n kafka;
done

kubectl delete ns kafka