#!/bin/bash

# Script to set the pallet version across the examples
# Note this is OSX specific sed usage
projects="basic blank-project hudson mini-webapp nano-webapp webapp-haproxy-nodes webapp-nodes"

if [ $# -lt 1 ]; then
  echo "Usage: set-version version-string"
  echo "   eg. set-version 0.4.0-SNAPSHOT"
  exit 1
fi

version=$1
echo Setting version to $version

for project in $projects; do
  if [ -e $project/project.clj ]; then
      echo $project/project.clj
      sed -E -i .bak \
          -e "s!defproject ([^ ]+).*!defproject \1 \"${version}\"!" \
          -e "s!hoist/pallet \"[^\"]+\"!hoist/pallet \"${version}\"!" \
          -e "s!/pallet-crates-all \"[^\"]+\"!/pallet-crates-all \"${version}\"!" \
          $project/project.clj
  fi
done
