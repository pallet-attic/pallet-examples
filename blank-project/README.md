# blank-project

This example is a blank project that contains
[pallet](http://github.com/hugoduncan/pallet).  The project can be used as
starting point for your own projects.

## Creating

The first thing we need is [leiningen](http://github.com/technomancy/leiningen),
a build tool for clojure.

    bash$ curl -O lein http://github.com/technomancy/leiningen/raw/stable/bin/lein
    bash$ chmod +x lein

Now we can create the project:

    bash$ lein new blank-project
    Created new project in: blank-project
    bash$ cd blank-project

Leiningen creates a `project.clj` file, and we need to add `pallet` and `jclouds` to the :dependencies, so it looks like:

    (defproject blank-project "0.2.0-SNAPSHOT"
      :description "blank-project for pallet"
      :dependencies [[org.clojure/clojure "1.2.0"]
                     [org.clojure/clojure-contrib "1.2.0"]
                     [pallet "0.2.0-SNAPSHOT"]
                     [org.jclouds/jclouds-all "1.0-SNAPSHOT"]
                     [org.jclouds/jclouds-jsch "1.0-SNAPSHOT"]
                     [org.jclouds/jclouds-log4j "1.0-SNAPSHOT"]
                     [org.jclouds/jclouds-enterprise "1.0-SNAPSHOT"]]
      :repositories {"jclouds-snapshot" "https://oss.sonatype.org/content/repositories/snapshots/"})

The last configuration step is to edit `~/.m2/settings.xml` to include your
cloud credentials. The
[setttings.xml](http://github.com/hugoduncan/pallet-examples/settings.xml) file
in this projects provides an example of the format.  If you do not have this
file, you can create it from the example.

## Testing

To test the configuration, we'll start a REPL, and list the nodes in your account.

    bash$ lein deps
    bash$ lein repl
    user> (use 'pallet.maven)

    user> (use 'pallet.compute)
    user> (use 'org.jclouds.compute)
    user> (def service (compute-service))
    user> (nodes service)

This should show any instances that you have running in your cloud account.

## Options

### Logging

The project contains a `resources/log4j.properties` file that configures log4j
logging into the log subdirectory.  Edit this to control the placement and
detail level of the logs.

### Swank
If you use [SLIME](http://common-lisp.net/project/slime), you can add swank-clojure to the `project.clj`.

  :dev-dependencies [[swank-clojure/swank-clojure "1.2.1"]]

### jclouds provider specific jars
When we added jclouds, we specified `jclouds-all` as the dependency.  Jclouds
also has fine grained jars for each individual cloud provider that can be used instead to reduce the size of the jclouds dependency.




## License

Copyright (C) 2010 Hugo Duncan

Distributed under the Eclipse Public License, the same as Clojure.
