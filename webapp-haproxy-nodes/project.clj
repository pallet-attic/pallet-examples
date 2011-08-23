(defproject webapp-haproxy-nodes "0.6.0"
  :description "Webapp with haproxy load balancer"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.cloudhoist/pallet "0.6.2"]
                 [org.cloudhoist/haproxy "0.5.0"]
                 [org.cloudhoist/java "0.5.1"]
                 [org.cloudhoist/tomcat "0.6.0"]
                 [org.cloudhoist/automated-admin-user "0.5.0" ]
                 [org.jclouds/jclouds-all "1.0.0"]
                 [org.jclouds.driver/jclouds-jsch "1.0.0"]
                 [org.jclouds.driver/jclouds-log4j "1.0.0"]
                 [log4j/log4j "1.2.14"]]
  :dev-dependencies [[swank-clojure/swank-clojure "1.3.2"] ; swank
                     [vmfest "0.2.2"]                      ; virtualbox
                     [org.cloudhoist/pallet-lein "0.4.1"]  ; lein
                     [cake-pallet "0.4.0"]]                ; cake
  :repositories
  {"sonatype" "https://oss.sonatype.org/content/repositories/releases/"}
  :tasks [cake-pallet.tasks])
