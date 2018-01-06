#!/bin/bash
ROOT_ADDRESS=${1:-`./whats_my_ip.sh`}

echo "Provided external address is [${ROOT_ADDRESS}]"

export CONCOURSE_EXTERNAL_URL=http://${ROOT_ADDRESS}:8080
# https://github.com/concourse/concourse/issues/1310
export CONCOURSE_EXTERNAL_DNS=`./whats_my_dns.sh`
echo "External DNS is [${CONCOURSE_EXTERNAL_DNS}]"

docker-compose up -d