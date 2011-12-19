(ns #^{:author "Hugo Duncan"}
  demo
  "This is an introductory example of using pallet.")

;; First we load the pallet commands into
(require 'pallet.repl)
(pallet.repl/use-pallet)
(use '[pallet.phase :only [phase-fn]])


;; Supported providers can be found with:
(supported-providers)

;; We provide some credentials to our cloud provider...
(def my-user "your user")
(def my-password "your api key")

;; ...and log in to the cloud using the credentials defined above.
;; provider is a string specifiying the provider, as returned
;; from (supported-providers). For example, for Amazon Web Service's
;; EC2, the provider is "aws-ec2"
(def my-service
  (compute-service "provider" :identity my-user :credential my-password))

;; current compute nodes in your account can be listed with the nodes
;; function (you might see none now if your account is new):
(nodes my-service)

;; Pallet does not address nodes directly, the smallest unit it
;; addresses is a node group or just `group`. A group can have any
;; number of identical nodes. A single node is then a group with just
;; one istance of a node.

;; In order to create compute nodes, we need to first define a node
;; group, and we do so with `group-spec`. The group-spec function
;; takes one mandatory argument: `name` which is the name that pallet
;; will use to identify nodes in this group created in the cloud
;; provider. For example, the following is a minimal group
;; specification, named `webserver`

(def webserver (group-spec "webserver"))

;; The other arguments for group-spec are optional. For now we will
;; use the parameter `:node-spec` which provides a hardware, OS (image) and
;; network specificaton for the nodes. For example, the following
;; group spec defines nodes of the smalles size provided by the cloud
;; provider and running Ubuntu, and with the network firewalls
;; configured so that ports 80 and 8080 are publicly accessible.

(def balancer
  (group-spec "balancer"
              :node-spec {:image {:os-family :ubuntu}
                          :hardware {:smallest true}
                          :network {:inbound-ports [80 8080]}}))

;; At this point we can start creating nodes via the cloud provider.
;; The way it is done is by specifying how many nodes of each group we
;; want. Pallet will ensure that we have as many nodes as we need. To
;; do so, it will first check with the cloud provider for existing
;; nodes of this group, and then will determine if it needs to create
;; more nodes, destroy some or do nothing.
;;
;; converge is the function that will perform this task. In the
;; following example we instruct pallet to make it so that we have 2
;; nodes of the `webserver` group. Notice that we specify with which
;; cloud provider we want pallet to work in `:compute`; in this case
;; we use the one we just created.

(converge {webserver 2} :compute my-service)

;; Now let's say we want two webservers and one balancer:

(converge {webserver 2 balancer 1} :compute my-service)

;; Finally, if we want to remove all the nodes in one group, we do the
;; following. (Please NOTE: at this point make sure run this command, as the
;; rest of the tutorial assumes you did so)

(converge {webserver 0 balancer 0} :compute my-service)

;; Images are configured differently between clouds and os's.
;; Pallet comes with some \"crates\" that can be used to normalise
;; the images.
;;
;; One very useful examples is `automated-admin-user`, a crate that
;; creates an admin user for your nodes. The default behavior for this
;; crate is to use your current login name, and authorize your local
;; ssh key (id_rsa in ~/.ssh in your home directory). This way, you
;; can quickly ssh into your node without needing to log into your
;; cloud provider to download the password or authentication keys.

;; The following code creates a new group-spec that contains what's
;; called a phase (defined under `:phases`. A Phase is just a named
;; function that can be executed during a converge session.

;; By default, when a node is newly created, if a `:bootstrap` phase
;; is defined, this phase will be run. This is a special phase that is
;; executed by the cloud provider itself right after the node is
;; created and doesn't require any authentication, hence is the
;; perfect time to create our user.

(use 'pallet.crate.automated-admin-user)

(def webserver
  (group-spec "webserver"
              :phases {:bootstrap (phase-fn (automated-admin-user))}))

;; Assuming you have deleted all the nodes as instructed above, the
;; next command will create a new `webserver` node, this time with an
;; admin user created, named as your user, and with your ssh keys
;; authenticated to it. The results of this command will be stored in
;; `results`.

(def results
  (converge {webserver 1} :compute my-service))

;; results will contain a large amount of information, including the
;; logs resulting from Pallet running all the scripts on the nodes on
;; your behalf (in this case, to create this admin user).

;; For now, we want to get the IP address of the nodes in the
;; `webserver` group. To do so we just inspect the contents of
;; :all-nodes in the results, which will print some of the relevant
;; node information, including the public address.

(:all-nodes results)

;; Go ahead and ssh into the public address of your freshly created
;; node. If all went well, you should be logged right in, no password
;; needed. 

;; Crates can do many (every?) things, for example, install software.
;; The following conde snippet redefines the `webservice` group to
;; install `Java` during the `:configure` phase. `:configure` is
;; another special phase that is run by configure by default, uless
;; otherwise specified. (There is only another special phase,
;; `:settings`, not covered in this tutorial)

(use 'pallet.crate.java)
(def webserver
  (group-spec "webserver"
              :phases {:bootstrap (phase-fn (automated-admin-user))
                       :configure (phase-fn (java :openjdk))}))

;; Reconverging the group will result in Java being installed in all
;; the nodes of the group.

(converge {webserver 1} :compute my-service)

;; If you do not want to adjust the number of nodes, there is also
;; a lift function, which can be used to apply the configuration to
;; all existing nodes of the group.

(lift webserver :compute my-service)

;; The following group-spec redefines `webserver` to add an arbitrary
;; phase that will install `curl` on the nodes in the group.

(def webserver
  (group-spec "webserver"
              :phases {:bootstrap (phase-fn (automated-admin-user))
                       :configure (phase-fn (java :openjdk))
                       :install-curl (phase-fn (package "cur"))}))

;; Now, if we only wanted to install curl, we'd do the following

(converge {webserver 1}
          :compute my-service
          :phase :install-curl)

;; But if we wanted both Java and curl to be installed, then we'd do:

(converge {webserver 1}
          :compute my-service
          :phase [:configure :install-curl])

;; Alternativley, you can define a phase to be executed in a ad-hoc
;; manner just like in the following example in which instead of a
;; phase keyword (:install-curl) there is the phase definition. This
;; is great for quickly running scripts on all the nodes from the REPL
;; itself

(converge {webserver 1}
          :compute my-service
          :phase [:configure (phase-fn (package "curl"))])

;; :configure is also the default phase for lift, but unlike with
;; converge, the :configure phase is not added if not specified. The
;; following example will *only* run :install-curl, but not configure.

(lift [webserver] :compute my-service :phase [:install-curl])


