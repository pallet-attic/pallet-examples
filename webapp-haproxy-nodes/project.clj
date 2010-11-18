(defproject webapp-haproxy-nodes "0.4.0-SNAPSHOT"
  :description "Webapp with haproxy load balancer"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.cloudhoist/pallet "0.4.0-SNAPSHOT"]
                 [org.cloudhoist/pallet-crates-all "0.4.0-SNAPSHOT"]
                 [org.jclouds/jclouds-aws "1.0-beta-8"]
                 [org.jclouds/jclouds-rackspace "1.0-beta-8"]
                 [org.jclouds/jclouds-jsch "1.0-beta-8"]
                 [org.jclouds/jclouds-log4j "1.0-beta-8"]
                 [org.jclouds/jclouds-enterprise "1.0-beta-8"]
                 [log4j/log4j "1.2.14"]]
  :dev-dependencies [[swank-clojure/swank-clojure "1.2.1"]
                     [org.cloudhoist/pallet-lein "0.2.0"]]
  :repositories {"sonatype"
                 "https://oss.sonatype.org/content/repositories/releases/"
                 "sonatype-snapshots"
                 "http://oss.sonatype.org/content/repositories/snapshots"})
