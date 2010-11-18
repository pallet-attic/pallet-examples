(defproject webapp-nodes "0.4.0-SNAPSHOT"
  :description "Webapp on tomcat"
  :dependencies [[org.cloudhoist/pallet "0.4.0-SNAPSHOT"]
                 [org.cloudhoist/pallet-crates-all "0.4.0-SNAPSHOT"]
                 [org.jclouds/jclouds-all "1.0-beta-8"]
                 [org.jclouds/jclouds-jsch "1.0-beta-8"]
                 [org.jclouds/jclouds-log4j "1.0-beta-8"]
                 [log4j/log4j "1.2.14"]]
  :dev-dependencies [[swank-clojure/swank-clojure "1.2.1"]
                     [org.cloudhoist/pallet-lein "0.2.0"]]
  :repositories {"sonatype"
                 "https://oss.sonatype.org/content/repositories/releases"
                 "sonatype-snapshots"
                 "http://oss.sonatype.org/content/repositories/snapshots"})
