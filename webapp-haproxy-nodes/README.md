# webapp-haproxy-nodes

This example is a project that contains
[pallet](http://github.com/hugoduncan/pallet) node configurations and
phases to deploy web applications to the cloud proxied by HAProxy.
The project can be used as starting point for your own multi-node
projects.

## Testing

### Setting up your environment

If you haven't created the file `~/.pallet/config.clj` with your cloud
provider data, do it so now by copying the file
[settings.xml](tree/master/webapp-haproxy-nodes/config.clj) into your
`~/.pallet` directory (create the directory if necessary). Change the
provider keys to match your cloud provider and your identity and
credential to sign into the cloud provider. The currently supported
providers are: 

    :openhosting-east1
    :serverlove-z1-man
    :cloudsigma-zrh
    :aws-ec2
    :eucalyptus-partnercloud-ec2
    :bluelock-vcloud-zone01
    :bluelock-vcdirector
    :cloudservers-us
    :elastichosts-lon-p
    :skalicloud-sdg-my
    :deltacloud
    :nova
    :trmk-vcloudexpress
    :eucalyptus
    :bluelock-vcloud-vcenterprise
    :elastichosts-lon-b
    :stratogen-vcloud-mycloud
    :cloudservers-uk
    :trmk-ecloud
    :elastichosts-sat-p
    :slicehost
    :gogrid
    :stub
    :vcloud
    :elasticstack
    :savvis-symphonyvpdc
    :rimuhosting
    :byon
    :node-list
    :virtualbox

### Building the demo webapps

We will use two different web applications for this example:
`nano-webapp` and `mini-webapp`.

First we build the war file in the
[mini-webapp](http://github.com/pallet/pallet-examples/tree/master/mini-webapp/)
project directory.

    bash$ lein deps
    bash$ lein compile
    bash$ lein uberwar

Then we build the war file in the
[nano-webapp](http://github.com/pallet/pallet-examples/tree/master/nano-webapp)
project directory.

    bash$ mvn clean package

### Testing from the command line

To test the configuration, from the webapp-nodes directory, we start a
webapp single instance of the 'proxied' node with nano-webapp
deployed. 

    bash$ lein deps
    bash$ lein pallet converge webapp-nodes.nodes/proxied 1 :deploy-nano-webapp :restart-tomcat

Using the public IP address of your new node, check that the newly
deployed application is running by visiting `http://<node's public IP>:8080`

To obtain the IP addresses of your nodes, run the following to get the
list of running nodes and their public (and private) IP addresses:

    bash$ lein pallet nodes

__NOTE__: When using `lein pallet`, pallet will automatically use the first
provider defined in `~/.pallet/config.clj`. If you want to use another
provider you should pass its name to pallet via the `-P` command line
option to `lein pallet`, e.g.:

    bash$ lein pallet -P <cloud provider name> converge ...

### Testing from the REPL

Alternatively, we can start a REPL from the webapp-nodes directory,
start a webapp node, and deploy our application (NOTE: in the REPL
examples, replace the key `:aws` for whatever you named the cloud
provider service configuration in `~/.pallet/config.clj`)

    bash$ lein deps
    bash$ lein repl
    user> (require 'webapp-nodes.nodes)
    user> (require 'pallet.compute)
    user> (require 'pallet.core)
    user> (def service (pallet.compute/compute-service-from-config-file :aws))
    user> (pallet.core/converge {webapp-nodes.nodes/proxied 1}
                                :compute service
                                :phase [:deploy-nano-webapp :restart-tomcat])

Using the public IP address of your new node, check that the newly
deployed application is running by visiting `http://<node's public
IP>:8080`. To get the IP address of your nodes, run the following:

    user> (pallet.compute/nodes service)

This will return the listing below. What we're looking for is the
public ip address 23.20.96.254 in this case.

```
    (       proxied
                    ZONE/us-east-1d.REGION/us-east-1.PROVIDER/aws-ec2 null
                    amzn-linux paravirtual null amazon/amzn-ami-pv-2012.03.1.x86_64-ebs
                    RUNNING
                    public: 23.20.96.254  private: 10.214.221.179)
```

### Redeploying your web application

Further deploys can be run with the `lift` function.

    bash$ lein pallet lift webapp-nodes.nodes/proxied :deploy-nano-webapp

or

    user> (pallet.core/lift webapp-nodes.nodes/proxied
                            :compute service
                            :phase :deploy-nano-webapp)

NOTE: The above methods should also work with mini-webapp by using the
phase `:deploy-mini-webapp`.

## Tearing down a deployment

If you want to destroy the node/s you just created, all you need to do
is set the count to 0 on converge, e.g.:

    bash$ lein pallet converge webapp-nodes.nodes/proxied 0

or

    user> (pallet.core/converge {webapp-nodes.nodes/proxied 0} :compute service)

## Multinode deployments

We are going to deploy a few 'proxied' nodes and a 'haproxy' node that
will be configured with HAProxy. For this job there are not many
changes needed, though.

### At the command line

    $ lein pallet converge webapp-nodes.nodes/proxied 2 webapp-nodes.nodes/haproxy 1 :deploy-nano-webapp :restart-tomcat :restart-haproxy

Visit `http://<proxy public address>/` and confirm that the proxy is
working. The IP address reported by the webapps should change between
refreshes of the page, reflecting the fact that the proxy is balancing
the requests. (NOTE: it might take a while for HAProxy to switch
webapps)

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
