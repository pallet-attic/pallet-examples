(ns mini-webapp.app
  "mini-webapp application"
  (:require
   [ring.util.response :as response]))

(defn app [req]
  (response/response "Hello World!"))
