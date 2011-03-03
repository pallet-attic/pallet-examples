#!/usr/bin/env bash

lein=${LEIN-~/bin/lein}
compute_service=${pallet_compute_service-${PALLET_COMPUTE_SERVICE-live-test}}
let fails=0

fail() {
    r=$?
    let fails=(fails + 1)
    echo "FAIL: lein $1 failed"
    return $r
}

# test with lein
echo "lein deps"
${lein} deps || fail "deps"
echo "lein pallet nodes"
${lein} pallet -P $compute_service nodes || fail "pallet nodes"
echo "lein clean"
${lein} clean || fail "clean"

exit $fails
