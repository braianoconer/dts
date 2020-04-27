#!/bin/bash

set -e

# Install helm
exists=true
which helm  > /dev/null 2>&1 || exists=false

if ! $exists; then
	echo "Helm seems not to be installed"
	script="/tmp/install-helm.sh"
	curl https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3 > $script
	chmod +x $script && $script && rm -rf $script
fi
