# webapp-nodes

This example is a project that contains
[pallet](http://github.com/hugoduncan/pallet) node configurations and phases to
deploy web applications to the cloud.  The project can be used as starting point
for your own projects.

## Testing

To test the configuration, we'll start a REPL, start a webapp node, and deploy our
application.

    bash$ lein deps
    bash$ lein repl
    user> (use 'pallet.maven)
    user> (use 'org.jclouds.compute)
    user> (use 'pallet.compute)
    user> (require 'webapp-nodes.nodes)
    user> (def service (compute-service-from-settings))
    user> (pallet.core/converge {webapp-nodes.nodes/webapp 1} service)
    user> (pallet.core/lift webapp-nodes.nodes/webapp service :deploy)

This should show any instances that you have running in your cloud account.


## License

Copyright (C) 2010 Hugo Duncan

Distributed under the Eclipse Public License, the same as Clojure.
