(ns webapp-nodes.nodes
  (:require
   [pallet.core :as core]
   [pallet.resource :as resource]
   [pallet.resource.service :as service]
   [webapp-nodes.crates :as crates]))

(core/defnode webapp
  "Basic web app, serverd by tomcat"
  {}
  :bootstrap (resource/phase
              (crates/bootstrap))
  :configure (resource/phase
              (crates/tomcat))
  :deploy (resource/phase
           (crates/tomcat-deploy
            "../mini-webapp/mini-webapp-1.0.0-SNAPSHOT.war"))
  :restart-tomcat (resource/phase
                   (service/service "tomcat6" :action :restart)))


(core/defnode haproxy
  "Simple haproxy"
  {}
  :bootstrap (resource/phase
              (crates/bootstrap))
  :configure (resource/phase
              (crates/haproxy))
  :restart-haproxy (resource/phase
                    (service/service "haproxy" :action :restart)))

(core/defnode proxied
  "A proxied web app"
  {}
  :bootstrap (resource/phase
              (crates/bootstrap))
  :configure (resource/phase
              (crates/tomcat)
              (crates/reverse-proxy :haproxy :app1 8080))
  :deploy (resource/phase
           (crates/tomcat-deploy
            "../mini-webapp/mini-webapp-1.0.0-SNAPSHOT.war"))
  :restart-tomcat (resource/phase
                   (service/service "tomcat6" :action :restart)))
