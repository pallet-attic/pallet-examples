(defproject mini-webapp "1.0.0-SNAPSHOT"
  :description "mini-webapp"
  :aot [mini-webapp.Servlet]
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [ring "0.2.6"]]
  :dev-dependencies [[uk.org.alienscience/leiningen-war "0.0.7"]
                     [swank-clojure/swank-clojure "1.2.1"]])
