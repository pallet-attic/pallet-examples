(defproject webapp-haproxy-nodes "0.6.0"
  :description "Webapp with haproxy load balancer"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.cloudhoist/pallet "0.6.8"]
                 [org.cloudhoist/haproxy "0.6.0"]
                 [org.cloudhoist/java "0.5.1"]
                 [org.cloudhoist/tomcat "0.6.0"]
                 [org.cloudhoist/automated-admin-user "0.5.0" ]
                 [org.jclouds/jclouds-all "1.2.1"]
                 [org.jclouds.driver/jclouds-jsch "1.2.1"]
                 [org.jclouds.driver/jclouds-slf4j "1.2.1"]
                 [org.jclouds/jclouds-compute "1.2.1"]
                 [org.jclouds/jclouds-blobstore "1.2.1"]   
                 [ch.qos.logback/logback-core "1.0.1"]
                 [ch.qos.logback/logback-classic "1.0.1"]]
  :dev-dependencies [[swank-clojure/swank-clojure "1.3.2"] ; swank
                     [org.cloudhoist/pallet-lein "0.4.2"]] ; leiningen
  :repositories
  {"sonatype" "https://oss.sonatype.org/content/repositories/releases/"})
