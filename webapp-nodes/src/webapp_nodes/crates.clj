(ns webapp-nodes.crates
  "Crates for configuring and deploying to a web application server"
  (:require
   [pallet.resource :as resource]
   [pallet.crate.automated-admin-user :as automated-admin-user]
   [pallet.crate.java :as java]
   [pallet.crate.tomcat :as tomcat]))

(defn bootstrap
  "Common Bootstrap"
  [request]
  (automated-admin-user/automated-admin-user request))

(defn tomcat
  "Tomcat server configuration"
  [request]
  (-> request
      (java/java :sun)
      (tomcat/tomcat)))

(defn tomcat-deploy
  "Tomcat deploy as ROOT application"
  [request path]
  (-> request
      (tomcat/deploy "ROOT" :local-file path :clear-existing true)))

