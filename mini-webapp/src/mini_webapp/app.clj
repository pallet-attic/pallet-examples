(ns mini-webapp.app
  "mini-webapp application"
  (:require
   [ring.util.response :as response])
  (:import (java.net InetAddress)))

(defn app [req]
  (response/response (str "Hello World! from: "
                          (.getHostAddress (InetAddress/getLocalHost)))))
