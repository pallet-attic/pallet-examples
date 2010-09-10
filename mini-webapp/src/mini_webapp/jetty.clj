(ns mini-webapp.jetty
  (:require
   [ring.adapter.jetty :as jetty]
   [mini-webapp.app :as app]))


;; Keep track of the server, so we can stop it if required
(defonce server (atom nil))

(defn start
  "Start the app, keeping track of the server"
  []
  (do
    (reset!
     server
     (jetty/run-jetty
      #'app/app
      {:port 8080
       :join? false}))))

(defn stop
  "Stop the app"
  []
  (swap! server (fn [server] (.stop server) nil)))
