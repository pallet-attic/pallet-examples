(ns #^{:author "Hugo Duncan"}
  demo
  "This is an introductory example of using pallet.")

;; First we load the pallet commands into
(require 'pallet.repl)
(pallet.repl/use-pallet)
(use '[pallet.resource :only [phase]])


;; Supported providers can be found with
(supported-providers)

;; We provide some credentials
(def my-user "your user")
(def my-password "your api key")

;; and log in to the cloud using the credentials defined above.
;; provider is a string specifiying the provider, as returned
;; from (supported-providers). For example, for Amazon Web Servie's
;; EC2, the provider is "aws-ec2"
(def my-service
  (compute-service "provider" :identity my-user :credential my-password))

;; nodes can be listed with the nodes function
(nodes my-service)

;; In order to create nodes, we need to define node types.
;; The second argument is a vector specifying features we want in our image.
(defnode webserver {})
(defnode balancer {:os-family :ubuntu :smallest true})

;; At this point we can manage instance counts as a map.
;; e.g ensure that we have two webserver nodes
(converge {webserver 2} :compute my-service)

;; now we want two webservers and one balancer

(converge {webserver 2 balancer 1} :compute my-service)

;; ... and we can remove our nodes
(converge {webserver 0 balancer 0} :compute my-service)

;; Images are configured differently between clouds and os's.
;; Pallet comes with some \"crates\" that can be used to normalise
;; the images.
;;
;; for example, we probably want an admin user. The default for
;; automated-admin-user is to use your current login name, with no
;; password and your id_rsa ssh key.
(use 'pallet.crate.automated-admin-user)
(defnode webserver
  {}
  :bootstrap (phase (automated-admin-user)))

;; recreate a node with our admin-user
(converge {webserver 1} :compute my-service)

;; Another example, that adds java to our node type, then converges
;; to install java
(use 'pallet.crate.java)
(defnode webserver
  {}
  :bootstrap (phase (automated-admin-user))
  :configure (phase (java :openjdk)))

(converge {webserver 1} :compute my-service)

;; if you do not want to adjust the number of nodes, there is also
;; a lift function, which can be used to apply configuration
(lift webserver :compute my-service)

;; :bootstrap and :configure are two phases.  These are used by default
;; by the converge method.  The :bootstrap phase applies to nodes on first
;; boot, and :configure on every invocation of converge. You can also
;; specify other phases, either as a keyword, in which case the
;; configuration is taken from the corresponding section of the
;; defnode, ...
(converge {webserver 1} :compute my-service :phase :configure)

;; ... or as an inline definiton, with phase
(converge {webserver 1}
          :compute my-service
          :phase [:configure (phase (package "curl"))])

;; :configure is also the default phase for lift, but unlike with
;; converge, the :configure phase is not added if not specified.
(lift [webserver] :compute my-service :phase (phase (package "curl")))

;; you can manage arbitrary machines that are ssh accesable, including
;; local virtual machines.  For this to work you may have to specify
;; the :sudo-password option in the admin user, even if you can
;; log in without a password
(defnode vm {:os-family :ubuntu})
(def vm1 (make-unmanaged-node "vm" "localhost" :ssh-port 2223))
(with-admin-user ["myuser" :sudo-password "xxx"]
  (lift {vm vm1} :compute service :phase (phase (package "curl"))))")
