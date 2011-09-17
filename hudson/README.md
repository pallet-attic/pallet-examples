# hudson

This is an example project for launching a fully configured hudson server for a
maven project, using [pallet](http://github.com/hugoduncan/pallet).

## Usage

Create a clojure project with

    bash$ lein new hudson
    Created new project in: hudson
    bash$ cd hudson

Leiningen creates a `project.clj` file, and we need to add pallet and jclouds to
the :dependencies, and pallet-lein to the :dev-dependencies, so it looks like:

    (defproject blank-project "0.3.0"
      :description "blank-project for pallet"
      :dependencies [[org.cloudhoist/pallet "0.4.6"]
                     [org.cloudhoist/pallet-crates-standalone "0.4.0"]
                     [org.jclouds/jclouds-all "1.0-beta-8"]
                     [org.jclouds/jclouds-jsch "1.0-beta-8"]
                     [org.jclouds/jclouds-log4j "1.0-beta-8"]
                     [org.jclouds/jclouds-enterprise "1.0-beta-8"]
                     [log4j/log4j "1.2.14"]]
      :dev-dependencies [[org.cloudhoist/pallet-lein "0.4.0"]]
      :repositories {"sonatype" "https://oss.sonatype.org/content/repositories/releases"})

You can create `~/.pallet/config.clj` to include your cloud credentials.

    (defpallet
      :serivces
        {:aws {:provider "ec2" :identity "key" :credential "secret-key"}
         :rs  {:provider "cloudservers" :identity "username" :credential "key"}})

Lein will create `src/hudson/core.clj` which we will rename to `src/hudson/ci.clj`.
See [ci.clj](http://github.com/pallet/pallet-examples/blob/master/hudson/src/hudson/ci.clj)


To launch the server:

    bash$ lein deps
    bash$ lein pallet converge hudson.ci/hudson 1



## License

Copyright (C) 2010 Hugo Duncan

Distributed under the Eclipse Public License.
