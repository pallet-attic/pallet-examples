#!/bin/bash

# run some sanity checks

projects="basic blank-project hudson webapp-haproxy-nodes webapp-nodes"

log=${PALLETLOG-$(pwd)/integration.log}
pallet_compute_service=${PALLET_COMPUTE_SERVICE-live-test}
root=$(pwd)
JVM_OPTS="-Xms256M -Xmx256M -Xmn128M -Djava.awt.headless=true -XX:MaxPermSize=128m"

export root
export pallet_compute_service
export JVM_OPTS
echo "STARTING integration tests $(date)" | tee -a $log

let fails=0

for project in ${projects}
do
    echo "testing $project"
    # propogate log4j.properties
    mkdir -p $project/test-resources
    cp ${root}/integration-tests/log4j.properties $project/test-resources
    ( \
        echo "global tests"
        cd $project
        for script in ${root}/integration-tests/*.sh; do
            before="$(date +%s)"
            ( /usr/bin/env bash $script ) | tee -a $log 2>&1
            let fails=(fails + $?)
            after="$(date +%s)"
            elapsed_seconds="$(expr $after - $before)"
            echo "Test ran in $elapsed_seconds secs : $script"
        done
    )
    ( \
        echo "local tests"
        if [ -e $project/integration-tests ]; then
        cd $project
        for script in integration-tests/*.sh; do
            ( /usr/bin/env bash $script ) | tee -a $log 2>&1
            let fails=(fails + $?)
        done
        fi
    )
done

echo "FINISHED integration tests $(date)" | tee -a $log
echo "FAILURES $fails" | tee -a $log 2>&1
