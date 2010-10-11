(ns webapp-nodes.nodes
  (:require
   [pallet.core :as core]
   [pallet.resource :as resource]
   [pallet.resource.service :as service]
   [webapp-nodes.crates :as crates]))

(core/defnode haproxy
  "Simple haproxy"
  {:image-id "us-east-1/ami-6006f309"
   :inbound-ports [80 22]}  ;; 80 for haproxy, 22 for SSH
  :bootstrap (resource/phase
              (crates/bootstrap))
  :configure (resource/phase
              (crates/haproxy))
  :restart-haproxy (resource/phase
                    (service/service "haproxy" :action :restart)))

(core/defnode proxied
  "A proxied web app"
  {:image-id "us-east-1/ami-6006f309"
   :inbound-ports [8080 22]} 
  :bootstrap (resource/phase
              (crates/bootstrap))
  :configure (resource/phase
              (crates/tomcat)
              (crates/reverse-proxy :haproxy :app1 8080))
  :deploy (resource/phase
           (crates/tomcat-deploy "../mini-webapp/mini-webapp-1.0.0-SNAPSHOT.war"))
  :restart-tomcat (resource/phase
                   (service/service "tomcat6" :action :restart)))

(def proxied-from-blobstore
     (assoc-in proxied [:phases :deploy]
               (resource/phase
                (crates/tomcat-deploy-from-blobstore "pallet-deployments" ;; todo: remove hardcoded  bucket
                                                  "mini-webapp-1.0.0-SNAPSHOT.war"))))


