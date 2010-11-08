(ns webapp-nodes.nodes
  (:require
   [pallet.maven :as maven]
   [pallet.core :as core]
   [pallet.resource :as resource]
   [pallet.resource.service :as service]
   [webapp-nodes.crates :as crates]))

(core/defnode haproxy
  "Simple haproxy"
  {:inbound-ports [80 22]}  ;; 80 for haproxy, 22 for SSH
  :bootstrap (resource/phase
              (crates/bootstrap))
  :configure (resource/phase
              (crates/haproxy))
  :restart-haproxy (resource/phase
                    (service/service "haproxy" :action :restart)))

(core/defnode proxied
  "A proxied web app"
  {:inbound-ports [8080 22]} ;; 8080 for tomcat, 22 for SSH
  :bootstrap (resource/phase
              (crates/bootstrap))
  :configure (resource/phase
              (crates/tomcat)
              (crates/reverse-proxy :haproxy :app1 8080))
  :deploy-mini-webapp (resource/phase
                       (crates/tomcat-deployy
                        "../mini-webapp/mini-webapp-1.0.0-SNAPSHOT.war"))
  :deploy-nano-webapp (resource/phase
                       (crates/tomcat-deploy
                        "../nano-webapp/target/nano-webapp.war"))
  :deploy-from-blobstore (resource/phase
                          (crates/tomcat-deploy-from-blobstore
                           (:pallet.deploy.bucket (maven/properties ["pallet-config"]))
                           "mini-webapp-1.0.0-SNAPSHOT.war"))
  :restart-tomcat (resource/phase
                   (service/service "tomcat6" :action :restart)))



