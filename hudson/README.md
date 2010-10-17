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

    (defproject blank-project "0.2.0-SNAPSHOT"
      :description "blank-project for pallet"
      :dependencies [[org.clojure/clojure "1.2.0"]
                     [org.clojure/clojure-contrib "1.2.0"]
                     [org.cloudhoist/pallet "0.3.0-SNAPSHOT"]
                     [org.jclouds/jclouds-all "1.0-SNAPSHOT"]
                     [org.jclouds/jclouds-jsch "1.0-SNAPSHOT"]
                     [org.jclouds/jclouds-log4j "1.0-SNAPSHOT"]
                     [org.jclouds/jclouds-enterprise "1.0-SNAPSHOT"]
                     [log4j/log4j "1.2.14"]]
      :dev-dependencies [[org.cloudhoist/pallet-lein "0.1.0"]]
      :repositories {"sonatype-snapshot" "https://oss.sonatype.org/content/repositories/snapshots/"
                     "sonatype" "https://oss.sonatype.org/content/repositories/releases"})


Now edit (or create) `~/.m2/settings.xml` to include your cloud credentials. The
[setttings.xml](http://github.com/hugoduncan/pallet-examples/blob/master/blank-project/settings.xml)
file in this projects provides an example of the format.  If you do not have
this file, you can create it from the example.

    <settings>
      <profiles>
        <profile>
          <id>cloud-credentials</id>
          <activation>
            <activeByDefault>true</activeByDefault>
          </activation>
          <properties>
            <jclouds.compute.provider>ec2</jclouds.compute.provider>
            <jclouds.compute.identity>api-key</jclouds.compute.identity>
            <jclouds.compute.credential>api-secret</jclouds.compute.credential>
          </properties>
        </profile>
      </profiles>
    </settings>

Lein will create `src/hudson/core.clj` which we will rename to `src/hudson/ci.clj`.
See [ci.clj](http://github.com/hugoduncan/pallet-examples/blob/master/hudson/src/hudson/ci.clj)


To launch the server:

    bash$ lein deps
    bash$ lein pallet converge hudson.ci/hudson 1 :deploy



## License

Copyright (C) 2010 Hugo Duncan

Distributed under the Eclipse Public License.
