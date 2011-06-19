(defproject webapp-haproxy-nodes "0.4.0"
  :description "Webapp with haproxy load balancer"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.cloudhoist/pallet "0.4.3"]
                 [org.cloudhoist/pallet-crates-standalone "0.4.0"]
                 [org.jclouds/jclouds-aws "1.0-beta-8"]
                 [org.jclouds/jclouds-rackspace "1.0-beta-8"]
                 [org.jclouds/jclouds-jsch "1.0-beta-8"]
                 [org.jclouds/jclouds-log4j "1.0-beta-8"]
                 [org.jclouds/jclouds-gogrid "1.0-beta-8"]
                 [org.jclouds/jclouds-enterprise "1.0-beta-8"]
                 [log4j/log4j "1.2.14"]
                 [org.apache.maven/maven-settings "2.0.10"]]
  :dev-dependencies [[swank-clojure/swank-clojure "1.2.1"] ; swank
                     [vmfest "0.2.2"]                      ; virtualbox
                     [org.cloudhoist/pallet-lein "0.4.0"]  ; lein
                     [cake-pallet "0.4.0"]]                ; cake
  :repositories
  {"sonatype" "https://oss.sonatype.org/content/repositories/releases/"}
  :tasks [cake-pallet.tasks])
