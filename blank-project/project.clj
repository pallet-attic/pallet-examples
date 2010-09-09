(defproject blank-project "0.2.0-SNAPSHOT"
  :description "blank-project for pallet"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [pallet "0.2.0-SNAPSHOT"]
                 [org.jclouds/jclouds-all "1.0-SNAPSHOT"]
                 [org.jclouds/jclouds-jsch "1.0-SNAPSHOT"]
                 [org.jclouds/jclouds-log4j "1.0-SNAPSHOT"]
                 [org.jclouds/jclouds-enterprise "1.0-SNAPSHOT"]]
  ;; This will add swank-clojure
  ;; :dev-dependencies [[swank-clojure/swank-clojure "1.2.1"]]
  :repositories {"jclouds-snapshot" "https://oss.sonatype.org/content/repositories/snapshots/"})
