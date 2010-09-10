# webapp-nodes

This example is a project that contains
[pallet](http://github.com/hugoduncan/pallet) node configurations and phases to
deploy web applications to the cloud.  The project can be used as starting point
for your own projects.

## Testing

First we build the war file, in the [mini-webapp](http://github.com/hugoduncan/pallet-examples/tree/master/mini-webapp/) project directory.

    bash$ lein deps
    bash$ lein uberwar

To test the configuration, we'll start a REPL from the webapp-nodes directory,
start a webapp node, and deploy our application.

    bash$ lein deps
    bash$ lein repl
    user> (use 'pallet.maven)
    user> (use 'org.jclouds.compute)
    user> (use 'pallet.compute)
    user> (require 'webapp-nodes.nodes)
    user> (def service (compute-service-from-settings))
    user> (pallet.core/converge {webapp-nodes.nodes/webapp 1} service :deploy)

Further deploys can be run with the `lift` function.

    user> (pallet.core/lift webapp-nodes.nodes/webapp service :deploy)

## License

Copyright (C) 2010 Hugo Duncan

Distributed under the Eclipse Public License, the same as Clojure.
