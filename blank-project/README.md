# blank-project

This example is a blank project that contains
[pallet](http://github.com/pallet/pallet).  The project can be used as
starting point for your own projects.

## Creating

### lein

The first thing we need is
[leiningen](http://github.com/technomancy/leiningen), a build tool for
clojure.

    bash$ curl -O http://github.com/technomancy/leiningen/raw/stable/bin/lein
    bash$ chmod +x lein

Now we can create the project:

    bash$ lein new blank-project
    Created new project in: blank-project
    bash$ cd blank-project

Leiningen creates a `project.clj` file, and we need to add pallet and
jclouds to the `:dependencies`, and pallet-lein to the
`:dev-dependencies`, so it looks like:

    (defproject blank-project "0.6.0"
      :description "blank-project for pallet"
      :dependencies [;; pallet lib
                     [org.cloudhoist/pallet "0.6.6"]
                     ;; all pallet crates
                     [org.cloudhoist/pallet-crates-all "0.5.0"]
                     ;; jclouds imports
                     [org.jclouds/jclouds-all "1.2.1"] 
                     [org.jclouds/jclouds-compute "1.2.1"]
                     [org.jclouds/jclouds-blobstore "1.2.1"]
                     [org.jclouds.driver/jclouds-jsch "1.2.1"]
                     [org.jclouds.driver/jclouds-slf4j "1.2.1"]
                     ;; logging
                     [ch.qos.logback/logback-core "1.0.0"]
                     [ch.qos.logback/logback-classic "1.0.0"]]
      :dev-dependencies [[swank-clojure/swank-clojure "1.3.2"] ; swank
                         [org.cloudhoist/pallet-lein "0.4.1"]] ; lein
      :repositories
      ;; pallet and jclouds libraries live at sonatype
      {"sonatype" "https://oss.sonatype.org/content/repositories/releases/"})

Note that `jclouds-all` is rather heavy.  You can use the list of
supported clouds and individual jclouds provider jars to slim the
dependency down.

    lein deps
    lein pallet providers

## Credentials

The last configuration step is to specify your cloud credentials.

### ~/.pallet/config.clj

You can create `~/.pallet/config.clj` to include your cloud credentials.

    (defpallet
      :services
        {:aws {:provider "aws-ec2" :identity "key" :credential "secret-key"}
         :rs  {:provider "cloudservers-us" :identity "username" :credential "key"}})


## Testing

To test the configuration, we can use the pallet-lein plugin, to list
the nodes in your cloud account (the first that appears in the
`defpallet` declaration above, in this example being `:aws`.)

    bash$ lein deps
    bash$ lein pallet nodes

If you wanted to pick another provider from your list in `defpallet`,
you can do it by passing the provider as a parameter the following way
(in this case, selecting the second provider `:rs`):

    bash$ lein pallet -P rs nodes

Alternatively we can start a REPL, to do the same.

    bash$ lein deps
    bash$ lein repl
    user> (use 'pallet.compute)
    user> (def my-service (compute-service-from-config-file))
    user> (nodes my-service)
    
The above snipped selected the default provider too, but from the REPL
you can also easily select another provider from your list, for
example RackSpace Cloudservers (`:rs` in the `defpallet` declaration).

    user> (def my-service (compute-service-from-config-file :rs))

Both of these should show any instances that you have running in your
cloud account.

## Options

### Logging

The project contains a `resources/logback.xml` file that configures
[logback](http://logback.qos.ch/) logging into the log subdirectory.
Edit this to control the placement and detail level of the logs.

### Swank

If you use [SLIME](http://common-lisp.net/project/slime), you can add
swank-clojure to the `project.clj`.

  :dev-dependencies [[swank-clojure/swank-clojure "1.3.2"]]

### jclouds provider specific jars

When we added jclouds, we specified `jclouds-all` as the dependency.
jclouds also has fine grained jars for each individual cloud provider
that can be used instead to reduce the size of the jclouds dependency.

### Eclipse

If you use eclipse, you can generate the project files using a
combination of lein and mvn.  When finished, you can import this as an
existing project.

    bash$ lein pom
    bash$ mvn eclipse:eclipse -DdownloadSources=true -DdownloadJavadocs=true

## License

Copyright (C) 2010 Hugo Duncan

Distributed under the Eclipse Public License.
