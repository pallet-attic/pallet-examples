(ns mini-webapp.jetty
  (:require
   [ring.adapter.jetty :as jetty]
   [mini-webapp.app :as app]))


;; Keep track of the server, so we can stop it if required
(defonce server (atom nil))

(defn start
  "Start the app, keeping track of the server"
  [& {:keys [port join?] :or {port 8080 join? false}}]
  (reset! server (jetty/run-jetty #'app/app {:port port :join? join?})))

(defn stop
  "Stop the app"
  []
  (swap! server (fn [server] (.stop server) nil)))
