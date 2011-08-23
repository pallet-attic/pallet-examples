# webapp-haproxy-nodes

This example is a project that contains
[pallet](http://github.com/hugoduncan/pallet) node configurations and phases to
deploy web applications to the cloud proxied by HAProxy.  The project can be used as starting point for your own multi-node projects.

## Testing

### Setting up your environment

Copy the file [settings.xml](http://github.com/hugoduncan/pallet-examples/tree/master/webapp-haproxy-nodes/settings.xml) into your ~/.m2 directory. If you already have an existing settings.xml file in that directory, merge the contents into the existing ones.

Add your cloud provider name and credentials in the profile, both for compute and optionally for blobstore. In the latter case, also provide the name of the bucket to use later on to deploy your applications from your blobstore.

### Building the demo webapps

We will use two different web applications for this example: nano-webapp and mini-webapp.

First we build the war file in the [mini-webapp](http://github.com/hugoduncan/pallet-examples/tree/master/mini-webapp/) project directory.

    bash$ lein deps
    bash$ lein compile
    bash$ lein uberwar

Then we build the war file in the [nano-webapp](http://github.com/hugoduncan/pallet-examples/tree/master/nano-webapp) project directory.

    bash$ mvn clean package

### Testing from the command line

To test the configuration, from the webapp-nodes directory, we start a webapp single instance of the 'proxied' node with nano-webapp deployed.

    bash$ lein deps
    bash$ lein pallet converge webapp-nodes.nodes/proxied 1 :configure :deploy-nano-webapp :restart-tomcat

Using the public IP address of your new node, check that the newly deployed application is running by visiting http://<node's public IP>:8080

### Testing from the REPL

Alternatively, we can start a REPL from the webapp-nodes directory,
start a webapp node, and deploy our application.

    bash$ lein deps
    bash$ lein repl
    user> (require 'webapp-nodes.nodes)
    user> (require 'pallet.compute)
    user> (require 'pallet.core)
    user> (def service (pallet.compute/compute-service-from-settings))
    user> (pallet.core/converge {webapp-nodes.nodes/proxied 1} :compute service :phase [:deploy-nano-webapp :restart-tomcat])

Using the public IP address of your new node, check that the newly deployed application is running by visiting http://<node's public IP>:8080

### Redeploying your web application

Further deploys can be run with the `lift` function.

    bash$ lein pallet lift webapp-nodes.nodes/proxied :deploy-nano-webapp

or

    user> (pallet.core/lift webapp-nodes.nodes/proxied :compute service :phase :deploy-nano-webapp)

NOTE: The above methods should also work with mini-webapp by using the phase :deploy-mini-webapp.

### Deploying from blobstore

Since mini-webapp is a relatively large application (5MB), if you were  to instantiate many 'proxied' nodes with this webapp, you would be uploading this .war file many times from your computer. For this reason you can optionally deploy a webapp from a blobstore.

For this to work you need to create a bucket in your blobstore and upload the file mini-webapp/mini-webapp-1.0.0-SNAPSHOT.war into the bucket. Make sure the bucket name coincides with the one set in settings.xml. If it is different, then you will need to update settings.xml and restart the REPL for pallet to pick up the changes.

Once this is al set, just use the phase :deploy-from-blobstore instead of :deploy-nano-webapp (or :deploy-mini-webapp), i.e.:

    user> (def blobstore (pallet.blobstore/blobstore-from-settings))
    user> (pallet.core/converge {webapp-nodes.nodes/proxied 1}
                 :compute service :blobstore blobstore
                 :phase [:deploy-from-blobstore :restart-tomcat])

## Tearing down a deployment

If you want to destroy the node/s you just created, all you need to do is set the count to 0 on converge, e.g.:

    bash$ lein pallet converge webapp-nodes.nodes/proxied 0

or

    user> (pallet.core/converge {webapp-nodes.nodes/proxied 0} :compute service)

NOTE: if you experience an error trying to deploy some nodes after having converged to 0 at the REPL, try recreating the compute session:

    user> (def service (pallet.compute/compute-service-from-settings))

## Multinode deployments

We are going to deploy a few 'proxied' nodes and a 'haproxy' node that will be configured with HAProxy. For this job there are not many changes needed, though.

### At the command line

    $ lein pallet converge webapp-nodes.nodes/proxied 2 webapp-nodes.nodes/haproxy 1 :deploy-nano-webapp :restart-tomcat :restart-haproxy

Visit http://<proxy public address>/ and confirm that the proxy is working. The IP address reported by the webapps should change between refreshes of the page, reflecting the fact that the proxy is balancing the requests. (NOTE: it might take a while for HAProxy to switch webapps)

### At the REPL

    user> (pallet.core/converge {webapp-nodes.nodes/proxied 2
                                 webapp-nodes.nodes/haproxy 1}
                 :compute service
                 :phase [:deploy-nano-webapp :restart-tomcat :restart-haproxy])

### Finishing up

To finish up, destroy all the nodes:

    $ lein pallet converge webapp-nodes.nodes/proxied 0 webapp-nodes.nodes/haproxy 0

or

    user> (pallet.core/converge {webapp-nodes.nodes/proxied 0
                                 webapp-nodes.nodes/haproxy 0}
                 :compute service)

## License

Copyright (C) 2010 Hugo Duncan

Distributed under the Eclipse Public License.
