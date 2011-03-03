#!/usr/bin/env bash

cake=${CAKE-~/bin/cake}
compute_service=${pallet_compute_service-${PALLET_COMPUTE_SERVICE-live-test}}
let fails=0

fail() {
    r=$?
    let fails=(fails + 1)
    echo "FAIL: cake $1 failed"
    return $r
}

# test with cake
echo "cake deps"
${cake} deps || fail "deps"
echo "cake pallet nodes"
${cake} pallet nodes -- -P $compute_service || fail "pallet nodes"
echo "cake clean"
${cake} clean || fail "clean"
echo "cake kill"
${cake} kill -9 || fail "kill"

exit $fails
